package com.github.chromaticforge.rawinput.impl

import cc.polyfrost.oneconfig.utils.Notifications
import com.github.chromaticforge.rawinput.RawInputMod
import com.github.chromaticforge.rawinput.util.EnvironmentFactory
import net.java.games.input.Mouse
import java.util.concurrent.atomic.AtomicInteger

object RawInputThread : Thread("Polling Thread") {
    init {
        isDaemon = true
    }

    val dx: AtomicInteger = AtomicInteger(0)
    val dy: AtomicInteger = AtomicInteger(0)

    var mice: List<Mouse> = emptyList()

    override fun run() {
        rescan()

        while (true) {
            if (RawInputMod.config.enabled) {
                mice.parallelStream().forEach {
                    if (!it.poll()) rescan()

                    dx.addAndGet(it.x.pollData.toInt())
                    dy.addAndGet(-it.y.pollData.toInt())
                }

                sleep(1)
            } else {
                sleep(50)
            }
        }
    }

    fun rescan() {
        mice = EnvironmentFactory.newInstance().controllers.filterIsInstance<Mouse>()

        if (RawInputMod.config.debugRescan && mice.isNotEmpty()) {
            Notifications.INSTANCE.send(
                "Raw Input",
                "Found ${mice.size} ${if (mice.size == 1) "mouse" else "mice"}."
            )
        }
    }

    fun reset() {
        dx.set(0)
        dy.set(0)
    }
}
