plugins {
    id("dev.deftu.gradle.multiversion-root")
}

preprocess {
    "1.12.2-fabric"(11202, "yarn") {
        "1.8.9-fabric"(10809, "yarn")
    }

    strictExtraMappings.set(true)
}