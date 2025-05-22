package com.github.konarjg.jojocraft.event;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.entity.EntityStand;
import com.github.konarjg.jojocraft.gui.GuiStandStats;
import com.github.konarjg.jojocraft.objectholder.JojoKeybinds;
import com.github.konarjg.jojocraft.packet.SetStandModePacket;
import com.github.konarjg.jojocraft.packet.StandPunchPacket;
import com.github.konarjg.jojocraft.packet.SyncStandPacket;
import com.github.konarjg.jojocraft.packet.ToggleStandPacket;
import com.github.konarjg.jojocraft.stand.capability.Stand;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.UUID;

@Mod.EventBusSubscriber
public class StandHandler {
    @CapabilityInject(Stand.class)
    public static final Capability<Stand> CAPABILITY_STAND = null;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START && JojoKeybinds.PUNCH.isKeyDown()) {
            JojoCraft.NETWORK_INSTANCE.sendToServer(new StandPunchPacket());
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (JojoKeybinds.OPEN_STAND_STATS_GUI.isPressed()) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            Stand stand = player.getCapability(StandHandler.CAPABILITY_STAND, null);
            JojoCraft.NETWORK_INSTANCE.sendToServer(new SyncStandPacket());
            Minecraft.getMinecraft().displayGuiScreen(new GuiStandStats(player, stand));
        } else if (JojoKeybinds.SUMMON_STAND.isPressed()) {
            JojoCraft.NETWORK_INSTANCE.sendToServer(new ToggleStandPacket());
        }
        else if (JojoKeybinds.MANUAL_MODE.isPressed()) {
            JojoCraft.NETWORK_INSTANCE.sendToServer(new SetStandModePacket(true));
        }
        else if (JojoKeybinds.FOLLOW_MODE.isPressed()) {
            JojoCraft.NETWORK_INSTANCE.sendToServer(new SetStandModePacket(false));
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (event.getEntity().world.isRemote) {
                return;
            }

            EntityPlayer player = (EntityPlayer) event.getEntity();
            Stand stand = player.getCapability(StandHandler.CAPABILITY_STAND, null);
            UUID entityId = stand.getEntityId();

            if (entityId == null) {
                return;
            }

            EntityStand entity = (EntityStand) player.getServer().getWorld(player.dimension).getEntityFromUuid(entityId);

            if (entity == null) {
                return;
            }

            stand.setEntityId(null);
            entity.setDead();
        }
    }


    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            return;
        }

        EntityPlayer oldPlayer = event.getOriginal();
        EntityPlayer newPlayer = event.getEntityPlayer();

        Stand oldStand = oldPlayer.getCapability(StandHandler.CAPABILITY_STAND, null);
        Stand newStand = newPlayer.getCapability(StandHandler.CAPABILITY_STAND, null);
        newStand.setName(oldStand.getName());
        newStand.setEntityId(oldStand.getEntityId());
    }
}
