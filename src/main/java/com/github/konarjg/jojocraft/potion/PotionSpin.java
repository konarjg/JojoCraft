package com.github.konarjg.jojocraft.potion;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.event.SpinHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionSpin extends Potion {
    public PotionSpin() {
        super(false, 0xbfff00);
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "spin"));
        setPotionName("effect.spin");
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (!entity.hasCapability(SpinHandler.CAPABILITY_CRITICAL_CHANCE, null)) {
            return;
        }

        if (amplifier > 5) {
            amplifier = 5;
        }

        double chance = 0.1 * amplifier;
        entity.getCapability(SpinHandler.CAPABILITY_CRITICAL_CHANCE, null).setCriticalChance(chance);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration > 0;
    }
}
