package com.github.konarjg.jojocraft.registry;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.objectholder.JojoItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Tags.MOD_ID)
public class ModelRegistry
{
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        registerModel(JojoItems.STONE_MASK, 0);
    }

    private static void registerModel(Item item, int meta)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }


}
