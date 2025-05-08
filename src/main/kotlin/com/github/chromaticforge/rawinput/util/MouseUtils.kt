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

    val env = Class.forName("net.java.games.input.DefaultControllerEnvironment")
        .getDeclaredConstructor().also { it.isAccessible = true }.newInstance() as ControllerEnvironment

    mouses = env.controllers.filterIsInstance<Mouse>()

    mouses.forEach {
        it.let {
            UChat.chat("[Raw Input] Found $it")
        }
    }
}