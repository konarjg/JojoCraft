package com.github.konarjg.jojocraft.event;

import com.github.konarjg.jojocraft.item.ItemPillarmanStoneMask;
import com.github.konarjg.jojocraft.item.ItemStoneMask;
import com.github.konarjg.jojocraft.objectholder.JojoDamageSources;
import com.github.konarjg.jojocraft.objectholder.JojoPotions;
import com.github.konarjg.jojocraft.power.Power;
import com.github.konarjg.jojocraft.power.PowerType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PillarmanHandler {
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        EntityLivingBase entity = event.getEntityLiving();

        if (!(entity instanceof EntityPlayer || entity instanceof EntityVillager)) {
            return;
        }

        if (!(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemPillarmanStoneMask)) {
            return;
        }

        Power power = player.getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.VAMPIRE) {
            return;
        }

        power.setType(PowerType.PILLAR_MAN);
        player.sendMessage(new TextComponentString("You became a pillarman!"));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;

        Power power = player.getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.PILLAR_MAN && power.getType() != PowerType.ULTIMATE_LIFE_FORM) {
            return;
        }

        player.addPotionEffect(new PotionEffect(JojoPotions.PILLARMAN_EFFECT, Integer.MAX_VALUE));
    }

    @SubscribeEvent
    public static void onPlayerDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof EntityPlayer)) {
            return;
        }

        Power power = event.getEntity().getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.PILLAR_MAN && power.getType() != PowerType.ULTIMATE_LIFE_FORM) {
            return;
        }

        if (event.getSource() != JojoDamageSources.HAMON_DAMAGE_SOURCE) {
            return;
        }

        event.setAmount(0.5f * event.getAmount());
    }
}
