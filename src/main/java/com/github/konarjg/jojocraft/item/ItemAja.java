package com.github.konarjg.jojocraft.item;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.Tags;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemAja extends Item {
    public ItemAja() {
        super();
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "aja"));
        setTranslationKey("aja");
        setMaxStackSize(1);
        setCreativeTab(JojoCraft.creativeTab);
    }
}
