package com.chiper.kz.utils

import kotlinx.datetime.Clock

object PlatformTime {
    fun currentTimeMillis(): Long =
        Clock.System.now().toEpochMilliseconds()
}
