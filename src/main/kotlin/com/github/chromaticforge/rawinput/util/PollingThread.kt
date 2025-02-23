package com.github.chromaticforge.rawinput.util

import com.github.chromaticforge.rawinput.config.RawInputConfig
import com.github.chromaticforge.rawinput.util.RescanThread.mouses
import com.github.chromaticforge.rawinput.util.RescanThread.rescan

object PollingThread : Thread("Polling") {
    init {
        isDaemon = true
    }

    var dx = 0f
        get() {
            val temp = field
            dx = 0f
            return temp
        }
    var dy = 0f
        get() {
            val temp = field
            dy = 0f
            return temp
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