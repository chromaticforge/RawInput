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
            deltaX = RawInputThread.dx.getAndSet(0)
            deltaY = RawInputThread.dy.getAndSet(0)

            tryRescan(
                deltaX != 0 || deltaY != 0,
                abs(Mouse.getDX()) <= 5 && abs(Mouse.getDY()) <= 5
            )
        } else {
            super.mouseXYChange()
        }
    }

    private fun tryRescan(still: Boolean, movement: Boolean) {
        if (!still && !movement) {
            if (++fails > 5) {
                RawInputThread.rescan()
                fails = 0
            }
        } else if (movement) {
            fails = 0
        }
    }
}