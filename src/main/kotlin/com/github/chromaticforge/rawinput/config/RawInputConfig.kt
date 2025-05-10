package com.github.chromaticforge.rawinput.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Dropdown
import cc.polyfrost.oneconfig.config.annotations.Info
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.InfoType
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import com.github.chromaticforge.rawinput.RawInputMod
import com.github.chromaticforge.rawinput.RawInputMod.supported
import com.github.chromaticforge.rawinput.util.rescan

object RawInputConfig : Config(Mod(RawInputMod.NAME, ModType.UTIL_QOL, "/assets/rawinput/icon.svg"), "${RawInputMod.ID}.json") {
    init {
        initialize()

        hideIf("unixWarning") { supported }
    }

    @Info(
        text = "Your system does not support Raw Input.",
        type = InfoType.WARNING, size = 2
    )
    private var unixWarning = false

    // FIXME: Why doesn't work :(
    @Button(
        name = "Rescan mice",
        description = "Rescans for new mice",
        text = "Rescan"
    )
    var rescans = {
        rescan()
    }

    @Switch(
        name = "Show rescans",
        description = "Displays when the mod rescans in chat.",
    )
    var debugRescan = false

    @Dropdown(
        name = "Input Environment",
        description = "",
        options = ["Direct & Raw", "Direct", "Default"]
    )
    var environment = 2
}