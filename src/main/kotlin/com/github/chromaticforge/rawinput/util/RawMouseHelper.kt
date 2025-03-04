package com.github.chromaticforge.rawinput.util

import com.github.chromaticforge.rawinput.config.RawInputConfig
import net.minecraft.util.MouseHelper

class RawMouseHelper : MouseHelper() {
    init {
        RescanThread.start()
        PollingThread.start()
    }

    override fun grabMouseCursor() {
        super.grabMouseCursor()
        PollingThread.dx = 0f
        PollingThread.dy = 0f
    }

    override fun mouseXYChange() {
        if (RawInputConfig.enabled) {
            deltaX = PollingThread.dx.toInt()
            deltaY = PollingThread.dy.toInt()
        } else {
            super.mouseXYChange()
        }
    }
}