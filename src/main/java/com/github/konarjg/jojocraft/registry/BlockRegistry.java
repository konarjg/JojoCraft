package com.github.konarjg.jojocraft.registry;

import com.github.konarjg.jojocraft.objectholder.JojoBlocks;
import com.github.konarjg.jojocraft.objectholder.JojoItems;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BlockRegistry {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                JojoBlocks.COFFIN,
                JojoBlocks.BLACK_PLANKS
        );
    }
}
