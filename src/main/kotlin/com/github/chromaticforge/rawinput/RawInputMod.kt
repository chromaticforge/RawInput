package com.github.chromaticforge.rawinput

import cc.polyfrost.oneconfig.utils.Notifications
import cc.polyfrost.oneconfig.utils.commands.CommandManager
import cc.polyfrost.oneconfig.utils.dsl.mc
import com.github.chromaticforge.rawinput.command.RawInputCommand
import com.github.chromaticforge.rawinput.config.RawInputConfig
import com.github.chromaticforge.rawinput.input.RawInputMouseHelper
import com.github.chromaticforge.rawinput.util.EnvironmentManager
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

    @Mod.EventHandler
    fun onFMLInitialization(event: FMLInitializationEvent) {
        val setupResult = EnvironmentManager.setup()

        if (!setupResult.supported) {
            Notifications.INSTANCE.send(
                NAME,
                "Raw Input is not supported on your system. You can safely remove this mod."
            )
            return
        }

        CommandManager.INSTANCE.registerCommand(RawInputCommand)

        RawInputConfig.load()

        mc.mouseHelper = RawInputMouseHelper()
    }
}
