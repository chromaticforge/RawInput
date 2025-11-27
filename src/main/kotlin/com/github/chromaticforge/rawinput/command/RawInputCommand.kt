package com.github.chromaticforge.rawinput.command

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main
import cc.polyfrost.oneconfig.utils.commands.annotations.SubCommand
import com.github.chromaticforge.rawinput.RawInputMod
import com.github.chromaticforge.rawinput.config.RawInputConfig
import com.github.chromaticforge.rawinput.input.RawInputPoller

@Command(
    value = RawInputMod.ID,
    description = "Access the ${RawInputMod.NAME} GUI."
)
object RawInputCommand {
    @Main
    fun openGui() {
        RawInputConfig.openGui()
    }

    @SubCommand(description = "Rescans for new mice.")
    fun rescan() {
        RawInputPoller.requestRescan()
    }
}
