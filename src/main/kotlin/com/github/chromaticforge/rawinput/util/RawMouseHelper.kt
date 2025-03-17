package com.github.chromaticforge.rawinput.util

import com.github.chromaticforge.rawinput.config.RawInputConfig
import net.minecraft.util.MouseHelper
import org.lwjgl.input.Mouse
import kotlin.math.abs

class RawMouseHelper : MouseHelper() {
    private var fails = 0

    init {
        rescan()
    }

    override fun grabMouseCursor() {
        // Poll each mouse to reset deltas.
        mouses.forEach { it.poll() }
        super.grabMouseCursor()
    }

    override fun mouseXYChange() {
        if (RawInputConfig.enabled) {
            var movement = false
            deltaX = 0
            deltaY = 0

            for (mouse in mouses) {
                mouse.poll()
                deltaX += mouse.x.pollData.toInt()
                deltaY -= mouse.y.pollData.toInt()
                movement = movement || (deltaX != 0 || deltaY != 0)
            }

            if (!(abs(Mouse.getDX()) <= 5 && abs(Mouse.getDY()) <= 5 || movement)) {
                if (fails++ > 5) {
                    rescan()
                }
            } else if (movement) {
                fails = 0
            }
        } else {
            super.mouseXYChange()
        }
    }
}