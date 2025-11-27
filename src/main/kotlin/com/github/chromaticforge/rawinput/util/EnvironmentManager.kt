package com.github.chromaticforge.rawinput.util

import cc.polyfrost.oneconfig.utils.Notifications
import com.github.chromaticforge.rawinput.RawInputMod
import net.java.games.input.ControllerEnvironment
import java.lang.reflect.Constructor

object EnvironmentManager {
    private const val DIRECT_PLUGIN = "DirectInputEnvironmentPlugin"
    private const val DIRECT_AND_RAW_PLUGIN = "DirectAndRawInputEnvironmentPlugin"

    private lateinit var constructor: Constructor<*>
    var environmentName: String = ""

    fun setup(): SetupResult {
        val hasDx8 = LibraryChecker.isLibraryLoaded("jinput-dx8")
        val hasRaw = LibraryChecker.isLibraryLoaded("jinput-raw")

        if (!hasDx8 && !hasRaw) {
            return SetupResult(supported = false)
        }

        environmentName = if (hasDx8 && !hasRaw) DIRECT_PLUGIN else DIRECT_AND_RAW_PLUGIN

        return try {
            initialize(environmentName)
            val supported = newInstance().isSupported
            if (!supported) {
                Notifications.INSTANCE.send(
                    RawInputMod.NAME,
                    "Detected input environment is not supported by your system."
                )
            }
            SetupResult(supported = supported, environmentName = environmentName)
        } catch (t: Throwable) {
            Notifications.INSTANCE.send(
                RawInputMod.NAME,
                "Failed to initialize input environment: ${t.message ?: "unknown error"}"
            )
            SetupResult(supported = false)
        }
    }

    private fun initialize(environment: String) {
        constructor = Class.forName("net.java.games.input.$environment")
            .getDeclaredConstructor()
            .apply { isAccessible = true }
    }

    fun newInstance(): ControllerEnvironment =
        constructor.newInstance() as ControllerEnvironment
}

data class SetupResult(
    val supported: Boolean,
    val environmentName: String? = null
)