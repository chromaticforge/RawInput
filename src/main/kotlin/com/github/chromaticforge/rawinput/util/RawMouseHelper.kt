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

    override fun mouseXYChange() {
        if (RawInputConfig.enabled) {
            this.deltaX = 0
            this.deltaY = 0

            var movement = false

            for (mouse in mouses) {
                mouse.poll()
                val f = mouse.x.pollData
                val f2 = mouse.y.pollData
                deltaX += f.toInt()
                deltaY -= f2.toInt()
                movement = movement || (f2 != 0.0f || f != 0.0f)
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