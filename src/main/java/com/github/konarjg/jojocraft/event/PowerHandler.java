package com.github.konarjg.jojocraft.event;

import com.github.konarjg.jojocraft.power.Power;
import com.github.konarjg.jojocraft.power.PowerProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PowerHandler {
    @CapabilityInject(Power.class)
    public static final Capability<Power> CAPABILITY_POWER = null;

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation("jojocraft", "power"), new PowerProvider());
        }
    }
}
