package com.github.konarjg.jojocraft.power;

import com.github.konarjg.jojocraft.event.SpinHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CriticalChanceProvider  implements ICapabilitySerializable<NBTTagCompound> {
    private final CriticalChance instance = new CriticalChance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == SpinHandler.CAPABILITY_CRITICAL_CHANCE;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == SpinHandler.CAPABILITY_CRITICAL_CHANCE ? SpinHandler.CAPABILITY_CRITICAL_CHANCE.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setDouble("criticalChance", instance.getCriticalChance());
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        instance.setCriticalChance(nbt.getDouble("criticalChance"));
    }
}
