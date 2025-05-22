package com.github.konarjg.jojocraft.entity.npc;

import com.github.konarjg.jojocraft.event.PowerHandler;
import com.github.konarjg.jojocraft.objectholder.JojoDamageSources;
import com.github.konarjg.jojocraft.objectholder.JojoItems;
import com.github.konarjg.jojocraft.power.PowerType;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityBaronZeppeli extends EntityCreature {
    public EntityBaronZeppeli(World worldIn) {
        super(worldIn);
        isImmuneToFire = true;
    }

    @Override
    protected void damageEntity(DamageSource damageSrc, float damageAmount) {
        if (damageSrc == JojoDamageSources.HAMON_DAMAGE_SOURCE) {
            return;
        }

        super.damageEntity(damageSrc, damageAmount);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (this.world.isRemote) {
            return true;
        }

        if (hand != EnumHand.MAIN_HAND) {
            return false;
        }

        if (player.getCapability(PowerHandler.CAPABILITY_POWER, null).getType() != PowerType.NONE) {
            return false;
        }

        if (player.getHeldItem(hand).getItem() != JojoItems.STONE_MASK) {
            return false;
        }

        player.getCapability(PowerHandler.CAPABILITY_POWER, null).setType(PowerType.HAMON);
        player.getHeldItem(hand).shrink(1);
        return true;
    }


    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.world.isRemote) {
            EntityPlayer closestPlayer = this.world.getClosestPlayerToEntity(this, 8.0D);
            if (closestPlayer != null) {
                double dx = closestPlayer.posX - this.posX;
                double dz = closestPlayer.posZ - this.posZ;
                float targetYaw = (float) (Math.atan2(dz, dx) * (180D / Math.PI));

                this.rotationYaw = targetYaw;
            }
        }
    }

}
