package com.github.chromaticforge.rawinput.command

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main
import com.github.chromaticforge.rawinput.RawInputMod
import com.github.chromaticforge.rawinput.config.RawInputConfig

@Command(
    value = RawInputMod.ID,
    description = "Access the " + RawInputMod.NAME + " GUI."
)
object RawInputCommand {
    @Main
    private fun main() {
        RawInputConfig.openGui()
    }
}