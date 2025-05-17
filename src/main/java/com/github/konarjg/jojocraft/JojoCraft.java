package com.github.konarjg.jojocraft;

import com.github.konarjg.jojocraft.creativetab.JojoCreativeTab;
import com.github.konarjg.jojocraft.entity.EntitySpinArrow;
import com.github.konarjg.jojocraft.event.*;
import com.github.konarjg.jojocraft.objectholder.JojoBlocks;
import com.github.konarjg.jojocraft.objectholder.JojoPotions;
import com.github.konarjg.jojocraft.power.CriticalChance;
import com.github.konarjg.jojocraft.power.CriticalChanceStorage;
import com.github.konarjg.jojocraft.power.Power;
import com.github.konarjg.jojocraft.power.PowerStorage;
import com.github.konarjg.jojocraft.registry.*;
import com.github.konarjg.jojocraft.stand.StandHandler;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scala.collection.parallel.ParIterableLike;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class JojoCraft {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);
    public static final CreativeTabs creativeTab = new JojoCreativeTab();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Hello From {}!", Tags.MOD_NAME);
        MinecraftForge.EVENT_BUS.register(BlockRegistry.class);
        MinecraftForge.EVENT_BUS.register(ItemRegistry.class);
        MinecraftForge.EVENT_BUS.register(ModelRegistry.class);
        MinecraftForge.EVENT_BUS.register(EntityRegistry.class);
        CapabilityManager.INSTANCE.register(Power.class, new PowerStorage(), Power::new);
        CapabilityManager.INSTANCE.register(CriticalChance.class, new CriticalChanceStorage(), CriticalChance::new);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ForgeRegistries.POTIONS.registerAll(
            JojoPotions.VAMPIRE_EFFECT,
            JojoPotions.PILLARMAN_EFFECT,
            JojoPotions.ULTIMATE_LIFE_FORM_EFFECT,
            JojoPotions.HAMON_EFFECT,
            JojoPotions.SPIN_EFFECT,
            JojoPotions.GOLDEN_SPIN_EFFECT
        );

        MinecraftForge.EVENT_BUS.register(PowerHandler.class);
        MinecraftForge.EVENT_BUS.register(VampirismHandler.class);
        MinecraftForge.EVENT_BUS.register(PillarmanHandler.class);
        MinecraftForge.EVENT_BUS.register(UltimateLifeFormHandler.class);
        MinecraftForge.EVENT_BUS.register(HamonHandler.class);
        MinecraftForge.EVENT_BUS.register(SpinHandler.class);
        MinecraftForge.EVENT_BUS.register(GoldenSpinHandler.class);
        MinecraftForge.EVENT_BUS.register(StandHandler.class);

        OreDictionary.registerOre("plankWood", new ItemStack(JojoBlocks.BLACK_PLANKS));
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        CommandRegistry.registerCommands(event);
    }
}
