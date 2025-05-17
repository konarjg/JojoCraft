package com.github.konarjg.jojocraft.potion;

import com.github.konarjg.jojocraft.Tags;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class PotionPillarman extends Potion {
    public PotionPillarman() {
        super(false, 0x953553);
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "pillarman"));
        setPotionName("effect.pillarman");
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (entity.world.isRemote) {
            return;
        }

        List<EntityLivingBase> entities = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, entity.getEntityBoundingBox().grow(3));

        for (EntityLivingBase target : entities) {
            if (target != entity) {
                target.attackEntityFrom(DamageSource.WITHER, amplifier);
                entity.heal(0.3f * amplifier);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration > 0;
    }
}
