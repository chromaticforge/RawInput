package com.github.chromaticforge.rawinput.util

import cc.polyfrost.oneconfig.utils.Notifications
import com.github.chromaticforge.rawinput.config.RawInputConfig
import net.java.games.input.ControllerEnvironment
import net.java.games.input.Mouse

object MouseUtils {
    private val directSupported = LibraryChecker.isLibraryLoaded("jinput-dx8")
    private val rawSupported = LibraryChecker.isLibraryLoaded("jinput-raw")
    private val bothSupported = directSupported && rawSupported

    fun supported(): Boolean {
        return when (environment()) {
            "DirectAndRawInputEnvironmentPlugin" -> bothSupported
            "DirectInputEnvironmentPlugin" -> directSupported
            else -> false
        }
    }

    private fun environment(): String = when (RawInputConfig.environment) {
        1 -> "DirectInputEnvironmentPlugin"
        else -> "DirectAndRawInputEnvironmentPlugin"
    }

    var mice: List<Mouse> = emptyList()

    fun rescan() {
        if (RawInputConfig.enabled) {
            val env = Class.forName("net.java.games.input.${environment()}")
                .getDeclaredConstructor().also { it.isAccessible = true }.newInstance() as ControllerEnvironment

            mice = env.controllers.filterIsInstance<Mouse>()

            if (RawInputConfig.debugRescan && mice.isNotEmpty()) {
                Notifications.INSTANCE.send(
                    "Raw Input",
                    "Found ${mice.size} ${if (mice.size == 1) "mouse" else "mice"}."
                )
            }
        }
    }
}