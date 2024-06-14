package com.rosiepies.sculksickness.mixin;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.register.ModEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(EntityRenderer.class)
public abstract class EntityRenderMixin<T extends Entity> {

    @Inject(method = "shouldRender", at = @At("TAIL"), cancellable = true)
    public void shouldRender(T entity, Frustum frustum, double d, double e, double f, CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (entity instanceof LivingEntity livingEntity &&  player != null) {
            boolean isEntityStill = livingEntity.getDeltaMovement().x <= 0 && livingEntity.isOnGround() && livingEntity.getDeltaMovement().z <= 0;
            boolean isEffectAmplifier = (SculkSickness.compareAmplifier(player.getEffect(ModEffects.SCULK_SICKNESS.get()),SculkSickness.CONFIG.common.darknessSymptom.applyDarknessAtStage - 1));
            if (isEffectAmplifier && ((isEntityStill && !entity.isOnFire() && !entity.isFreezing()) || entity.dampensVibrations())) {
                cir.setReturnValue(false);
            } else if (player.distanceTo(entity) <= 96) {
                SculkSickness.runVibration(entity,player.getEyePosition(),3);
                cir.setReturnValue(true);
            }
        }
    }
}
