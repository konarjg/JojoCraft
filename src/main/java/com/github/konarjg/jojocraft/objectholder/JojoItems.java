package com.github.konarjg.jojocraft.objectholder;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.item.ItemStoneMask;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Tags.MOD_ID)
public class JojoItems {
    public static final Item STONE_MASK = new ItemStoneMask();
}
