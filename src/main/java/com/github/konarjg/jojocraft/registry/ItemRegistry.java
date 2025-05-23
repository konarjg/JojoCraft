package com.github.konarjg.jojocraft.registry;

import com.github.konarjg.jojocraft.objectholder.JojoBlocks;
import com.github.konarjg.jojocraft.objectholder.JojoItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemRegistry {
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                JojoItems.STONE_MASK,
                JojoItems.PILLARMAN_STONE_MASK,
                JojoItems.AJA_STONE_MASK,
                JojoItems.AJA,
                JojoItems.SPIN_ARROW,
                JojoItems.STEEL_BALL,
                JojoItems.STAND_ARROW,
                new ItemBlock(JojoBlocks.COFFIN).setRegistryName(JojoBlocks.COFFIN.getRegistryName()),
                new ItemBlock(JojoBlocks.BLACK_PLANKS).setRegistryName(JojoBlocks.BLACK_PLANKS.getRegistryName())
        );
    }

}
