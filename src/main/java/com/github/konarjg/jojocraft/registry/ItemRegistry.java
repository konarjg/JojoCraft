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
                JojoItems.SPIN_ARROW,
                JojoItems.STEEL_BALL,
                new ItemBlock(JojoBlocks.COFFIN).setRegistryName(JojoBlocks.COFFIN.getRegistryName())
        );
    }

}
