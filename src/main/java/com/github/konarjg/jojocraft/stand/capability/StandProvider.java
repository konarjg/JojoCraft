package com.github.konarjg.jojocraft.stand.capability;

import com.github.konarjg.jojocraft.event.StandHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import java.util.UUID;

public class StandProvider implements ICapabilitySerializable<NBTTagCompound> {
    private final Stand instance = new Stand();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == StandHandler.CAPABILITY_STAND;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == StandHandler.CAPABILITY_STAND ? StandHandler.CAPABILITY_STAND.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("stand", instance.getName());
        tag.setString("entityId", instance.getEntityId() == null ? "" : instance.getEntityId().toString());

        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        instance.setName(nbt.getString("stand"));
        instance.setEntityId(nbt.getString("entityId").isEmpty() ? null : UUID.fromString(nbt.getString("entityId")));
    }
}
