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
                val ms = (1000L / polling)
                val ns = ((1000L % polling) * 1000000L) / polling
                sleep(ms, ns.toInt())
            }
        }
    }
}