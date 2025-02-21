package com.github.chromaticforge.rawinput.util

import com.github.chromaticforge.rawinput.RescanThread
import net.minecraft.util.MouseHelper

class RawMouseHelper : MouseHelper() {
    init {
        RescanThread.start()
    }

    override fun mouseXYChange() {
        deltaX = 0
        deltaY = 0

        for (mouse in RescanThread.mouses) {
            mouse.poll()
            deltaX += mouse.x.pollData.toInt()
            deltaY -= mouse.y.pollData.toInt()
        }
    }
}