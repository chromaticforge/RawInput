package com.github.chromaticforge.rawinput.util

import com.github.chromaticforge.rawinput.config.RawInputConfig
import net.minecraft.util.MouseHelper
import org.apache.commons.lang3.SystemUtils
import org.lwjgl.input.Mouse
import kotlin.math.abs

class RawInputMouseHelper : MouseHelper() {
    private var fails = 0

    init {
        rescan()
        RawInputThread.start()
    }

    override fun grabMouseCursor() {
        if (RawInputConfig.enabled) mouses.forEach { it.poll() }

        super.grabMouseCursor()
    }

    override fun mouseXYChange() {
        if (RawInputConfig.enabled && SystemUtils.IS_OS_WINDOWS) {
            var movement = false

            deltaX = RawInputThread.dx
            deltaY = RawInputThread.dy

            movement = movement || (deltaX != 0 || deltaY != 0)

            if (!(abs(Mouse.getDX()) <= 5 && abs(Mouse.getDY()) <= 5 || movement)) {
                if (fails++ > 5) {
                    rescan()
                }
            } else if (movement) {
                fails = 0
            }
        } else {
            if (RawInputConfig.enabled) {
                RawInputConfig.enabled = false
            }

            super.mouseXYChange()
        }
    }
}