package com.github.chromaticforge.rawinput.util

import java.io.File

object LibraryChecker {
    fun isLibraryLoaded(name: String): Boolean {
        return try {
            System.getProperty("java.library.path")?.let { path ->
                val mapped = System.mapLibraryName(name)
                path.split(File.pathSeparator ?: ";")
                    .any { libPath -> File(libPath, mapped).exists() }
            } ?: false
        } catch (_: Exception) {
            false
        }
    }
}