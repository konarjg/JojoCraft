package com.github.konarjg.jojocraft.event;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.item.ItemStoneMask;
import com.github.konarjg.jojocraft.objectholder.JojoDamageSources;
import com.github.konarjg.jojocraft.objectholder.JojoPotions;
import com.github.konarjg.jojocraft.power.Power;
import com.github.konarjg.jojocraft.power.PowerType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class VampirismHandler {
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

        if (!(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemStoneMask)) {
            return;
        }

        Power power = player.getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.NONE) {
            return;
        }

        power.setType(PowerType.VAMPIRE);
        player.sendMessage(new TextComponentString("You casted away your humanity!"));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        World world = player.world;

        Power power = player.getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.VAMPIRE) {
            return;
        }

        player.addPotionEffect(new PotionEffect(JojoPotions.VAMPIRE_EFFECT, Integer.MAX_VALUE));

        boolean canSeeSky = world.canBlockSeeSky(player.getPosition());
        boolean isDaytime = world.isDaytime();

        if (!canSeeSky || !isDaytime) {
            return;
        }

        player.setFire(5);
        player.attackEntityFrom(JojoDamageSources.HAMON_DAMAGE_SOURCE, 3.0f);
    }

    @SubscribeEvent
    public static void onPlayerDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof EntityPlayer)) {
            return;
        }

        Power power = event.getEntity().getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.VAMPIRE) {
            return;
        }

        if (event.getSource() == JojoDamageSources.HAMON_DAMAGE_SOURCE || event.getSource() == DamageSource.OUT_OF_WORLD) {
            return;
        }

        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingHurtEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        Power power = player.getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.VAMPIRE) {
            return;
        }

        event.getEntity().attackEntityFrom(DamageSource.WITHER, 3f);

        if (!(event.getEntity() instanceof EntityVillager) && !(event.getEntity() instanceof EntityPlayer)) {
            return;
        }

        if (!player.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
            return;
        }

        player.heal(0.3f * event.getAmount());
    }
}
