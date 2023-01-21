package com.app.examexchange.utils

import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

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
