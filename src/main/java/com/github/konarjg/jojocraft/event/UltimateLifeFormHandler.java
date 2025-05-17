package com.github.konarjg.jojocraft.event;

import com.github.konarjg.jojocraft.item.ItemAjaStoneMask;
import com.github.konarjg.jojocraft.item.ItemPillarmanStoneMask;
import com.github.konarjg.jojocraft.objectholder.JojoDamageSources;
import com.github.konarjg.jojocraft.objectholder.JojoPotions;
import com.github.konarjg.jojocraft.power.Power;
import com.github.konarjg.jojocraft.power.PowerType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class UltimateLifeFormHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;

        Power power = player.getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() == PowerType.PILLAR_MAN) {
            if (!player.world.isDaytime() || !player.world.canSeeSky(player.getPosition()) || !(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemAjaStoneMask)) {
                return;
            }

            player.getCapability(PowerHandler.CAPABILITY_POWER, null).setType(PowerType.ULTIMATE_LIFE_FORM);
            return;
        }

        if (power.getType() != PowerType.ULTIMATE_LIFE_FORM) {
            return;
        }

        player.addPotionEffect(new PotionEffect(JojoPotions.ULTIMATE_LIFE_FORM_EFFECT, Integer.MAX_VALUE));
    }

    @SubscribeEvent
    public static void onPlayerDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof EntityPlayer)) {
            return;
        }

        Power power = event.getEntity().getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.ULTIMATE_LIFE_FORM) {
            return;
        }

        if (event.getSource() == DamageSource.OUT_OF_WORLD || event.getSource() == JojoDamageSources.GOLDEN_SPIN_DAMAGE_SOURCE) {
            return;
        }

        event.setCanceled(true);
    }
}
