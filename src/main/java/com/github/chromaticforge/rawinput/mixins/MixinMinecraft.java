package com.github.chromaticforge.rawinput.mixins;

import com.github.chromaticforge.rawinput.util.RawMouseHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MouseHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Shadow
    public MouseHelper mouseHelper;

    @Inject(method = "startGame", at = @At("TAIL"))
    private void startGame(CallbackInfo info) {
        mouseHelper = new RawMouseHelper();
    }
}
