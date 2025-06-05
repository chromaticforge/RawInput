package com.github.chromaticforge.rawinput

import cc.polyfrost.oneconfig.utils.Notifications
import cc.polyfrost.oneconfig.utils.commands.CommandManager
import com.github.chromaticforge.rawinput.command.RawInputCommand
import com.github.chromaticforge.rawinput.command.RescanCommand
import com.github.chromaticforge.rawinput.config.RawInputConfig
import com.github.chromaticforge.rawinput.impl.RawInputMouseHelper
import com.github.chromaticforge.rawinput.util.LibraryChecker
import net.java.games.input.DirectAndRawInputEnvironmentPlugin
import net.minecraft.client.Minecraft
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

    var environment: String = ""

    @Mod.EventHandler
    fun onFMLInitialization(event: FMLInitializationEvent) {
        val direct = LibraryChecker.isLibraryLoaded("jinput-dx8")
        val raw = LibraryChecker.isLibraryLoaded("jinput-raw")

        if (raw || direct) {
            environment = if (direct && !raw) {
                "DirectInputEnvironmentPlugin"
            } else {
                "DirectAndRawInputEnvironmentPlugin"
            }

            config = RawInputConfig()

            CommandManager.INSTANCE.registerCommand(RawInputCommand)
            CommandManager.INSTANCE.registerCommand(RescanCommand)

            Minecraft.getMinecraft().mouseHelper = RawInputMouseHelper()
        } else {
            Notifications.INSTANCE.send("Raw Input", "Your system does not support Raw Input. Feel free to remove this mod!")
        }
    }
}