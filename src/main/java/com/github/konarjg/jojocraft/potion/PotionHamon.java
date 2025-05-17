package com.github.konarjg.jojocraft.potion;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.power.PowerType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class PotionHamon extends Potion {
    public PotionHamon() {
        super(false, 0xffff00);
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "hamon"));
        setPotionName("effect.hamon");
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        World world = entity.world;
        double radius = 20.0;
        float healthRatio = entity.getHealth() / entity.getMaxHealth();

        if (entity.isInsideOfMaterial(Material.WATER) || healthRatio < 0.2f) {
            return;
        }

        for (EntityLivingBase mob : world.getEntitiesWithinAABB(EntityLivingBase.class, entity.getEntityBoundingBox().grow(radius))) {
            if (mob == entity) {
                continue;
            }

            mob.addPotionEffect(new net.minecraft.potion.PotionEffect(MobEffects.GLOWING, 5, 0, false, false));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration > 0;
    }
}
