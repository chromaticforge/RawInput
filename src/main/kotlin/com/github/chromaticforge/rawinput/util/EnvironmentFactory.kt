package com.github.chromaticforge.rawinput.util

import net.java.games.input.ControllerEnvironment
import java.lang.reflect.Constructor

object EnvironmentFactory {
    private lateinit var constructor: Constructor<*>

    fun initialize(environment: String) {
        constructor = Class.forName("net.java.games.input.$environment")
            .getDeclaredConstructor().apply { isAccessible = true }
    }

    fun newInstance(): ControllerEnvironment = constructor.newInstance() as ControllerEnvironment
}

