package com.github.chromaticforge.rawinput.input

import com.github.chromaticforge.rawinput.config.RawInputConfig
import net.minecraft.util.MouseHelper
import org.lwjgl.input.Mouse
import kotlin.math.abs

class RawInputMouseHelper : MouseHelper() {
    private var fails = 0

    init {
        RawInputPoller.start()
    }

    override fun grabMouseCursor() {
        RawInputPoller.reset()
        super.grabMouseCursor()
    }

    override fun mouseXYChange() {
        if (RawInputConfig.enabled) {
            val drained = mutableListOf<Pair<Int, Int>>()
            RawInputPoller.buffer.drainTo(drained)

            deltaX = drained.sumOf { it.first }
            deltaY = drained.sumOf { it.second }

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
                RawInputPoller.requestRescan()
                fails = 0
            }
        } else if (movement) {
            fails = 0
        }
    }
}
