package com.github.konarjg.jojocraft.item;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.entity.EntitySteelball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemSteelball extends Item {
    public ItemSteelball() {
        super();
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "steelball"));
        setTranslationKey("steelball");
        setMaxStackSize(5);
        setCreativeTab(JojoCraft.creativeTab);
    }

    private float getVelocity() {
        float velocity = 1f;
        velocity = (velocity * velocity + velocity * 2.0F) / 3.0F;

        if (velocity > 1.0F) {
            velocity = 1.0F;
        }

        return velocity;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        if (!worldIn.isRemote) {
            EntitySteelball ball = new EntitySteelball(worldIn, playerIn);
            float velocity = getVelocity();
            ball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, velocity * 3.0F, 1.0F);
            worldIn.spawnEntity(ball);
        }

        playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
}
