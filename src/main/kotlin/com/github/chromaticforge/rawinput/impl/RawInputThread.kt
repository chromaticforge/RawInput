package com.github.chromaticforge.rawinput.impl

import com.github.chromaticforge.rawinput.RawInputMod
import net.java.games.input.ControllerEnvironment
import net.java.games.input.Mouse
import java.util.concurrent.atomic.AtomicInteger

object RawInputThread : Thread("Raw Mouse Input") {
    init {
        isDaemon = true
    }

    val dx: AtomicInteger = AtomicInteger(0)
    val dy: AtomicInteger = AtomicInteger(0)

    var mice: List<Mouse> = emptyList()

    override fun run() {
        rescan()

        while (true) {
            mice.forEach {
                if (!it.poll()) rescan()

                dx.addAndGet(it.x.pollData.toInt())
                dy.addAndGet(-it.y.pollData.toInt())
            }

            sleep(1)
        }
    }

    fun rescan() {
        val env = Class.forName("net.java.games.input.${RawInputMod.environment}")
            .getDeclaredConstructor().also { it.isAccessible = true }.newInstance() as ControllerEnvironment

        mice = env.controllers.filterIsInstance<Mouse>()
    }

    fun reset() {
        dx.set(0)
        dy.set(0)
    }
}
