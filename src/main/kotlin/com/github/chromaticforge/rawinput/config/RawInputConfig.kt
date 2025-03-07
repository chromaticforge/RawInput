package com.github.chromaticforge.rawinput.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Dropdown
import cc.polyfrost.oneconfig.config.annotations.Info
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.InfoType
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.utils.dsl.runAsync
import com.github.chromaticforge.rawinput.RawInputMod
import com.github.chromaticforge.rawinput.util.RescanThread
import org.apache.commons.lang3.SystemUtils


object RawInputConfig : Config(Mod(RawInputMod.NAME, ModType.UTIL_QOL, "/rawinput_dark.png"), "${RawInputMod.MODID}.json") {
    init {
        initialize()

        addListener("mode") { RescanThread.rescan() }
        addListener("polling") { polling = polling.toInt().toFloat() }

        hideIf("pollingRateWarnings") { polling <= 2000 }
        hideIf("unixWarning") { SystemUtils.IS_OS_WINDOWS }
    }

    // General

    @Info(
        text = "Raw input has no effect on Linux / MacOS.",
        type = InfoType.WARNING, size = 2
    )
    private var unixWarning = false

    // TODO: Look into bug where this doesn't actually rescan?
    @Button(
        name = "Rescan mouses",
        description = "Rescans for new input devices",
        text = "Rescan"
    )
    var rescan = Runnable { runAsync { RescanThread.rescan() } }

    // Advanced

    @Info(
        text = "Only make changes if explicitly instructed.",
        type = InfoType.WARNING, size = 2, category = "Advanced"
    )
    private var advancedWarning = false

    @Dropdown(
        name = "Input mode",
        options = ["Default", "Direct", "Raw", "Direct & Raw"],
        description = "Changes the input plugin",
        category = "Advanced", subcategory = "Input"
    )
    var mode = 0

    @Info(
        text = "High polling rates may cause issues.",
        type = InfoType.WARNING, size = 2, category = "Advanced", subcategory = "Polling"
    )
    private var pollingRateWarnings = false

    @Slider(
        name = "Polling rate",
        description = "How often the game polls for new mouse data.",
        min = 1f, max = 8000f, category = "Advanced", subcategory = "Polling"
    )
    var polling = 1000f

    @Switch(
        name = "Automatic rescan",
        description = "Automatically rescans under select conditions",
        category = "Advanced", subcategory = "Rescan"
    )
    var rescans = true

    @Switch(
        name = "Show rescans",
        description = "Displays when the mod rescans in chat.",
        category = "Advanced", subcategory = "Rescan"
    )
    var debugRescan = false
}