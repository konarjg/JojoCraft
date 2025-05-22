package com.github.konarjg.jojocraft.item;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.entity.EntityStand;
import com.github.konarjg.jojocraft.event.StandHandler;
import com.github.konarjg.jojocraft.stand.capability.Stand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemStand extends Item {
    public ItemStand() {
        super();
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "stand"));
        setTranslationKey("stand");
        setCreativeTab(JojoCraft.creativeTab);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        if (handIn != EnumHand.MAIN_HAND) {
            return ActionResult.newResult(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }

        Stand stand = playerIn.getCapability(StandHandler.CAPABILITY_STAND, null);

        if (stand.getEntityId() != null) {
            Entity entity = playerIn.getServer().getWorld(playerIn.dimension).getEntityFromUuid(stand.getEntityId());
            entity.setDead();
            stand.setEntityId(null);
            return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }

        Entity entity = new EntityStand(playerIn, worldIn, stand);
        entity.setPositionAndUpdate(playerIn.posX, playerIn.posY, playerIn.posZ);
        worldIn.spawnEntity(entity);
        stand.setEntityId(entity.getUniqueID());

        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
