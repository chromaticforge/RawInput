package com.github.chromaticforge.rawinput.command

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main
import com.github.chromaticforge.rawinput.util.rescan

@Command(
    value = "rescan",
    description = "Rescans for new mouses."
)
object RescanCommand {
    @Main
    fun main() {
        rescan()
    }
}