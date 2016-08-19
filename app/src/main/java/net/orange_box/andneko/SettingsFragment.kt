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

package net.orange_box.andneko

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.PreferenceFragment
import android.provider.Settings
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager

class SettingsFragment : PreferenceFragment() {
    
    private val enabled by lazy {
        findPreference(getString(R.string.settings_enabled)) as CheckBoxPreference
    }
    private val permissions by lazy {
        with(findPreference(getString(R.string.settings_permissions))) {
            setOnPreferenceClickListener {
                activity.startActivity(Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$activity.packageName")))
                true
            }
            this as CheckBoxPreference
        }
    }
    private val screenMate by lazy { findPreference(getString(R.string.settings_screenmate)) }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        addPreferencesFromResource(R.xml.settings)
        
        screenMate.setOnPreferenceClickListener({
            val prefs = Preferences(activity)
            
            AlertDialog.Builder(activity)
                    .setTitle(screenMate.title)
                    .setSingleChoiceItems(
                            ScreenMate.values(resources),
                            prefs.screenMate.ordinal) { dialog, which ->
                        dialog.dismiss()
                        prefs.screenMate = ScreenMate.values()[which]
            }.show()
            
            true
        })
    }
    
    override fun onResume() {
        super.onResume()
        
        enabled.isChecked = activity
                .getService<AccessibilityManager>(Context.ACCESSIBILITY_SERVICE)
                .getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK)
                .firstOrNull { it.id.endsWith(FloatingService::class.java.simpleName) } != null
        
        if (SettingsCompat.canDrawOverlays(activity)) {
            permissions.isChecked = true
            permissions.isEnabled = false
        } else {
            permissions.isChecked = false
            permissions.isEnabled = true
        }
    }
}
