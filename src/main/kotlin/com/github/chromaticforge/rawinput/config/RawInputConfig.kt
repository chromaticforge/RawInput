package com.github.chromaticforge.rawinput.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Info
import cc.polyfrost.oneconfig.config.data.InfoType
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import com.github.chromaticforge.rawinput.RawInputMod
import com.github.chromaticforge.rawinput.RescanThread
import org.apache.commons.lang3.SystemUtils


object RawInputConfig : Config(Mod(RawInputMod.NAME, ModType.UTIL_QOL, "/rawinput_dark.png"), "${RawInputMod.MODID}.json") {
    @Info(
        text = "Raw Input is useless on Linux/Mac.",
        type = InfoType.WARNING,
        size = 2
    )
    private var unixWarning = false

    @Button(name = "Rescan Mouses", text = "Rescan")
    var rescan: Runnable = Runnable {
        RescanThread.rescan()
        save()
        openGui()
    }

    init {
        initialize()
        hideIf("unixWarning") { SystemUtils.IS_OS_WINDOWS }
    }
}