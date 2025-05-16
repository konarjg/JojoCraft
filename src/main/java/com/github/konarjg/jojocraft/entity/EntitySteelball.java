package com.github.konarjg.jojocraft.entity;

import com.github.konarjg.jojocraft.event.PowerHandler;
import com.github.konarjg.jojocraft.objectholder.JojoDamageSources;
import com.github.konarjg.jojocraft.objectholder.JojoItems;
import com.github.konarjg.jojocraft.power.PowerType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class EntitySteelball extends EntityArrow {

    public EntitySteelball(World worldIn) {
        super(worldIn);
    }

    public EntitySteelball(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (shootingEntity == null || !shootingEntity.hasCapability(PowerHandler.CAPABILITY_POWER, null) ||
                shootingEntity.getCapability(PowerHandler.CAPABILITY_POWER, null).getType() != PowerType.SPIN) {
            return;
        }

        List<EntityLivingBase> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(5.0D));
        EntityLivingBase closestTarget = null;
        double closestDistance = Double.MAX_VALUE;

        for (EntityLivingBase targetCandidate : targets) {
            if (targetCandidate == shootingEntity || !targetCandidate.canEntityBeSeen(this) || !targetCandidate.attackable()
                || targetCandidate.isDead) {
                continue;
            }

            double distance = getDistance(targetCandidate);

            if (distance < closestDistance) {
                closestTarget = targetCandidate;
                closestDistance = distance;
            }
        }

        if (closestTarget != null) {
            double dx = closestTarget.posX - posX;
            double dy = closestTarget.posY + closestTarget.getEyeHeight() - posY;
            double dz = closestTarget.posZ - posZ;
            double speed = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);

            motionX = (dx / closestDistance) * speed;
            motionY = (dy / closestDistance) * speed;
            motionZ = (dz / closestDistance) * speed;
            ProjectileHelper.rotateTowardsMovement(this, 0.2f);
        }
    }


    @Override
    protected void arrowHit(EntityLivingBase living) {
        living.attackEntityFrom(JojoDamageSources.SPIN_DAMAGE_SOURCE, 10f);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(JojoItems.STEEL_BALL);
    }
}
