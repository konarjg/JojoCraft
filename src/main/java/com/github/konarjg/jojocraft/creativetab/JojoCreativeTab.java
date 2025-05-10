package com.github.konarjg.jojocraft.creativetab;

import com.github.konarjg.jojocraft.objectholder.JojoItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class JojoCreativeTab extends CreativeTabs {
    public JojoCreativeTab() {
        super("jojocraft");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(JojoItems.STONE_MASK);
    }
}
