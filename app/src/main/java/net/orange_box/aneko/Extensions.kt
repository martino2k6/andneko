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

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Handler
import android.os.SystemClock
import android.util.DisplayMetrics
import android.util.Log
import java.util.*
import java.util.concurrent.CountDownLatch

// *

private fun apiCheck(version: Int, check: (Int, Int) -> Boolean) =
        check.invoke(Build.VERSION.SDK_INT, version)

fun api23plus() = apiCheck(Build.VERSION_CODES.M, { x, y -> x >= y })

fun illegal(msg: String) { throw IllegalArgumentException(msg) }

fun time() = SystemClock.elapsedRealtime()

// Any

fun Any.tag() = javaClass.simpleName

fun Any.debug(msg: () -> String, tag: String = tag()) {
    if (BuildConfig.DEBUG) Log.d(tag, msg.invoke())
}

fun Any.warn(msg: () -> String, tag: String = tag()) {
    Log.w(tag, msg.invoke())
}

// Context

inline fun <reified T> Context.getService(name: String) = getSystemService(name) as T

// Handler

fun Handler.postAndWait(r: () -> Unit) {
    val latch = CountDownLatch(1)
    post({
        r.invoke()
        latch.countDown()
    })
    latch.await()
}

// Int

fun Int.abs() = Math.abs(this)
fun Int.dpToPx() = this / (Resources.getSystem().displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

// List

fun <T> List<T>.random() = get(Random().nextInt(count()))
