package com.github.konarjg.jojocraft.power;

import com.github.konarjg.jojocraft.event.PowerHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PowerProvider implements ICapabilitySerializable<NBTTagCompound> {
    private final Power instance = new Power();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == PowerHandler.CAPABILITY_POWER;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == PowerHandler.CAPABILITY_POWER ? PowerHandler.CAPABILITY_POWER.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("power", instance.getType().toString());
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        instance.setType(PowerType.valueOf(nbt.getString("power")));
    }
}
