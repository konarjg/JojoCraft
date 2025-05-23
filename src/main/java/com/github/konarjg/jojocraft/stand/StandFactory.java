package com.github.konarjg.jojocraft.stand;

import com.github.konarjg.jojocraft.entity.stand.EntityStand;
import com.github.konarjg.jojocraft.entity.stand.EntityStandStarPlatinum;
import com.github.konarjg.jojocraft.stand.capability.Stand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;

public class StandFactory {
    public static void createStand(WorldServer world, EntityPlayer player, Stand stand, Vec3d position) {
        switch (stand.getName()) {
            case "Star Platinum":
                EntityStandStarPlatinum entityStarPlatinum = new EntityStandStarPlatinum(player, world, stand);
                entityStarPlatinum.setPositionAndUpdate(position.x, position.y, position.z);
                world.spawnEntity(entityStarPlatinum);
                stand.setEntityId(entityStarPlatinum.getUniqueID());
        }
    }
}
