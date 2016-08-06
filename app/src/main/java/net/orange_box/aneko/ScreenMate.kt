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

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

internal enum class ScreenMate private constructor(
        @StringRes private val title: Int) {
    
    BSD(R.string.screenmate_bsd) {
        override fun resolve(state: State) = state.bsd
        override fun resolve(state: StateMask) = state.bsd
    },
    DOG(R.string.screenmate_dog) {
        override fun resolve(state: State) = state.dog
        override fun resolve(state: StateMask) = state.dog
    },
    NEKO(R.string.screenmate_neko) {
        override fun resolve(state: State) = state.neko
        override fun resolve(state: StateMask) = state.neko
    },
    SAKURA(R.string.screenmate_sakura) {
        override fun resolve(state: State) = state.sakura
        override fun resolve(state: StateMask) = state.sakura
    },
    TOMOYO(R.string.screenmate_tomoyo) {
        override fun resolve(state: State) = state.tomoyo
        override fun resolve(state: StateMask) = state.tomoyo
    },
    TORA(R.string.screenmate_tora) {
        override fun resolve(state: State) = state.tora
        override fun resolve(state: StateMask) = state.tora
    };
    
    fun bitmap(res: Resources, state: State): Bitmap {
        val mask = resolve(state.mask())
        val bitmap = BitmapFactory.decodeResource(
                res,
                resolve(state),
                with(BitmapFactory.Options()) {
                    inMutable = mask != 0
                    this
                })
        
        if (mask == 0) return bitmap
        
        with(BitmapFactory.decodeResource(res, resolve(state.mask()))) {
            for (x in 0..width - 1) {
                for (y in 0..height - 1) {
                    if (getPixel(x, y) == -1) bitmap.setPixel(
                            x, y, Color.TRANSPARENT)
                }
            }
        }
        
        return bitmap.copy(Bitmap.Config.ARGB_8888, false)
    }
    
    @DrawableRes
    abstract fun resolve(state: State): Int
    
    @DrawableRes
    abstract fun resolve(state: StateMask): Int
    
    companion object {
        
        fun values(res: Resources): Array<String> {
            return values().map { res.getString(it.title) }.toTypedArray()
        }
    }
}
