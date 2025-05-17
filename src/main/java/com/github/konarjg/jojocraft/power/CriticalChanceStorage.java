package com.github.konarjg.jojocraft.power;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class CriticalChanceStorage implements Capability.IStorage<CriticalChance>{
    @Override
    public NBTBase writeNBT(Capability<CriticalChance> capability, CriticalChance instance, EnumFacing side) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setDouble("criticalChance", instance.getCriticalChance());
        return tag;
    }

    @Override
    public void readNBT(Capability<CriticalChance> capability, CriticalChance instance, EnumFacing side, NBTBase nbt) {
        if (nbt instanceof NBTTagCompound) {
            double criticalChance = ((NBTTagCompound) nbt).getDouble("criticalChance");
            instance.setCriticalChance(criticalChance);
        }
    }
}
