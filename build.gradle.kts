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

toolkitLoomHelper {
    // The mod doesn't work in a dev environment.
    // Raw / Direct natives aren't loaded.
    disableRunConfigs(GameSide.BOTH)
}

toolkitReleases {
    versionType = VersionType.BETA

    val changelog = rootProject.file("changelogs/${modData.version}.md")

    if (changelog.exists()) {
        changelogFile.set(changelog)
    }

    modrinth {
        projectId.set("rawinput")
        dependencies.add(
            ModDependency("fabric-language-kotlin", DependencyType.REQUIRED)
        )
    }
}