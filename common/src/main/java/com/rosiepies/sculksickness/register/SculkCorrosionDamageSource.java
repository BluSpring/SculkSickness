package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.SculkPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

public class SculkCorrosionDamageSource extends DamageSource {
    public SculkCorrosionDamageSource() {
        super("sculk_corrosion");
    }

    @Override
    public boolean isBypassArmor() {
        return true;
    }
    @Override
    public boolean isBypassMagic() {
        return true;
    }
    @Override
    public boolean isBypassEnchantments() {
        return true;
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity entity) {
        if (!(entity instanceof Player)) return super.getLocalizedDeathMessage(entity);
        String msg = "death.attack.sculk_corrosion";
        return Component.translatable(msg, entity.getDisplayName());
    }
}
