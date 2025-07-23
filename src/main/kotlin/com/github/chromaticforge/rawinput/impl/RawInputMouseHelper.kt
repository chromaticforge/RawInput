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
        if (RawInputThread.state == Thread.State.NEW) {
            RawInputThread.start()
        }

        if (RawInputMod.config.enabled && RawInputThread.mice.isNotEmpty() && RawInputThread.isAlive) {
            var movement = false

            deltaX = RawInputThread.dx.getAndSet(0)
            deltaY = RawInputThread.dy.getAndSet(0)

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