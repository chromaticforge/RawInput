package com.github.chromaticforge.rawinput

import cc.polyfrost.oneconfig.utils.Notifications
import cc.polyfrost.oneconfig.utils.commands.CommandManager
import cc.polyfrost.oneconfig.utils.dsl.mc
import com.github.chromaticforge.rawinput.command.RawInputCommand
import com.github.chromaticforge.rawinput.command.RescanCommand
import com.github.chromaticforge.rawinput.config.RawInputConfig
import com.github.chromaticforge.rawinput.impl.RawInputMouseHelper
import com.github.chromaticforge.rawinput.util.EnvironmentFactory
import com.github.chromaticforge.rawinput.util.LibraryChecker
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(
    modid = RawInputMod.ID,
    name = RawInputMod.NAME,
    version = RawInputMod.VERSION,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter"
)
object RawInputMod {
    const val ID = "@MOD_ID@"
    const val NAME = "@MOD_NAME@"
    const val VERSION = "@MOD_VERSION@"

    lateinit var config: RawInputConfig

    lateinit var environment: String

    @Mod.EventHandler
    fun onFMLInitialization(event: FMLInitializationEvent) {
        val isDirectInputAvailable = LibraryChecker.isLibraryLoaded("jinput-dx8")
        val isRawInputAvailable = LibraryChecker.isLibraryLoaded("jinput-raw")

        if (isDirectInputAvailable || isRawInputAvailable) {
            setupRawInputEnvironment(isDirectInputAvailable, isRawInputAvailable)
        } else {
            Notifications.INSTANCE.send(
                NAME,
                "Raw Input is not supported on your system. You can safely remove this mod."
            )
        }
    }

    private fun setupRawInputEnvironment(direct: Boolean, raw: Boolean) {
        environment = when {
            direct && !raw -> "DirectInputEnvironmentPlugin"
            else -> "DirectAndRawInputEnvironmentPlugin"
        }

        config = RawInputConfig()

        CommandManager.INSTANCE.registerCommand(RawInputCommand)
        CommandManager.INSTANCE.registerCommand(RescanCommand)

        EnvironmentFactory.initialize(environment)

        mc.mouseHelper = RawInputMouseHelper()
    }
}