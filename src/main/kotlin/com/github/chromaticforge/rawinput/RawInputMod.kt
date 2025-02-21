package com.github.chromaticforge.rawinput

import cc.polyfrost.oneconfig.utils.commands.CommandManager
import com.github.chromaticforge.rawinput.command.RawInputCommand
import com.github.chromaticforge.rawinput.command.RescanCommand
import com.github.chromaticforge.rawinput.config.RawInputConfig
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(
    modid = RawInputMod.MODID,
    name = RawInputMod.NAME,
    version = RawInputMod.VERSION,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter"
)
object RawInputMod {
    const val MODID: String = "@ID@"
    const val NAME: String = "@NAME@"
    const val VERSION: String = "@VER@"

    @Mod.EventHandler
    fun onFMLInitialization(event: FMLInitializationEvent?) {
        RawInputConfig
        CommandManager.INSTANCE.registerCommand(RawInputCommand)
        CommandManager.INSTANCE.registerCommand(RescanCommand)
    }
}