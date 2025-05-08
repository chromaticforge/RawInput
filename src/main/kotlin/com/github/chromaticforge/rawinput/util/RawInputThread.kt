package com.github.chromaticforge.rawinput.util

import com.github.chromaticforge.rawinput.config.RawInputConfig

object RawInputThread : Thread("RawInput") {
    init {
        isDaemon = true
    }

    var dx = 0
        get() {
            val value = field
            field = 0
            return value
        }

    var dy = 0
        get() {
            val value = field
            field = 0
            return value
        }

    override fun run() {
        while (true) {
            if (RawInputConfig.enabled) {
                mouses.forEach {
                    if (!it.poll()) rescan()

                    dx += it.x.pollData.toInt()
                    dy -= it.y.pollData.toInt()
                }

                sleep(1)
            }
        }
    }
}
