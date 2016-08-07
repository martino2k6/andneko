/*
 * Copyright (c) 2016 Martin Bella. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.orange_box.aneko

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.*
import android.net.Uri
import android.os.Handler
import android.provider.Settings
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat
import android.support.v7.app.NotificationCompat
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class FloatingService :
        AccessibilityService(),
        SharedPreferences.OnSharedPreferenceChangeListener {
    
    private val allowed: Boolean
        get() = SettingsCompat.canDrawOverlays(this)
    
    private val updater = Executors.newSingleThreadScheduledExecutor()
    private val handler = Handler()
    
    private val current by lazy { Point(displayWidth / 2, displayHeight / 2) }
    private val last by lazy { Point(current) }
    
    private val displayWidth by lazy { resources.displayMetrics.widthPixels }
    private val displayHeight by lazy { resources.displayMetrics.heightPixels }
    
    private val manager by lazy { getService<WindowManager>(WINDOW_SERVICE) }
    private val view by lazy { WindowView(this) }
    
    private val prefs by lazy {
        with(Preferences(this)) {
            register(this@FloatingService)
            this
        }
    }
    
    private val cache = object : LinkedHashMap<Int, Bitmap>() {
        private val limit = State.values().size * 2
        override fun removeEldestEntry(
                eldest: MutableMap.MutableEntry<Int, Bitmap>?) = size >= limit
    }
    
    private var scheduledAwake: ScheduledFuture<*>? = null
    private var scheduledMovementUpdater: ScheduledFuture<*>? = null
    private var scheduledStateAlternator: ScheduledFuture<*>? = null
    private var scheduledSleepWatcher: ScheduledFuture<*>? = null
    
    override fun onServiceConnected() {
        serviceInfo = with(AccessibilityServiceInfo()) {
            eventTypes = AccessibilityEvent.TYPES_ALL_MASK
            feedbackType = AccessibilityServiceInfo.FEEDBACK_VISUAL
            flags = AccessibilityServiceInfo.DEFAULT
            this
        }
        
        if (!allowed) notifyMissingPermissions()
        else {
            manager.addView(view, WindowManager.LayoutParams(
                    prefs.size.dpToPx(),
                    prefs.size.dpToPx(),
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT))
            
            scheduledAwake = updater.schedule(Awake(), 0, TimeUnit.SECONDS)
            scheduledStateAlternator = updater.scheduleWithFixedDelay(
                    StateAlternator(), 500, 500, TimeUnit.MILLISECONDS)
        }
    }
    
    override fun onDestroy() {
        updater.shutdown()
        manager.removeView(view)
        prefs.unregister(this)
        
        super.onDestroy()
    }
    
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (!allowed) {
            notifyMissingPermissions()
            return
        } else if (!when (event.eventType) {
            AccessibilityEvent.TYPE_VIEW_CLICKED,
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> true
            else -> false
        } || event.source == null) return
        
        debug({ "goal changed" })
        
        val info = AccessibilityNodeInfoCompat(
                AccessibilityNodeInfo.obtain(event.source))
        
        with(Rect()) {
            info.getBoundsInScreen(this)
            last.set(centerX(), top - view.height)
        }
        
        info.recycle()
        event.recycle()
        
        if (scheduledSleepWatcher?.cancel(false) != null)
            debug({ "cancelled sleep watcher" })
        if (    scheduledMovementUpdater?.isDone ?: false
                && scheduledAwake?.isDone ?: true) {
            debug({ "scheduling awake" })
            scheduledAwake = updater.schedule(Awake(), 0, TimeUnit.SECONDS)
        }
    }
    
    override fun onInterrupt() { /* NOP */ }
    
    override fun onSharedPreferenceChanged(p: SharedPreferences, key: String) {
        when (key) {
            getString(R.string.settings_screenmate) ->
                view.mate = prefs.screenMate
            getString(R.string.settings_size) -> {
                val size = prefs.size
                
                manager.updateViewLayout(
                        view,
                        with(view.layoutParams as WindowManager.LayoutParams) {
                            width = size.dpToPx()
                            height = width
                            this
                        })
                view.size = size
            }
            
            getString(R.string.settings_movement_frequency),
            getString(R.string.settings_movement_granularity),
            getString(R.string.settings_sleep_delay) -> {
                stopSelf()
                startService(intent(this))
            }
        }
    }
    
    private fun startSleepWatcher(
            watcher: SleepWatcher,
            delay: Long = prefs.sleepDelay) {
        scheduledSleepWatcher?.cancel(false)
        scheduledSleepWatcher = updater.schedule(watcher, delay, TimeUnit.SECONDS)
    }
    
    private fun notifyMissingPermissions() {
        getService<NotificationManager>(Context.NOTIFICATION_SERVICE).notify(
                0,
                NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                        .setWhen(Date().time)
                        .setContentTitle(getString(R.string.notification_permissions_title))
                        .setContentText(getString(R.string.notification_permissions_content))
                        .setContentIntent(PendingIntent.getActivity(
                                this,
                                0,
                                Intent( Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                        Uri.parse("package:$packageName")),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                        .setAutoCancel(true)
                        .build())
    }
    
    companion object {
        
        fun intent(ctx : Context) = Intent(ctx, FloatingService::class.java)
    }
    
    private inner class WindowView(context: Context) : View(context) {
        
        val mat = Matrix()
        val src = RectF()
        val dst = RectF()
        
        var mate: ScreenMate = prefs.screenMate
            set(value) {
                field = value
                view.invalidate()
            }
        var size: Int = prefs.size.dpToPx()
            set(value) {
                field = value.dpToPx()
                view.invalidate()
            }
        var state = State.MATI2
            set(value) {
                field = value
                post({ invalidate() })
            }
        
        override fun onDraw(canvas: Canvas) {
            debug({"onDraw"})
            super.onDraw(canvas)
            
            with(bitmap(mate, state)) {
                src.set(0F, 0F, width.toFloat(), height.toFloat())
                dst.set(0F, 0F, size.toFloat(), size.toFloat())
                
                mat.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER)
                
                canvas.drawBitmap(this, mat, null)
            }
        }
        
        private fun bitmap(mate: ScreenMate, state: State) = cache.getOrPut(
                mate.resolve(state),
                { mate.bitmap(resources, state) })
    }
    
    private inner class Awake() : Runnable {
        
        override fun run() {
            debug({"run"})
            
            view.state = State.MATI2
            Thread.sleep(100)
            view.state = State.AWAKE
            Thread.sleep(1000)
            view.state = State.MATI2
            
            scheduledSleepWatcher?.cancel(false)
            scheduledMovementUpdater?.cancel(false)
            scheduledMovementUpdater = updater.scheduleWithFixedDelay(
                    MovementUpdater(),
                    100,
                    prefs.movementFrequency,
                    TimeUnit.MILLISECONDS)
            debug({"scheduling movement updater"})
        }
    }
    
    private inner class MovementUpdater() : Runnable {
        
        private val finder = PathFinder(
                PathGraph(
                        0, 0,
                        displayWidth, displayHeight,
                        prefs.movementGranularity),
                PathLocation(current.x, current.y),
                PathLocation(last.x, last.y))
        private var lastMove: State? = null
        
        override fun run() {
            debug({"run"})
            
            val next = finder.next(
                    PathLocation(current.x, current.y),
                    PathLocation(last.x, last.y))
            val state = view.state.evaluate(current, Point(next.x, next.y))
            
            if (state.idle() != null) lastMove = state
            if (view.state.different(state)) view.state = state
            
            if (current != Point(next.x, next.y)) {
                debug({"moving from $current to $next"})
                handler.postAndWait {
                    manager.updateViewLayout(
                            view,
                            with(view.layoutParams as WindowManager.LayoutParams) {
                                x = next.x - displayWidth / 2
                                y = next.y - displayHeight / 2
                                current.set(next.x, next.y)
                                this
                            })
                }
            } else {
                debug({"reached $next"})
                if (scheduledMovementUpdater?.cancel(false) != null)
                    debug({"cancelled movement updater"})
                debug({"starting sleep watcher"})
                startSleepWatcher(SleepWatcher(lastMove))
            }
        }
    }
    
    private inner class StateAlternator() : Runnable {
        
        override fun run() {
            debug({"run"})
            
            view.state = view.state.alternative()
        }
    }
    
    private inner class SleepWatcher(
            private val lastMove: State? = null,
            state: State? = null) : Runnable {
        
        private val state : State
        private val repeat = Random().nextBoolean()
        
        init {
            this.state = state ?: with(lastMove?.idle()) {
                val states = mutableListOf(State.MATI3, State.KAKI1)
                (if (this == null) states else states.plus(this)).random() 
            }
        }
        
        override fun run() {
            debug({"run"})
            
            view.state = state
            
            if (state != State.SLEEP1 || state != State.SLEEP1.alternative()) {
                handler.post {
                    startSleepWatcher(
                            SleepWatcher(
                                    lastMove,
                                    if (repeat) state else State.SLEEP1),
                            prefs.sleepDelay / 2)
                }
            }
        }
    }
}
