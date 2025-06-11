package com.github.chromaticforge.rawinput

import com.github.chromaticforge.rawinput.impl.RawInputMouseHelper
import com.github.chromaticforge.rawinput.util.LibraryChecker
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.MinecraftClient

object RawInputMod : ClientModInitializer {
    var environment: String = ""

    override fun onInitializeClient() {
        val direct = LibraryChecker.isLibraryLoaded("jinput-dx8")
        val raw = LibraryChecker.isLibraryLoaded("jinput-raw")

        if (raw || direct) {
            environment = if (direct && !raw) {
                "DirectInputEnvironmentPlugin"
            } else {
                "DirectAndRawInputEnvironmentPlugin"
            }

            MinecraftClient.getInstance().mouse = RawInputMouseHelper()
        }
    }
}