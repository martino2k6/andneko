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

import android.graphics.Point
import android.support.annotation.DrawableRes

internal enum class State private constructor(
        @DrawableRes val bsd: Int,
        @DrawableRes val dog: Int,
        @DrawableRes val neko: Int,
        @DrawableRes val sakura: Int,
        @DrawableRes val tomoyo: Int,
        @DrawableRes val tora: Int) {
    
    AWAKE(  R.drawable.awake_bsd,
            R.drawable.awake_dog,
            R.drawable.awake_neko,
            R.drawable.awake_sakura,
            R.drawable.awake_tomoyo,
            R.drawable.awake_tora) {
        override fun alternative() = this
        override fun mask() = StateMask.AWAKE
    },
    
    DOWN1(  R.drawable.down1_bsd,
            R.drawable.down1_dog,
            R.drawable.down1_neko,
            R.drawable.down1_sakura,
            R.drawable.down1_tomoyo,
            R.drawable.down1_tora) {
        override fun alternative() = DOWN2
        override fun mask() = StateMask.DOWN1
        override fun idle() = DTOGI1
    },
    
    DOWN2(  R.drawable.down2_bsd,
            R.drawable.down2_dog,
            R.drawable.down2_neko,
            R.drawable.down2_sakura,
            R.drawable.down2_tomoyo,
            R.drawable.down2_tora) {
        override fun alternative() = DOWN1
        override fun mask() = StateMask.DOWN2
        override fun idle() = DTOGI2
    },
    
    DTOGI1( R.drawable.dtogi1_bsd,
            R.drawable.dtogi1_dog,
            R.drawable.dtogi1_neko,
            R.drawable.dtogi1_sakura,
            R.drawable.dtogi1_tomoyo,
            R.drawable.dtogi1_tora) {
        override fun alternative() = DTOGI2
        override fun mask() = StateMask.DTOGI1
    },
    
    DTOGI2( R.drawable.dtogi2_bsd,
            R.drawable.dtogi2_dog,
            R.drawable.dtogi2_neko,
            R.drawable.dtogi2_sakura,
            R.drawable.dtogi2_tomoyo,
            R.drawable.dtogi2_tora) {
        override fun alternative() = DTOGI1
        override fun mask() = StateMask.DTOGI2
    },
    
    DWLEFT1(R.drawable.dwleft1_bsd,
            R.drawable.dwleft1_dog,
            R.drawable.dwleft1_neko,
            R.drawable.dwleft1_sakura,
            R.drawable.dwleft1_tomoyo,
            R.drawable.dwleft1_tora) {
        override fun alternative() = DWLEFT2
        override fun mask() = StateMask.DWLEFT1
        override fun idle() = LTOGI1
    },
    
    DWLEFT2(R.drawable.dwleft2_bsd,
            R.drawable.dwleft2_dog,
            R.drawable.dwleft2_neko,
            R.drawable.dwleft2_sakura,
            R.drawable.dwleft2_tomoyo,
            R.drawable.dwleft2_tora) {
        override fun alternative() = DWLEFT1
        override fun mask() = StateMask.DWLEFT2
        override fun idle() = LTOGI2
    },
    
    DWRIGHT1(
            R.drawable.dwright1_bsd,
            R.drawable.dwright1_dog,
            R.drawable.dwright1_neko,
            R.drawable.dwright1_sakura,
            R.drawable.dwright1_tomoyo,
            R.drawable.dwright1_tora) {
        override fun alternative() = DWRIGHT2
        override fun mask() = StateMask.DWRIGHT1
        override fun idle() = RTOGI1
    },
    
    DWRIGHT2(
            R.drawable.dwright2_bsd,
            R.drawable.dwright2_dog,
            R.drawable.dwright2_neko,
            R.drawable.dwright2_sakura,
            R.drawable.dwright2_tomoyo,
            R.drawable.dwright2_tora) {
        override fun alternative() = DWRIGHT1
        override fun mask() = StateMask.DWRIGHT2
        override fun idle() = RTOGI2
    },
    
    JARE2(  R.drawable.jare2_bsd,
            R.drawable.jare2_dog,
            R.drawable.jare2_neko,
            R.drawable.jare2_sakura,
            R.drawable.jare2_tomoyo,
            R.drawable.jare2_tora) {
        override fun alternative() = this
        override fun mask() = StateMask.JARE2
    },
    
    KAKI1(  R.drawable.kaki1_bsd,
            R.drawable.kaki1_dog,
            R.drawable.kaki1_neko,
            R.drawable.kaki1_sakura,
            R.drawable.kaki1_tomoyo,
            R.drawable.kaki1_tora) {
        override fun alternative() = KAKI2
        override fun mask() = StateMask.KAKI1
    },
    
    KAKI2(  R.drawable.kaki2_bsd,
            R.drawable.kaki2_dog,
            R.drawable.kaki2_neko,
            R.drawable.kaki2_sakura,
            R.drawable.kaki2_tomoyo,
            R.drawable.kaki2_tora) {
        override fun alternative() = KAKI1
        override fun mask() = StateMask.KAKI2
    },
    
    LEFT1(  R.drawable.left1_bsd,
            R.drawable.left1_dog,
            R.drawable.left1_neko,
            R.drawable.left1_sakura,
            R.drawable.left1_tomoyo,
            R.drawable.left1_tora) {
        override fun alternative() = LEFT2
        override fun mask() = StateMask.LEFT1
        override fun idle() = LTOGI1
    },
    
    LEFT2(  R.drawable.left2_bsd,
            R.drawable.left2_dog,
            R.drawable.left2_neko,
            R.drawable.left2_sakura,
            R.drawable.left2_tomoyo,
            R.drawable.left2_tora) {
        override fun alternative() = LEFT1
        override fun mask() = StateMask.LEFT2
        override fun idle() = LTOGI2
    },
    
    LTOGI1( R.drawable.ltogi1_bsd,
            R.drawable.ltogi1_dog,
            R.drawable.ltogi1_neko,
            R.drawable.ltogi1_sakura,
            R.drawable.ltogi1_tomoyo,
            R.drawable.ltogi1_tora) {
        override fun alternative() = LTOGI2
        override fun mask() = StateMask.LTOGI1
    },
    
    LTOGI2( R.drawable.ltogi2_bsd,
            R.drawable.ltogi2_dog,
            R.drawable.ltogi2_neko,
            R.drawable.ltogi2_sakura,
            R.drawable.ltogi2_tomoyo,
            R.drawable.ltogi2_tora) {
        override fun alternative() = LTOGI1
        override fun mask() = StateMask.LTOGI2
    },
    
    MATI2(  R.drawable.mati2_bsd,
            R.drawable.mati2_dog,
            R.drawable.mati2_neko,
            R.drawable.mati2_sakura,
            R.drawable.mati2_tomoyo,
            R.drawable.mati2_tora) {
        override fun alternative() = MATI2
        override fun mask() = StateMask.MATI2
    },
    
    MATI3(  R.drawable.mati3_bsd,
            R.drawable.mati3_dog,
            R.drawable.mati3_neko,
            R.drawable.mati3_sakura,
            R.drawable.mati3_tomoyo,
            R.drawable.mati3_tora) {
        override fun alternative() = MATI3
        override fun mask() = StateMask.MATI3
    },
    
    RIGHT1( R.drawable.right1_bsd,
            R.drawable.right1_dog,
            R.drawable.right1_neko,
            R.drawable.right1_sakura,
            R.drawable.right1_tomoyo,
            R.drawable.right1_tora) {
        override fun alternative() = RIGHT2
        override fun mask() = StateMask.RIGHT1
        override fun idle() = RTOGI1
    },
    
    RIGHT2( R.drawable.right2_bsd,
            R.drawable.right2_dog,
            R.drawable.right2_neko,
            R.drawable.right2_sakura,
            R.drawable.right2_tomoyo,
            R.drawable.right2_tora) {
        override fun alternative() = RIGHT1
        override fun mask() = StateMask.RIGHT2
        override fun idle() = RTOGI2
    },
    
    RTOGI1( R.drawable.rtogi1_bsd,
            R.drawable.rtogi1_dog,
            R.drawable.rtogi1_neko,
            R.drawable.rtogi1_sakura,
            R.drawable.rtogi1_tomoyo,
            R.drawable.rtogi1_tora) {
        override fun alternative() = RTOGI2
        override fun mask() = StateMask.RTOGI1
    },
    
    RTOGI2( R.drawable.rtogi2_bsd,
            R.drawable.rtogi2_dog,
            R.drawable.rtogi2_neko,
            R.drawable.rtogi2_sakura,
            R.drawable.rtogi2_tomoyo,
            R.drawable.rtogi2_tora) {
        override fun alternative() = RTOGI1
        override fun mask() = StateMask.RTOGI2
    },
    
    SLEEP1( R.drawable.sleep1_bsd,
            R.drawable.sleep1_dog,
            R.drawable.sleep1_neko,
            R.drawable.sleep1_sakura,
            R.drawable.sleep1_tomoyo,
            R.drawable.sleep1_tora) {
        override fun alternative() = SLEEP2
        override fun mask() = StateMask.SLEEP1
    },
    
    SLEEP2( R.drawable.sleep2_bsd,
            R.drawable.sleep2_dog,
            R.drawable.sleep2_neko,
            R.drawable.sleep2_sakura,
            R.drawable.sleep2_tomoyo,
            R.drawable.sleep2_tora) {
        override fun alternative() = SLEEP1
        override fun mask() = StateMask.SLEEP2
    },
    
    SPACE(  R.drawable.space_bsd,
            0,
            0,
            0,
            0,
            0) {
        override fun alternative() = this
        override fun mask() = StateMask.SPACE
    },
    
    UP1(    R.drawable.up1_bsd,
            R.drawable.up1_dog,
            R.drawable.up1_neko,
            R.drawable.up1_sakura,
            R.drawable.up1_tomoyo,
            R.drawable.up1_tora) {
        override fun alternative() = UP2
        override fun mask() = StateMask.UP1
        override fun idle() = UTOGI1
    },
    
    UP2(    R.drawable.up2_bsd,
            R.drawable.up2_dog,
            R.drawable.up2_neko,
            R.drawable.up2_sakura,
            R.drawable.up2_tomoyo,
            R.drawable.up2_tora) {
        override fun alternative()=  UP1
        override fun mask() = StateMask.UP2
        override fun idle() = UTOGI2
    },
    
    UPLEFT1(R.drawable.upleft1_bsd,
            R.drawable.upleft1_dog,
            R.drawable.upleft1_neko,
            R.drawable.upleft1_sakura,
            R.drawable.upleft1_tomoyo,
            R.drawable.upleft1_tora) {
        override fun alternative() = UPLEFT2
        override fun mask() = StateMask.UPLEFT1
        override fun idle() = LTOGI1
    },
    
    UPLEFT2(R.drawable.upleft2_bsd,
            R.drawable.upleft2_dog,
            R.drawable.upleft2_neko,
            R.drawable.upleft2_sakura,
            R.drawable.upleft2_tomoyo,
            R.drawable.upleft2_tora) {
        override fun alternative() = UPLEFT1
        override fun mask() = StateMask.UPLEFT2
        override fun idle() = LTOGI2
    },
    
    UPRIGHT1(
            R.drawable.upright1_bsd,
            R.drawable.upright1_dog,
            R.drawable.upright1_neko,
            R.drawable.upright1_sakura,
            R.drawable.upright1_tomoyo,
            R.drawable.upright1_tora) {
        override fun alternative() = UPRIGHT2
        override fun mask() = StateMask.UPRIGHT1
        override fun idle() = RTOGI1
    },
    
    UPRIGHT2(
            R.drawable.upright2_bsd,
            R.drawable.upright2_dog,
            R.drawable.upright2_neko,
            R.drawable.upright2_sakura,
            R.drawable.upright2_tomoyo,
            R.drawable.upright2_tora) {
        override fun alternative() = UPRIGHT1
        override fun mask() = StateMask.UPRIGHT2
        override fun idle() = RTOGI2
    },
    
    UTOGI1( R.drawable.utogi1_bsd,
            R.drawable.utogi1_dog,
            R.drawable.utogi1_neko,
            R.drawable.utogi1_sakura,
            R.drawable.utogi1_tomoyo,
            R.drawable.utogi1_tora) {
        override fun alternative() = UTOGI2
        override fun mask() = StateMask.UTOGI1
    },
    
    UTOGI2( R.drawable.utogi2_bsd,
            R.drawable.utogi2_dog,
            R.drawable.utogi2_neko,
            R.drawable.utogi2_sakura,
            R.drawable.utogi2_tomoyo,
            R.drawable.utogi2_tora) {
        override fun alternative() = UTOGI1
        override fun mask() = StateMask.UTOGI2
    };
    
    init {
        check(bsd, dog, neko, sakura, tomoyo, tora)
    }
    
    abstract fun alternative(): State
    
    abstract fun mask(): StateMask
    
    open fun idle(): State? = null
    
    fun different(next: State): Boolean {
        return this != next && this != next.alternative()
    }
    
    fun evaluate(current: Point, next: Point): State {
        return if (next.y > current.y)
            if (next.x > current.x) DWRIGHT1
            else if (next.x < current.x) DWLEFT1
            else return DOWN1
        else if (next.y < current.y)
            if (next.x > current.x) UPRIGHT1
            else if (next.x < current.x) UPLEFT1
            else UP1
        else if (next.x > current.x) RIGHT1
        else if (next.x < current.x) LEFT1
        else if (this == SLEEP1 || this == SLEEP1.alternative()) this
        else MATI2
    }
    
    private fun check(@DrawableRes vararg params: Int) {
        for ((i, param) in params.withIndex()) {
            if (param == 0) {
                warn({"No drawable for " + this})
                continue
            }
            
            for ((j, jparam) in params.withIndex()) {
                if (i != j && param == jparam)
                    illegal("Same drawables for " + this)
            }
        }
    }
}
