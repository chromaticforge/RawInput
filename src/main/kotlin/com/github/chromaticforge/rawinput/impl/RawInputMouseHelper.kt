package com.github.chromaticforge.rawinput.impl

import com.github.chromaticforge.rawinput.RawInputMod
import net.minecraft.util.MouseHelper
import org.lwjgl.input.Mouse
import kotlin.math.abs

class RawInputMouseHelper : MouseHelper() {
    private var fails = 0

    init {
        RawInputThread.start()
    }

    override fun grabMouseCursor() {
        RawInputThread.reset()
        super.grabMouseCursor()
    }

    override fun mouseXYChange() {
        if (RawInputMod.config.enabled && RawInputThread.mice.isNotEmpty()) {
            var movement = false

            deltaX = RawInputThread.dx.get()
            deltaY = RawInputThread.dy.get()

            movement = movement || (deltaX != 0 || deltaY != 0)

            if (!(abs(Mouse.getDX()) <= 5 && abs(Mouse.getDY()) <= 5 || movement)) {
                if (fails++ > 5) {
                    RawInputThread.rescan()
                }
            } else if (movement) {
                fails = 0
            }
        } else {
            super.mouseXYChange()
        }
    }
}