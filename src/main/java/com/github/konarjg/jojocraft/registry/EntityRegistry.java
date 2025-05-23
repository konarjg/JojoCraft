package com.github.konarjg.jojocraft.registry;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.entity.EntitySpinArrow;
import com.github.konarjg.jojocraft.entity.stand.EntityStand;
import com.github.konarjg.jojocraft.entity.EntitySteelball;
import com.github.konarjg.jojocraft.entity.npc.EntityBaronZeppeli;
import com.github.konarjg.jojocraft.entity.stand.EntityStandStarPlatinum;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod.EventBusSubscriber
public class EntityRegistry {
    public static final String SPIN_ARROW_ID = "spin_arrow";
    public static final String STEEL_BALL_ID = "steelball";
    public static final String BARON_ZEPPELI_ID = "baron_zeppeli";
    public static final String STAR_PLATINUM_ID = "star_platinum";


    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().registerAll(
                EntityEntryBuilder.create()
                        .entity(EntitySpinArrow.class)
                        .id(new ResourceLocation(Tags.MOD_ID, SPIN_ARROW_ID), 1)
                        .name(SPIN_ARROW_ID)
                        .tracker(64, 3, true)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(EntitySteelball.class)
                        .id(new ResourceLocation(Tags.MOD_ID, STEEL_BALL_ID), 2)
                        .name(STEEL_BALL_ID)
                        .tracker(64, 3, true)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(EntityBaronZeppeli.class)
                        .id(new ResourceLocation(Tags.MOD_ID, BARON_ZEPPELI_ID), 3)
                        .name(BARON_ZEPPELI_ID)
                        .tracker(64, 3, true)
                        .build(),
                EntityEntryBuilder.create()
                        .entity(EntityStandStarPlatinum.class)
                        .id(new ResourceLocation(Tags.MOD_ID, STAR_PLATINUM_ID), 4)
                        .name(STAR_PLATINUM_ID)
                        .tracker(64, 3, true)
                        .build()
        );
    }


}
