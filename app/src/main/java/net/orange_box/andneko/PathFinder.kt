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

import java.util.*

internal class PathFinder(
        private val graph: PathGraph,
        private var start: PathLocation,
        private var goal: PathLocation) {
    
    private val open = PriorityQueue<WeightedLocation>()
    private val cameFrom = mutableMapOf<PathLocation, PathLocation?>()
    private val costSoFar = mutableMapOf<PathLocation, Int>()
    
    fun next(now: PathLocation, to: PathLocation) : PathLocation {
        if (now == to) return to
        
        val timeBefore = time()
        debug({"finding from $now to $to"})
        
        start = now
        if (to != goal || start !in cameFrom) {
            goal = to
            
            open.clear()
            cameFrom.clear()
            costSoFar.clear()
        } else return with(path().drop(1).firstOrNull() ?: now) {
                debug({"found cached $this in ${time() - timeBefore}ms"})
                this
        }
        
        open.add(WeightedLocation(start, 0))
        cameFrom.put(start, null)
        costSoFar.put(start, 0)
        
        while (open.isNotEmpty()) {
            val current = open.poll()
            
            if (current.loc == goal) break
            
            for (next in graph.neighbours(current.loc, goal)) {
                val newCost = costSoFar[current.loc] ?: 0 + graph.cost(
                        current.loc, next)
                
                if (next !in costSoFar || newCost < costSoFar[next] ?: 0) {
                    costSoFar.put(next, newCost)
                    open.add(WeightedLocation(
                            next, newCost + heuristic(next, goal)))
                    cameFrom.put(next, current.loc)
                }
            }
        }
        
        return with(path().drop(1).firstOrNull() ?: now) {
            debug({"found $this in ${time() - timeBefore}ms"})
            this
        }
    }
    
    private fun heuristic(a: PathLocation, b: PathLocation): Int {
        return (a.x - b.x).abs() + (a.y - b.y).abs()
    }
    
    private fun path(): List<PathLocation> {
        var current = goal
        val path = mutableListOf(current)
        
        while (current != start) {
            current = cameFrom[current]!!
            path.add(current)
        }
        
        path.reverse()
        return path
    }
    
    internal class WeightedLocation(
            val loc: PathLocation,
            val weight: Int) : Comparable<WeightedLocation> {
        
        override fun compareTo(other: WeightedLocation): Int {
            return weight.compareTo(other.weight)
        }
    }
}
