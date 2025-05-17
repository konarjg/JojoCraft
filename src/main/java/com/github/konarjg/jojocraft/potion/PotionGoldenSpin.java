package com.github.konarjg.jojocraft.potion;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.event.SpinHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class PotionGoldenSpin extends Potion {
    public PotionGoldenSpin() {
        super(false, 0xd4af37);
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "golden_spin"));
        setPotionName("effect.golden_spin");
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        List<EntityLivingBase> entities = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, entity.getEntityBoundingBox().grow(3));

        for (EntityLivingBase target : entities) {
            if (target != entity) {
                Vec3d knockbackDirection = new Vec3d(target.posX - entity.posX, 0, target.posZ - entity.posZ).normalize();
                target.motionX += knockbackDirection.x * 3D;
                target.motionZ += knockbackDirection.z * 3D;
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration > 0;
    }
}
