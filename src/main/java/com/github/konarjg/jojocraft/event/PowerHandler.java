package com.github.konarjg.jojocraft.event;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.power.Power;
import com.github.konarjg.jojocraft.power.PowerProvider;
import com.github.konarjg.jojocraft.stand.capability.Stand;
import com.github.konarjg.jojocraft.stand.capability.StandProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Mod.EventBusSubscriber
public class PowerHandler {
    @CapabilityInject(Power.class)
    public static final Capability<Power> CAPABILITY_POWER = null;

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(Tags.MOD_ID, "power"), new PowerProvider());
            event.addCapability(new ResourceLocation(Tags.MOD_ID, "stand"), new StandProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            return;
        }

        EntityPlayer oldPlayer = event.getOriginal();
        EntityPlayer newPlayer = event.getEntityPlayer();

        Power oldPower = oldPlayer.getCapability(PowerHandler.CAPABILITY_POWER, null);
        Power newPower = newPlayer.getCapability(PowerHandler.CAPABILITY_POWER, null);
        newPower.setType(oldPower.getType());
    }
}