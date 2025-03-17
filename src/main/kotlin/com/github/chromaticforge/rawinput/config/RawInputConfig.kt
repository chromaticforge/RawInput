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
import com.github.chromaticforge.rawinput.util.rescan
import org.apache.commons.lang3.SystemUtils

object RawInputConfig : Config(Mod(RawInputMod.NAME, ModType.UTIL_QOL, "/rawinput_dark.svg"), "${RawInputMod.MODID}.json") {
    init {
        initialize()

        addListener("mode") { rescan() }

        hideIf("unixWarning") { SystemUtils.IS_OS_WINDOWS }
    }

    @Info(
        text = "Your device does not support Raw Input.",
        type = InfoType.WARNING, size = 2
    )
    private var unixWarning = false

    // FIXME: Why doesn't work
    @Button(
        name = "Rescan mouses",
        description = "Rescans for new input devices",
        text = "Rescan"
    )
    var rescan = Runnable {
        rescan()
    }

    @Switch(
        name = "Show rescans",
        description = "Displays when the mod rescans in chat.",
    )
    var debugRescan = false
}