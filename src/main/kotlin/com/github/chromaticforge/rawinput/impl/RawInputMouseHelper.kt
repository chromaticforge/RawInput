package com.github.chromaticforge.rawinput.impl

import net.minecraft.client.MouseInput
import org.lwjgl.input.Mouse
import kotlin.math.abs

class RawInputMouseHelper : MouseInput() {
    private var fails = 0

    init {
        RawInputThread.start()
    }

    override fun lockMouse() {
        RawInputThread.reset()
        super.lockMouse()
    }

    override fun updateMouse() {
        if (!RawInputThread.isAlive) {
            RawInputThread.start()
        }

        if (RawInputThread.mice.isNotEmpty() && RawInputThread.isAlive) {
            var movement = false

            x = RawInputThread.dx.getAndSet(0)
            y = RawInputThread.dy.getAndSet(0)

            movement = movement || (x != 0 || y != 0)

            if (!(abs(Mouse.getDX()) <= 5 && abs(Mouse.getDY()) <= 5 || movement)) {
                if (fails++ > 5) {
                    RawInputThread.rescan()
                }
            } else if (movement) {
                fails = 0
            }
        } else {
            super.updateMouse()
        }
    }
}