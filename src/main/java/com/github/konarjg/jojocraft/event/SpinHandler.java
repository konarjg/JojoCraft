package com.github.konarjg.jojocraft.event;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.objectholder.JojoDamageSources;
import com.github.konarjg.jojocraft.objectholder.JojoPotions;
import com.github.konarjg.jojocraft.power.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class SpinHandler {

    @CapabilityInject(CriticalChance.class)
    public static final Capability<CriticalChance> CAPABILITY_CRITICAL_CHANCE = null;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;

        Power power = player.getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.SPIN && power.getType() != PowerType.GOLDEN_SPIN) {
            return;
        }

        player.addPotionEffect(new PotionEffect(JojoPotions.SPIN_EFFECT, Integer.MAX_VALUE));
    }

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(Tags.MOD_ID, "criticalChance"), new CriticalChanceProvider());
        }
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingHurtEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        Power power = player.getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.SPIN && power.getType() != PowerType.GOLDEN_SPIN) {
            return;
        }

        double criticalChance = player.getCapability(SpinHandler.CAPABILITY_CRITICAL_CHANCE, null).getCriticalChance();
        double criticalRoll = Math.random();

        if (criticalRoll >= criticalChance) {
            return;
        }

        event.setAmount(event.getAmount() * 2);
        player.sendMessage(new TextComponentString("Your Spin attack inflicted a critical hit! The chance was: " + (criticalChance * 100) + "%"));
    }
}
