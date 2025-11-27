package com.github.chromaticforge.rawinput.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import com.github.chromaticforge.rawinput.RawInputMod
import com.github.chromaticforge.rawinput.input.RawInputPoller
import com.github.chromaticforge.rawinput.util.EnvironmentManager

object RawInputConfig : Config(
    Mod(RawInputMod.NAME, ModType.UTIL_QOL, "/assets/rawinput/icon.png"),
    "${RawInputMod.ID}.json"
) {
    @Button(
        name = "Rescan mice",
        description = "Rescans for new mice.",
        text = "Rescan"
    )
    var rescanButton = Runnable { RawInputPoller.requestRescan() }

    @Switch(
        name = "Show rescans",
        description = "Displays when the mod rescans in chat."
    )
    var debugRescan = false

    init {
        if (EnvironmentManager.environmentName.isNotBlank()) {
            println("[Raw Input] ${EnvironmentManager.environmentName}")
            initialize()
        }
    }
}
