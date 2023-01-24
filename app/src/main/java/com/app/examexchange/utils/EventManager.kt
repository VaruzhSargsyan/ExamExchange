package com.app.examexchange.utils

import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/*
 * The event generator which is able to call the block after about 5 seconds
 * Used Scheduled Thread Pool which is more comfortable to control the time delays
 * and tasks in in pool.
 */
class EventManager {
    private val threadPool = ScheduledThreadPoolExecutor(1)
    private var delayInSeconds: Long = 5
    private lateinit var block: () -> Unit
    
    fun setup(delayInSeconds: Long = 5, block: () -> Unit) {
        this.block = block
        this.delayInSeconds = delayInSeconds
    }
    
    fun prepareNextCall() {
        threadPool.schedule(Runnable(block), delayInSeconds, TimeUnit.SECONDS)
    }
    
    fun stop() {
        threadPool.shutdownNow()
    }
}
