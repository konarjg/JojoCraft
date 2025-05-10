package com.github.konarjg.jojocraft.event;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.item.ItemStoneMask;
import com.github.konarjg.jojocraft.power.Power;
import com.github.konarjg.jojocraft.power.PowerType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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

        boolean canSeeSky = world.canBlockSeeSky(player.getPosition());
        boolean isDaytime = world.isDaytime();

        if (canSeeSky && isDaytime) {
            player.setFire(5);
        }
    }
}
