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
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.res.Resources
import android.preference.PreferenceManager
import android.content.SharedPreferences.OnSharedPreferenceChangeListener as Listener

internal class Preferences(context: Context) {
    
    private val prefs: SharedPreferences
    private val res: Resources
    
    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        res = context.resources
    }
    
    var screenMate: ScreenMate
        get() = ScreenMate.valueOf(prefs.getString(
                res.getString(R.string.settings_screenmate),
                ScreenMate.NEKO.name))
        set(value) = edit {
            putString(res.getString(R.string.settings_screenmate), value.name)
        }
    
    val size: Int
        get() = Integer.parseInt(prefs.getString(
                res.getString(R.string.settings_size),
                res.getString(R.string.settings_size_default)))
    
    val movementFrequency: Long
        get() = prefs.getString(
                res.getString(R.string.settings_movement_frequency),
                res.getString(R.string.settings_movement_frequency_default))
                .toLong()
    
    val movementGranularity: Int
        get() = prefs.getString(
                res.getString(R.string.settings_movement_granularity),
                res.getString(R.string.settings_movement_granularity_default))
                .toInt()
    
    val sleepDelay: Long
        get() = prefs.getString(
                res.getString(R.string.settings_sleep_delay),
                res.getString(R.string.settings_sleep_delay_default))
                .toLong()
    
    fun register(listener: Listener): Preferences {
        prefs.registerOnSharedPreferenceChangeListener(listener)
        return this
    }
    
    fun unregister(listener: Listener): Preferences {
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
        return this
    }
    
    private fun edit(block: Editor.() -> Unit) {
        with(prefs.edit()) {
            block.invoke(this)
            apply()
        }
    }
}
