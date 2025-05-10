package com.github.konarjg.jojocraft.item;

import com.github.konarjg.jojocraft.JojoCraft;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemStoneMask extends ItemArmor {
    public ItemStoneMask() {
        super(ArmorMaterial.CHAIN, 0, EntityEquipmentSlot.HEAD);
        setCreativeTab(JojoCraft.creativeTab);
        setRegistryName("stone_mask");
        setTranslationKey("stone_mask");
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EntityEquipmentSlot.HEAD;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return "jojocraft:textures/models/armor/stone_mask.png";
    }
}
