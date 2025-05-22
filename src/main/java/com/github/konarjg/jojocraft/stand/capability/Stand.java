package com.github.konarjg.jojocraft.stand.capability;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.objectholder.JojoStands;
import com.github.konarjg.jojocraft.stand.StandStats;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.fixes.EntityId;

import java.util.UUID;

public class Stand {
    private String name;
    private UUID entityId;

    public Stand() {
        name = "NONE";
        entityId = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StandStats getStats() {
        if (name.equals("NONE")) {
            return null;
        }

        return JojoStands.STAND_STATS.get(name);
    }

    public UUID getEntityId() {
        return entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }
}
