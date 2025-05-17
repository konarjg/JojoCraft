package com.github.konarjg.jojocraft.block;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class BlockBlackPlanks extends Block {
    public BlockBlackPlanks() {
        super(Material.WOOD);
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "black_planks"));
        setTranslationKey("black_planks");
        setCreativeTab(JojoCraft.creativeTab);
        setHardness(2.0f);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }
}
