package com.github.chromaticforge.rawinput.util

import com.github.chromaticforge.rawinput.config.RawInputConfig
import com.github.chromaticforge.rawinput.util.RescanThread.mouses
import com.github.chromaticforge.rawinput.util.RescanThread.rescan

object PollingThread : Thread("Polling") {
    init {
        isDaemon = true
    }

    var prevDx = 0f
    var prevDy = 0f

    var dx = 0f
        get() {
            prevDx = field
            dx = 0f
            return prevDx
        }
    var dy = 0f
        get() {
            prevDy = field
            dy = 0f
            return prevDy
        }

    override fun run() {
        while (true) {
            if (RawInputConfig.enabled) {
                for (mouse in mouses) {
                    if (!mouse.poll()) rescan()

                    dx += mouse.x.pollData
                    dy -= mouse.y.pollData
                }

                val polling = RawInputConfig.polling.toLong()
                val (ms, ns) = (1000L * 1000000L / polling).let { it / 1000000 to it % 1000000 }
                sleep(ms, ns.toInt())
            }
        }
    }
}