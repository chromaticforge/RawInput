@file:Suppress("UnstableApiUsage", "PropertyName")

import com.modrinth.minotaur.dependencies.DependencyType
import com.modrinth.minotaur.dependencies.ModDependency
import dev.deftu.gradle.utils.GameSide
import dev.deftu.gradle.utils.VersionType

plugins {
    java
    kotlin("jvm")
    id("dev.deftu.gradle.multiversion")
    id("dev.deftu.gradle.tools")
    id("dev.deftu.gradle.tools.resources")
    id("dev.deftu.gradle.tools.bloom")
    id("dev.deftu.gradle.tools.shadow")
    id("dev.deftu.gradle.tools.minecraft.loom")
    id("dev.deftu.gradle.tools.minecraft.releases")
}

repositories {
    maven("https://repo.polyfrost.org/releases")
    maven("https://repo.polyfrost.org/snapshots")
}

dependencies {
    compileOnly("cc.polyfrost:oneconfig-1.8.9-forge:0.2.2-alpha+")

    shade("cc.polyfrost:oneconfig-wrapper-launchwrapper:1.0.0-beta17")
    implementation("cc.polyfrost:oneconfig-wrapper-launchwrapper:1.0.0-beta17")
}

toolkitLoomHelper {
    useTweaker("cc.polyfrost.oneconfig.loader.stage0.LaunchWrapperTweaker")

    // The mod doesn't work in a dev env.
    // Raw / Direct natives aren't loaded.
    disableRunConfigs(GameSide.BOTH)
}

toolkitReleases {
    versionType = VersionType.RELEASE

    val changelog = rootProject.file("changelogs/${modData.version}.md")

    if (changelog.exists()) {
        changelogFile.set(changelog)
    }

    modrinth {
        projectId.set("rawinput")
        dependencies.add(ModDependency("oneconfig", DependencyType.EMBEDDED))
    }
}