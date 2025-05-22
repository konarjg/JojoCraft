package com.github.konarjg.jojocraft.item;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.gui.GuiStandSelection;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemStandArrow extends Item{
    public ItemStandArrow() {
        super();
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "stand_arrow"));
        setTranslationKey("stand_arrow");
        setMaxStackSize(1);
        setCreativeTab(JojoCraft.creativeTab);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (handIn != EnumHand.MAIN_HAND) {
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }

        Minecraft.getMinecraft().displayGuiScreen(new GuiStandSelection(playerIn));
        playerIn.getHeldItem(handIn).shrink(1);
        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
