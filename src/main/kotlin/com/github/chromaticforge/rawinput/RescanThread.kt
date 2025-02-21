package com.github.chromaticforge.rawinput

import net.java.games.input.ControllerEnvironment
import net.java.games.input.Mouse

object RescanThread : Thread("Rescan") {
    var mouses: List<Mouse> = emptyList()

    init {
        isDaemon = true
    }

    private var backoffMs = 100L
    private const val maxBackoff = 5000L

    override fun run() {
        rescan()

        outer@ while (true) {
            for (mouse in mouses) {
                if (!mouse.poll()) {
                    rescan()
                    continue@outer
                }
            }

            if (mouses.isEmpty()) {
                sleep(backoffMs)
                backoffMs = (2L * backoffMs).coerceAtMost(maxBackoff)
                rescan()
            }

            sleep(10L)
        }
    }

    fun rescan() {
        mouses = emptyList()

        val env = Class.forName("net.java.games.input.DefaultControllerEnvironment")
            .getDeclaredConstructor().also { it.isAccessible = true }.newInstance() as ControllerEnvironment

        mouses = env.controllers.filterIsInstance<Mouse>()
    }
}