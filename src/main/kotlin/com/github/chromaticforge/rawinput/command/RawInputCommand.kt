package com.github.chromaticforge.rawinput.command

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main
import cc.polyfrost.oneconfig.utils.commands.annotations.SubCommand
import com.github.chromaticforge.rawinput.RawInputMod
import com.github.chromaticforge.rawinput.impl.RawInputThread

@Command(
    value = RawInputMod.ID,
    description = "Access the " + RawInputMod.NAME + " GUI."
)
object RawInputCommand {
    @Main
    fun main() {
        RawInputMod.config.openGui()
    }

    @SubCommand(description = "Rescans for new mouses.")
    fun rescan() {
        RawInputThread.rescan()
    }
}