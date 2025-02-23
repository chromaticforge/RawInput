package com.github.chromaticforge.rawinput.util

import com.github.chromaticforge.rawinput.config.RawInputConfig
import net.java.games.input.ControllerEnvironment
import net.java.games.input.Mouse
import kotlin.math.abs

object RescanThread : Thread("Rescan") {
    var mouses: List<Mouse> = emptyList()

    init {
        isDaemon = true
    }

    override fun run() {
        rescan()

        var fails = 0

        outer@while (true) {
            for (mouse in mouses) {
                if (PollingThread.dx == 0.0f && PollingThread.dy == 0.0f
                    && abs(org.lwjgl.input.Mouse.getDX()) > 3 && abs(org.lwjgl.input.Mouse.getDY()) > 3)
                {
                    if (++fails > 3) rescan()
                } else {
                    fails = 0
                }
            }

            if (mouses.isEmpty()) {
                rescan()
                sleep(2500L)
            }

            sleep(10L)
        }
    }

    fun rescan() {
        mouses = emptyList()

        val plugin = when (RawInputConfig.mode) {
            1 -> "DirectInputEnvironmentPlugin"
            2 -> "RawInputEnvironmentPlugin"
            3 -> "DirectAndRawInputEnvironmentPlugin"
            else -> "DefaultControllerEnvironment"
        }

        val env = Class.forName("net.java.games.input.$plugin")
            .getDeclaredConstructor().also { it.isAccessible = true }.newInstance() as ControllerEnvironment

        mouses = env.controllers.filterIsInstance<Mouse>()
    }
}