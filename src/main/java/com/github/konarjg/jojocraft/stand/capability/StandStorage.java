package com.github.konarjg.jojocraft.stand.capability;

import com.github.konarjg.jojocraft.power.PowerType;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.UUID;

public class StandStorage implements Capability.IStorage<Stand> {
    @Override
    public NBTBase writeNBT(Capability<Stand> capability, Stand instance, EnumFacing side) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("stand", instance.getName());
        tag.setString("entityId", instance.getEntityId() == null ? "" : instance.getEntityId().toString());

        return tag;
    }

    @Override
    public void readNBT(Capability<Stand> capability, Stand instance, EnumFacing side, NBTBase nbt) {
        if (nbt instanceof NBTTagCompound) {
            String stand = ((NBTTagCompound) nbt).getString("stand");
            String entityIdStr = ((NBTTagCompound) nbt).getString("entityId");
            instance.setName(stand);
            instance.setEntityId(entityIdStr.isEmpty() ? null : UUID.fromString(entityIdStr));
        }
    }
}
