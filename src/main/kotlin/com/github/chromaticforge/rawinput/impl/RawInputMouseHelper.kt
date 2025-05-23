package com.github.chromaticforge.rawinput.impl

import com.github.chromaticforge.rawinput.config.RawInputConfig
import com.github.chromaticforge.rawinput.util.MouseUtils
import net.minecraft.util.MouseHelper
import org.apache.commons.lang3.SystemUtils
import org.lwjgl.input.Mouse
import kotlin.math.abs

class RawInputMouseHelper : MouseHelper() {
    private var fails = 0

    init {
        MouseUtils.rescan()
        RawInputThread.start()
    }

    override fun mouseXYChange() {
        if (RawInputConfig.enabled && MouseUtils.supported() && SystemUtils.IS_OS_WINDOWS && MouseUtils.mice.isNotEmpty()) {
            var movement = false

            deltaX = RawInputThread.dx
            deltaY = RawInputThread.dy

            movement = movement || (deltaX != 0 || deltaY != 0)

            if (!(abs(Mouse.getDX()) <= 5 && abs(Mouse.getDY()) <= 5 || movement)) {
                if (fails++ > 5) {
                    MouseUtils.rescan()
                }
            } else if (movement) {
                fails = 0
            }
        } else {
            if (RawInputConfig.enabled && !MouseUtils.supported()) {
                RawInputConfig.enabled = false
            }

            super.mouseXYChange()
        }
    }
}