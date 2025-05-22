package com.github.konarjg.jojocraft.packet;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.event.StandHandler;
import com.github.konarjg.jojocraft.stand.capability.Stand;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncStandPacket implements IMessage {
    public SyncStandPacket() {}


    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<SyncStandPacket, IMessage> {

        @Override
        public IMessage onMessage(SyncStandPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            Stand stand = player.getCapability(StandHandler.CAPABILITY_STAND, null);

            player.getServerWorld().addScheduledTask(() -> {
                JojoCraft.NETWORK_INSTANCE.sendTo(new SyncStandResponsePacket(stand), player);
            });
            return null;
        }
    }
}
