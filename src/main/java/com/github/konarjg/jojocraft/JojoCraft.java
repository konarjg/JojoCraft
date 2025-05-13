package com.github.konarjg.jojocraft;

import com.github.konarjg.jojocraft.creativetab.JojoCreativeTab;
import com.github.konarjg.jojocraft.event.PowerHandler;
import com.github.konarjg.jojocraft.event.VampirismHandler;
import com.github.konarjg.jojocraft.objectholder.JojoPotions;
import com.github.konarjg.jojocraft.power.Power;
import com.github.konarjg.jojocraft.power.PowerStorage;
import com.github.konarjg.jojocraft.registry.BlockRegistry;
import com.github.konarjg.jojocraft.registry.CommandRegistry;
import com.github.konarjg.jojocraft.registry.ItemRegistry;
import com.github.konarjg.jojocraft.registry.ModelRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
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
        CapabilityManager.INSTANCE.register(Power.class, new PowerStorage(), Power::new);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ForgeRegistries.POTIONS.registerAll(
            JojoPotions.VAMPIRE_EFFECT,
            JojoPotions.HAMON_EFFECT
        );
        MinecraftForge.EVENT_BUS.register(PowerHandler.class);
        MinecraftForge.EVENT_BUS.register(VampirismHandler.class);
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        CommandRegistry.registerCommands(event);
    }
}
