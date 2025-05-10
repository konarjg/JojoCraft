package com.github.konarjg.jojocraft.power;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class PowerStorage implements Capability.IStorage<Power>{
    @Override
    public NBTBase writeNBT(Capability<Power> capability, Power instance, EnumFacing side) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("power", instance.getType().toString());
        return tag;
    }

    @Override
    public void readNBT(Capability<Power> capability, Power instance, EnumFacing side, NBTBase nbt) {
        if (nbt instanceof NBTTagCompound) {
            String power = ((NBTTagCompound) nbt).getString("power");
            instance.setType(PowerType.valueOf(power));
        }
    }
}
