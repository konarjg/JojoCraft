package com.github.konarjg.jojocraft.item;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.entity.EntitySpinArrow;
import com.github.konarjg.jojocraft.event.PowerHandler;
import com.github.konarjg.jojocraft.power.PowerType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemSpinArrow extends ItemArrow {
    public ItemSpinArrow() {
        super();
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "spin_arrow"));
        setTranslationKey("spin_arrow");
        setCreativeTab(JojoCraft.creativeTab);
        setMaxStackSize(16);
    }

    @Override
    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
        return new EntitySpinArrow(worldIn, shooter);
    }
}
