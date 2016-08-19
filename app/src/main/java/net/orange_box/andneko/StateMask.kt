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

import android.support.annotation.DrawableRes

internal enum class StateMask private constructor(
        @DrawableRes val bsd: Int,
        @DrawableRes val dog: Int,
        @DrawableRes val neko: Int,
        @DrawableRes val sakura: Int,
        @DrawableRes val tomoyo: Int,
        @DrawableRes val tora: Int) {
    
    AWAKE(  R.drawable.awake_bsd_mask,
            R.drawable.awake_dog_mask,
            R.drawable.awake_neko_mask,
            R.drawable.awake_sakura_mask,
            R.drawable.awake_tomoyo_mask,
            0),
    
    DOWN1(  R.drawable.down1_bsd_mask,
            R.drawable.down1_dog_mask,
            R.drawable.down1_neko_mask,
            R.drawable.down1_sakura_mask,
            R.drawable.down1_tomoyo_mask,
            0),
    
    DOWN2(  R.drawable.down2_bsd_mask,
            R.drawable.down2_dog_mask,
            R.drawable.down2_neko_mask,
            R.drawable.down2_sakura_mask,
            R.drawable.down2_tomoyo_mask,
            0),
    
    DTOGI1( R.drawable.dtogi1_bsd_mask,
            R.drawable.dtogi1_dog_mask,
            R.drawable.dtogi1_neko_mask,
            R.drawable.dtogi1_sakura_mask,
            R.drawable.dtogi1_tomoyo_mask,
            0),
    
    DTOGI2( R.drawable.dtogi2_bsd_mask,
            R.drawable.dtogi2_dog_mask,
            R.drawable.dtogi2_neko_mask,
            R.drawable.dtogi2_sakura_mask,
            R.drawable.dtogi2_tomoyo_mask,
            0),
    
    DWLEFT1(R.drawable.dwleft1_bsd_mask,
            R.drawable.dwleft1_dog_mask,
            R.drawable.dwleft1_neko_mask,
            R.drawable.dwleft1_sakura_mask,
            R.drawable.dwleft1_tomoyo_mask,
            0),
    
    DWLEFT2(R.drawable.dwleft2_bsd_mask,
            R.drawable.dwleft2_dog_mask,
            R.drawable.dwleft2_neko_mask,
            R.drawable.dwleft2_sakura_mask,
            R.drawable.dwleft2_tomoyo_mask,
            0),
    
    DWRIGHT1(
            R.drawable.dwright1_bsd_mask,
            R.drawable.dwright1_dog_mask,
            R.drawable.dwright1_neko_mask,
            R.drawable.dwright1_sakura_mask,
            R.drawable.dwright1_tomoyo_mask,
            0),
    
    DWRIGHT2(
            R.drawable.dwright2_bsd_mask,
            R.drawable.dwright2_dog_mask,
            R.drawable.dwright2_neko_mask,
            R.drawable.dwright2_sakura_mask,
            R.drawable.dwright2_tomoyo_mask,
            0),
    
    JARE2(  R.drawable.jare2_bsd_mask,
            R.drawable.jare2_dog_mask,
            R.drawable.jare2_neko_mask,
            R.drawable.jare2_sakura_mask,
            R.drawable.jare2_tomoyo_mask,
            0),
    
    KAKI1(  R.drawable.kaki1_bsd_mask,
            R.drawable.kaki1_dog_mask,
            R.drawable.kaki1_neko_mask,
            R.drawable.kaki1_sakura_mask,
            R.drawable.kaki1_tomoyo_mask,
            0),
    
    KAKI2(  R.drawable.kaki2_bsd_mask,
            R.drawable.kaki2_dog_mask,
            R.drawable.kaki2_neko_mask,
            R.drawable.kaki2_sakura_mask,
            R.drawable.kaki2_tomoyo_mask,
            0),
    
    LEFT1(  R.drawable.left1_bsd_mask,
            R.drawable.left1_dog_mask,
            R.drawable.left1_neko_mask,
            R.drawable.left1_sakura_mask,
            R.drawable.left1_tomoyo_mask,
            0),
    
    LEFT2(  R.drawable.left2_bsd_mask,
            R.drawable.left2_dog_mask,
            R.drawable.left2_neko_mask,
            R.drawable.left2_sakura_mask,
            R.drawable.left2_tomoyo_mask,
            0),
    
    LTOGI1( R.drawable.ltogi1_bsd_mask,
            R.drawable.ltogi1_dog_mask,
            R.drawable.ltogi1_neko_mask,
            R.drawable.ltogi1_sakura_mask,
            R.drawable.ltogi1_tomoyo_mask,
            0),
    
    LTOGI2( R.drawable.ltogi2_bsd_mask,
            R.drawable.ltogi2_dog_mask,
            R.drawable.ltogi2_neko_mask,
            R.drawable.ltogi2_sakura_mask,
            R.drawable.ltogi2_tomoyo_mask,
            0),
    
    MATI2(  R.drawable.mati2_bsd_mask,
            R.drawable.mati2_dog_mask,
            R.drawable.mati2_neko_mask,
            R.drawable.mati2_sakura_mask,
            R.drawable.mati2_tomoyo_mask,
            0),
    
    MATI3(  R.drawable.mati3_bsd_mask,
            R.drawable.mati3_dog_mask,
            R.drawable.mati3_neko_mask,
            R.drawable.mati3_sakura_mask,
            R.drawable.mati3_tomoyo_mask,
            0),
    
    RIGHT1( R.drawable.right1_bsd_mask,
            R.drawable.right1_dog_mask,
            R.drawable.right1_neko_mask,
            R.drawable.right1_sakura_mask,
            R.drawable.right1_tomoyo_mask,
            0),
    
    RIGHT2( R.drawable.right2_bsd_mask,
            R.drawable.right2_dog_mask,
            R.drawable.right2_neko_mask,
            R.drawable.right2_sakura_mask,
            R.drawable.right2_tomoyo_mask,
            0),
    
    RTOGI1( R.drawable.rtogi1_bsd_mask,
            R.drawable.rtogi1_dog_mask,
            R.drawable.rtogi1_neko_mask,
            R.drawable.rtogi1_sakura_mask,
            R.drawable.rtogi1_tomoyo_mask,
            0),
    
    RTOGI2( R.drawable.rtogi2_bsd_mask,
            R.drawable.rtogi2_dog_mask,
            R.drawable.rtogi2_neko_mask,
            R.drawable.rtogi2_sakura_mask,
            R.drawable.rtogi2_tomoyo_mask,
            0),
    
    SLEEP1( R.drawable.sleep1_bsd_mask,
            R.drawable.sleep1_dog_mask,
            R.drawable.sleep1_neko_mask,
            R.drawable.sleep1_sakura_mask,
            R.drawable.sleep1_tomoyo_mask,
            0),
    
    SLEEP2( R.drawable.sleep2_bsd_mask,
            R.drawable.sleep2_dog_mask,
            R.drawable.sleep2_neko_mask,
            R.drawable.sleep2_sakura_mask,
            R.drawable.sleep2_tomoyo_mask,
            0),

    SPACE(  R.drawable.space_bsd_mask,
            0,
            R.drawable.space_neko_mask,
            0,
            0,
            0),
    
    UP1(    R.drawable.up1_bsd_mask,
            R.drawable.up1_dog_mask,
            R.drawable.up1_neko_mask,
            R.drawable.up1_sakura_mask,
            R.drawable.up1_tomoyo_mask,
            0),
    
    UP2(    R.drawable.up2_bsd_mask,
            R.drawable.up2_dog_mask,
            R.drawable.up2_neko_mask,
            R.drawable.up2_sakura_mask,
            R.drawable.up2_tomoyo_mask,
            0),
    
    UPLEFT1(R.drawable.upleft1_bsd_mask,
            R.drawable.upleft1_dog_mask,
            R.drawable.upleft1_neko_mask,
            R.drawable.upleft1_sakura_mask,
            R.drawable.upleft1_tomoyo_mask,
            0),
    
    UPLEFT2(R.drawable.upleft2_bsd_mask,
            R.drawable.upleft2_dog_mask,
            R.drawable.upleft2_neko_mask,
            R.drawable.upleft2_sakura_mask,
            R.drawable.upleft2_tomoyo_mask,
            0),
    
    UPRIGHT1(
            R.drawable.upright1_bsd_mask,
            R.drawable.upright1_dog_mask,
            R.drawable.upright1_neko_mask,
            R.drawable.upright1_sakura_mask,
            R.drawable.upright1_tomoyo_mask,
            0),
    
    UPRIGHT2(
            R.drawable.upright2_bsd_mask,
            R.drawable.upright2_dog_mask,
            R.drawable.upright2_neko_mask,
            R.drawable.upright2_sakura_mask,
            R.drawable.upright2_tomoyo_mask,
            0),
    
    UTOGI1( R.drawable.utogi1_bsd_mask,
            R.drawable.utogi1_dog_mask,
            R.drawable.utogi1_neko_mask,
            R.drawable.utogi1_sakura_mask,
            R.drawable.utogi1_tomoyo_mask,
            0),
    
    UTOGI2( R.drawable.utogi2_bsd_mask,
            R.drawable.utogi2_dog_mask,
            R.drawable.utogi2_neko_mask,
            R.drawable.utogi2_sakura_mask,
            R.drawable.utogi2_tomoyo_mask,
            0)
}
