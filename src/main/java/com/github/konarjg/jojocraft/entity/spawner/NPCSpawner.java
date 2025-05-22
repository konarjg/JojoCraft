package com.github.konarjg.jojocraft.entity.spawner;

import com.github.konarjg.jojocraft.entity.npc.EntityBaronZeppeli;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class NPCSpawner {
    @SubscribeEvent
    public static void onWorldLoad(EntityJoinWorldEvent event) {
        World world = event.getWorld();

        if (!world.isRemote && event.getEntity() instanceof EntityPlayer) {
            for (Village village : world.villageCollection.getVillageList()) {
                BlockPos pos = village.getCenter();

                if (world.rand.nextFloat() < 0.5f && world.getEntitiesWithinAABB(EntityBaronZeppeli.class, new AxisAlignedBB(pos).grow(200)).isEmpty()) {
                    Entity entity = new EntityBaronZeppeli(world);
                    entity.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
                    world.spawnEntity(new EntityBaronZeppeli(world));
                }
            }
        }
    }


}
