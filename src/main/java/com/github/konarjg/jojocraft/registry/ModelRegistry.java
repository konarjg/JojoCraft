package com.github.konarjg.jojocraft.registry;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.entity.EntitySpinArrow;
import com.github.konarjg.jojocraft.entity.EntitySteelball;
import com.github.konarjg.jojocraft.entity.render.RenderSteelball;
import com.github.konarjg.jojocraft.objectholder.JojoBlocks;
import com.github.konarjg.jojocraft.objectholder.JojoItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
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
        registerModel(JojoItems.PILLARMAN_STONE_MASK, 0);
        registerModel(JojoItems.AJA, 0);
        registerModel(JojoItems.AJA_STONE_MASK, 0);
        registerModel(JojoItems.SPIN_ARROW, 0);
        registerModel(JojoItems.STEEL_BALL, 0);
        registerModel(Item.getItemFromBlock(JojoBlocks.BLACK_PLANKS), 0);
        registerModel(Item.getItemFromBlock(JojoBlocks.COFFIN), 0);

        RenderingRegistry.registerEntityRenderingHandler(EntitySpinArrow.class, manager ->
                new RenderArrow<EntitySpinArrow>(manager) {
                    @Override
                    protected ResourceLocation getEntityTexture(EntitySpinArrow entity) {
                        return new ResourceLocation(Tags.MOD_ID, "textures/entity/spin_arrow.png");
                    }
                }
        );

        RenderingRegistry.registerEntityRenderingHandler(EntitySteelball.class,manager ->
                new RenderSteelball(manager)
        );
    }

    private static void registerModel(Item item, int meta)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
