package com.github.konarjg.jojocraft.packet;

import com.github.konarjg.jojocraft.entity.EntityStand;
import com.github.konarjg.jojocraft.event.StandHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class StandPunchPacket implements IMessage {
    public StandPunchPacket() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<StandPunchPacket, IMessage> {

        @Override
        public IMessage onMessage(StandPunchPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            WorldServer world = player.getServerWorld();
            UUID entityId = player.getCapability(StandHandler.CAPABILITY_STAND, null).getEntityId();

            if (entityId == null) {
                return null;
            }

            EntityStand stand = (EntityStand)world.getEntityFromUuid(entityId);

            if (stand == null) {
                return null;
            }

            stand.schedulePunch();
            return null;
        }
    }
}
