package com.github.konarjg.jojocraft.objectholder;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.block.BlockCoffin;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Tags.MOD_ID)
public class JojoBlocks {
    public static final Block COFFIN = new BlockCoffin();
}
