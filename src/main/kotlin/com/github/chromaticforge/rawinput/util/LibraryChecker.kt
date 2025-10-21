package com.github.chromaticforge.rawinput.util

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

object LibraryChecker {
    fun isLibraryLoaded(name: String): Boolean = runCatching {
        val libraryPath = System.getProperty("java.library.path") ?: return false
        val mappedName = System.mapLibraryName(name)

        libraryPath.split(File.pathSeparator)
            .any { dir ->
                Files.exists(Paths.get(dir, mappedName))
            }
    }.getOrDefault(false)
}