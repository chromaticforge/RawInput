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
import com.github.chromaticforge.rawinput.util.directSupported
import com.github.chromaticforge.rawinput.util.rawSupported
import com.github.chromaticforge.rawinput.util.rescan
import org.apache.commons.lang3.SystemUtils

object RawInputConfig : Config(Mod(RawInputMod.NAME, ModType.UTIL_QOL, "/assets/rawinput/icon.svg"), "${RawInputMod.ID}.json") {
    @Info(
        text = "Your operating system does not support Raw Input. Feel free to uninstall this mod.",
        type = InfoType.WARNING, size = 2
    )
    private var unixWarning = false

    @Button(
        name = "Rescan mice",
        description = "Rescans for new mice.",
        text = "Rescan"
    )
    var rescanButton = Runnable { rescan() }

    @Switch(
        name = "Show rescans",
        description = "Displays when the mod rescans in chat.",
    )
    var debugRescan = false

    @Info(
        text = "Raw input is not supported, try using the Direct Input Environment or disabling this mod.",
        type = InfoType.ERROR, category = "Advanced", size = 2
    )
    private var rawError = false

    @Info(
        text = "Direct input is not supported, try using the Default Input Environment or disabling this mod.",
        type = InfoType.ERROR, category = "Advanced", size = 2
    )
    private var directError = false

    @Dropdown(
        name = "Input Environment", options = ["Direct & Raw", "Direct", "Default"],
        description = "The Input Environment plugin used.",
        category = "Advanced"
    )
    var environment = 2

    init {
        initialize()

        hideIf("unixWarning") { SystemUtils.IS_OS_WINDOWS }

        hideIf("rescanButton") { !SystemUtils.IS_OS_WINDOWS }
        hideIf("debugRescan") { !SystemUtils.IS_OS_WINDOWS }
        hideIf("environment") { !SystemUtils.IS_OS_WINDOWS }


        hideIf("directError") { environment != 1 || directSupported }
        hideIf("rawError") { environment != 0 || rawSupported }
    }
}