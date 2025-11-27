package com.github.chromaticforge.rawinput.impl

import cc.polyfrost.oneconfig.utils.Notifications
import com.github.chromaticforge.rawinput.RawInputMod
import com.github.chromaticforge.rawinput.util.EnvironmentFactory
import net.java.games.input.Mouse
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

object RawInputThread : Thread("Polling Thread") {
    init {
        isDaemon = true
    }

    // this capacity is random, it's kinda just to avoid (extra) lag if your game freezes
    val buffer: BlockingQueue<Pair<Int, Int>> = LinkedBlockingQueue(2048)

    var shouldRescan = true

    var mice: List<Mouse> = emptyList()

    override fun run() {
        while (true) {
            if (RawInputMod.config.enabled) {
                if (shouldRescan) {
                    shouldRescan = false

                    mice = EnvironmentFactory.newInstance().controllers.filterIsInstance<Mouse>()

                    if (RawInputMod.config.debugRescan && mice.isNotEmpty()) {
                        Notifications.INSTANCE.send(
                            "Raw Input",
                            "Found ${mice.size} ${if (mice.size == 1) "mouse" else "mice"}."
                        )
                    }
                }

                mice.forEach {
                    if (!it.poll()) rescan()
                    val dx = it.x.pollData.toInt()
                    val dy = -it.y.pollData.toInt()

                    buffer.offer(dx to dy)
                }

                sleep(1)
            } else {
                sleep(50)
            }
        }
    }

    fun rescan() {
        shouldRescan = true
    }

    fun reset() {
        buffer.clear()
    }
}
