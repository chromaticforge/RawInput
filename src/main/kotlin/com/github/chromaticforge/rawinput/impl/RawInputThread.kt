package com.github.chromaticforge.rawinput.impl

import com.github.chromaticforge.rawinput.config.RawInputConfig
import com.github.chromaticforge.rawinput.util.MouseUtils
import org.lwjgl.input.Mouse

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
            if (RawInputConfig.enabled && Mouse.isGrabbed()) {
                MouseUtils.mice.forEach {
                    if (!it.poll()) MouseUtils.rescan()

                    dx += it.x.pollData.toInt()
                    dy -= it.y.pollData.toInt()
                }

                sleep(1)
            }
        }
    }
}
