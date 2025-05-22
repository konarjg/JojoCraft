package com.github.konarjg.jojocraft.objectholder;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.item.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Tags.MOD_ID)
public class JojoItems {
    public static final Item STONE_MASK = new ItemStoneMask();
    public static final Item PILLARMAN_STONE_MASK = new ItemPillarmanStoneMask();
    public static final Item AJA = new ItemAja();
    public static final Item AJA_STONE_MASK = new ItemAjaStoneMask();
    public static final Item SPIN_ARROW = new ItemSpinArrow();
    public static final Item STEEL_BALL = new ItemSteelball();
    public static final Item STAND_ARROW = new ItemStandArrow();
    public static final Item STAND = new ItemStand();
}
