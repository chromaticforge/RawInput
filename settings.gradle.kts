@file:Suppress("PropertyName")

import groovy.lang.MissingPropertyException

pluginManagement {
    repositories {
        maven("https://maven.deftu.dev/releases")
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net")
        maven("https://repo.essential.gg/repository/maven-public")
        maven("https://server.bbkr.space/artifactory/libs-release/")
        maven("https://jitpack.io/")

        maven("https://maven.deftu.dev/snapshots")
        mavenLocal()

        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("jvm") version("1.9.10")
        id("dev.deftu.gradle.multiversion-root") version("2.33.3")
    }
}

val projectName: String = extra["mod.name"]?.toString()
    ?: throw MissingPropertyException("mod.name has not been set.")

rootProject.name = projectName
rootProject.buildFileName = "root.gradle.kts"

listOf(
    "1.8.9-forge",
    "1.12.2-forge"
).forEach { version ->
    include(":$version")
    project(":$version").apply {
        projectDir = file("versions/$version")
        buildFileName = "../../build.gradle.kts"
    }
}