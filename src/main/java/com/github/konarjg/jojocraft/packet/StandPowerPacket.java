package com.github.konarjg.jojocraft.packet;

import com.github.konarjg.jojocraft.entity.stand.EntityStand;
import com.github.konarjg.jojocraft.event.StandHandler;
import com.github.konarjg.jojocraft.event.TimeHandler;
import com.github.konarjg.jojocraft.objectholder.JojoSounds;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class StandPowerPacket implements IMessage {
    public StandPowerPacket() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<StandPowerPacket, IMessage> {

        @Override
        public IMessage onMessage(StandPowerPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            WorldServer world = player.getServerWorld();
            UUID entityId = player.getCapability(StandHandler.CAPABILITY_STAND, null).getEntityId();

            if (TimeHandler.timeStopperUser != null && !TimeHandler.timeStopperUser.getUniqueID().equals(player)) {
                return null;
            }

            if (entityId == null) {
                return null;
            }

            EntityStand stand = (EntityStand)world.getEntityFromUuid(entityId);

            if (stand == null) {
                return null;
            }

            stand.usePower();
            return null;
        }
    }
}
