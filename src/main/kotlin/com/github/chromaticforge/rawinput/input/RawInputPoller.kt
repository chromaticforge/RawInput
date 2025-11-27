package com.github.chromaticforge.rawinput.input

import cc.polyfrost.oneconfig.utils.Notifications
import com.github.chromaticforge.rawinput.config.RawInputConfig
import com.github.chromaticforge.rawinput.util.EnvironmentManager
import net.java.games.input.Mouse
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

object RawInputPoller {
    private const val BUFFER_CAPACITY = 2048
    private const val ACTIVE_POLL_DELAY_MS = 1L
    private const val IDLE_POLL_DELAY_MS = 50L
    private const val RESCAN_COOLDOWN_MS = 2000L

    private val executor = Executors.newSingleThreadScheduledExecutor { r ->
        Thread(r, "RawInput Poller").apply { isDaemon = true }
    }

    val buffer: LinkedBlockingQueue<Pair<Int, Int>> = LinkedBlockingQueue(BUFFER_CAPACITY)

    @Volatile
    private var mice: List<Mouse> = emptyList()

    @Volatile
    private var shouldRescan = true

    @Volatile
    private var lastRescanAt: Long = 0L

    fun start() {
        executor.scheduleAtFixedRate(::tick, 0, ACTIVE_POLL_DELAY_MS, TimeUnit.MILLISECONDS)
    }

    fun reset() {
        buffer.clear()
    }

    fun requestRescan() {
        val now = System.currentTimeMillis()
        if (now - lastRescanAt >= RESCAN_COOLDOWN_MS) {
            shouldRescan = true
            lastRescanAt = now
        }
    }

    private fun tick() {
        if (!RawInputConfig.enabled || mice.isEmpty()) {
            sleep()
            return
        }

        if (shouldRescan) {
            shouldRescan = false
            performRescan()
        }

        for (m in mice) {
            if (!m.poll()) {
                requestRescan()
                continue
            }

            val dx = m.x.pollData.toInt()
            val dy = -m.y.pollData.toInt()

            buffer.offer(dx to dy)
        }
    }

    private fun performRescan() {
        val env = EnvironmentManager.newInstance()
        mice = env.controllers.filterIsInstance<Mouse>()

        if (RawInputConfig.debugRescan) {
            val count = mice.size
            if (count > 0) {
                Notifications.INSTANCE.send(
                    "Raw Input",
                    "Found $count ${if (count == 1) "mouse" else "mice"}."
                )
            } else {
                Notifications.INSTANCE.send("Raw Input", "No mice detected.")
            }
        }
    }

    private fun sleep() {
        try {
            Thread.sleep(IDLE_POLL_DELAY_MS)
        } catch (_: InterruptedException) {
        }
    }
}
