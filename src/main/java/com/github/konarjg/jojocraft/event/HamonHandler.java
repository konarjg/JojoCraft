package com.github.konarjg.jojocraft.event;

import com.github.konarjg.jojocraft.objectholder.JojoDamageSources;
import com.github.konarjg.jojocraft.objectholder.JojoPotions;
import com.github.konarjg.jojocraft.power.Power;
import com.github.konarjg.jojocraft.power.PowerType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class HamonHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;

        Power power = player.getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.HAMON && power.getType() != PowerType.SPIN && power.getType() != PowerType.GOLDEN_SPIN) {
            return;
        }

        player.addPotionEffect(new PotionEffect(JojoPotions.HAMON_EFFECT, Integer.MAX_VALUE));
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingHurtEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        Power power = player.getCapability(PowerHandler.CAPABILITY_POWER, null);

        if ((power.getType() != PowerType.HAMON && power.getType() != PowerType.SPIN && power.getType() != PowerType.GOLDEN_SPIN) || player.isInsideOfMaterial(Material.WATER)) {
            return;
        }

        float healthRatio = player.getHealth() / player.getMaxHealth();

        if (healthRatio < 0.2f) {
            return;
        }

        event.getEntity().setFire(5);
        event.getEntity().attackEntityFrom(JojoDamageSources.HAMON_DAMAGE_SOURCE, 3f * healthRatio);
    }

    @SubscribeEvent
    public static void onPlayerDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof EntityPlayer)) {
            return;
        }

        Power power = event.getEntity().getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.HAMON && power.getType() != PowerType.SPIN && power.getType() != PowerType.GOLDEN_SPIN) {
            return;
        }

        if (event.getSource() != JojoDamageSources.HAMON_DAMAGE_SOURCE) {
            return;
        }

        event.setCanceled(true);
    }
}
