package com.github.chromaticforge.rawinput

import cc.polyfrost.oneconfig.utils.commands.CommandManager
import com.github.chromaticforge.rawinput.command.RawInputCommand
import com.github.chromaticforge.rawinput.command.RescanCommand
import com.github.chromaticforge.rawinput.config.RawInputConfig
import net.java.games.input.DirectAndRawInputEnvironmentPlugin
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

    val supported = DirectAndRawInputEnvironmentPlugin.getDefaultEnvironment().isSupported

    @Mod.EventHandler
    fun onFMLInitialization(event: FMLInitializationEvent) {
        RawInputConfig
        CommandManager.INSTANCE.registerCommand(RawInputCommand)
        CommandManager.INSTANCE.registerCommand(RescanCommand)
    }
}