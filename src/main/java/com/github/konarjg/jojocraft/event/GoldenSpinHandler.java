package com.github.konarjg.jojocraft.event;

import com.github.konarjg.jojocraft.objectholder.JojoPotions;
import com.github.konarjg.jojocraft.power.Power;
import com.github.konarjg.jojocraft.power.PowerType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class GoldenSpinHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;

        Power power = player.getCapability(PowerHandler.CAPABILITY_POWER, null);

        if (power.getType() != PowerType.GOLDEN_SPIN) {
            return;
        }

        player.addPotionEffect(new PotionEffect(JojoPotions.GOLDEN_SPIN_EFFECT, Integer.MAX_VALUE));
    }

}
