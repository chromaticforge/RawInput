package com.github.chromaticforge.rawinput.util

import cc.polyfrost.oneconfig.libs.universal.UChat
import com.github.chromaticforge.rawinput.config.RawInputConfig
import net.java.games.input.ControllerEnvironment
import net.java.games.input.Mouse

var mouses: List<Mouse> = emptyList()

fun rescan() {
    if (RawInputConfig.debugRescan) {
        UChat.chat("[Raw Input] Rescanning!")
    }

    val environment = when(RawInputConfig.environment) {
        0 -> "DirectAndRawInputEnvironmentPlugin"
        1 -> "DirectInputEnvironmentPlugin"
        else -> "DefaultControllerEnvironment"
    }

    val env = Class.forName("net.java.games.input.$environment")
        .getDeclaredConstructor().also { it.isAccessible = true }.newInstance() as ControllerEnvironment

    mouses = env.controllers.filterIsInstance<Mouse>()

    mouses.forEach {
        it.let {
            UChat.chat("[Raw Input] Found $it")
        }
    }
}