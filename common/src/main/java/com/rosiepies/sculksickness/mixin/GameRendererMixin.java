package com.rosiepies.sculksickness.mixin;

import com.rosiepies.sculksickness.EffectRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "render", at = @At("RETURN"))
    private void renderProxy(float partialTick, long nanos, boolean error, CallbackInfo info) {
        EffectRenderer.renderShaderEffect(partialTick);
    }
}
