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

internal class PathGraph(
        private val left: Int,
        private val top: Int,
        width: Int,
        height: Int,
        private val step: Int) {
    
    private val right: Int
    private val bottom: Int
    
    private val weights = mutableMapOf<PathLocation, Int>()
    
    init {
        right = left + width
        bottom = top + height
    }
    
    fun neighbours(loc: PathLocation, goal: PathLocation): List<PathLocation> {
        return with((-step..step step step).map { x ->
            (-step..step step step).map { y ->
                PathLocation(loc.x + x, loc.y + y)
            }
        }.reduce { list1, list2 ->
            list1 + list2
        }.filter {
            (it != loc)
                    && (it.x >= left && it.x <= right)
                    && (it.y >= top && it.y <= bottom)
        }) {
            val dX = loc.x - goal.x
            val dY = loc.y - goal.y
            if (dX.abs() < step && dY.abs() < step)
                plus(goal)
            else this
        }
    }
    
    fun cost(from: PathLocation, to: PathLocation): Int {
        return weights.getOrElse(to, { 1 })
    }
}
