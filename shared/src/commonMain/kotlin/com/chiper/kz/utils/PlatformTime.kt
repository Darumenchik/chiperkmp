package com.chiper.kz.utils

import kotlin.random.Random

/**
 * KMP-compatible replacement for System.currentTimeMillis().
 * Uses a simulation-based approach for mock data generation.
 */
object PlatformTime {
    private val startTime = (Random.nextLong() % 1000000000000L).let {
        if (it < 0) -it else it
    }
    private val startNanos = kotlin.system.getTimeNanos()

    fun currentTimeMillis(): Long {
        // Use monotonic time to simulate wall clock since process start
        return startTime + (kotlin.system.getTimeNanos() - startNanos) / 1_000_000
    }
}
