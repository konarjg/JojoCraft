package com.github.konarjg.jojocraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionVampire extends Potion {
    public PotionVampire() {
        super(false, 0x200000);
        setRegistryName(new ResourceLocation("jojocraft", "vampire"));
        setPotionName("effect.vampire");
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (entity.world.isRemote || entity.ticksExisted % 40 != 0) {
            return;
        }

        entity.heal(1F + amplifier);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration > 0;
    }
}
