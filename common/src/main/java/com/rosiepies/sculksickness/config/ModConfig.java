package com.rosiepies.sculksickness.config;

import com.rosiepies.sculksickness.SculkSickness;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@SuppressWarnings("unused")
@Config(name = SculkSickness.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/sculk.png")
public class ModConfig extends PartitioningSerializer.GlobalData {

    @ConfigEntry.Category("common")
    @ConfigEntry.Gui.TransitiveObject()
    public Common common = new Common();

    @Config(name = "common")
    public static final class Common implements ConfigData {
        @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
        public General general = new General();

        public static final class General implements ConfigData {
            @Comment("Chance to apply Sculk Sickness when stepping on / breaking sculk blocks or being hit by and infected entity / sculk mob. Should be from 0 to below 1.")
            public double randomInfectChance = .1;


            @Comment("Minimum Stage Interval: The minimum amount of seconds until the infection progresses to next stage. (Can be a decimal, ex. 100.50)")
            public float minStageInterval = 120;

            @Comment("Maximum Stage Interval: The maximum amount of seconds until the infection progresses to next stage. (Can be a decimal, ex. 100.50)")
            public float maxStageInterval = 600;
        }

        @ConfigEntry.Gui.CollapsibleObject()
        public DarknessSymptom darknessSymptom = new DarknessSymptom();

        public static final class DarknessSymptom implements ConfigData {
            @ConfigEntry.BoundedDiscrete(min = 1, max = 5)
            @Comment("Stage for 'Darkness' (Grants darkness & amplifies custom shader effect), should be a number 1-5")
            public int applyDarknessAtStage = 3;
        }

        @ConfigEntry.Gui.CollapsibleObject()
        public WeaknessSymptom weaknessSymptom = new WeaknessSymptom();

        public static final class WeaknessSymptom implements ConfigData {
            @ConfigEntry.BoundedDiscrete(min = 1, max = 5)
            @Comment("Stage for Weakness Effect, should be a whole number 1-5")
            public int applyWeaknessAtStage = 3;
        }

        @ConfigEntry.Gui.CollapsibleObject()
        public DamageSymptom damageSymptom = new DamageSymptom();

        public static final class DamageSymptom implements ConfigData {
            @ConfigEntry.BoundedDiscrete(min = 1, max = 5)
            @Comment("Stage for Damage, should be a whole number 1-5")
            public int applyDamageAtStage = 5;

            @Comment("Amount of damage taken per half second. Should be a whole number above 0. 1 = 0.5 hearts of damage.")
            public int applyDamageAmountPerHalfSecond = 3;

            @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
            @Comment("Chance to apply damage per half second. Should be from 0 to below 1.")
            public double randomDamageChance = .1;
        }
    }
}
