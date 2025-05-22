package com.github.konarjg.jojocraft.packet;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.event.StandHandler;
import com.github.konarjg.jojocraft.stand.capability.Stand;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SetStandPacket implements IMessage {
    public String standName;

    public SetStandPacket() {

    }

    public SetStandPacket(String standName) {
        this.standName = standName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        standName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, standName);
    }

    public static class Handler implements IMessageHandler<SetStandPacket, IMessage> {
        @Override
        public IMessage onMessage(SetStandPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            player.getServerWorld().addScheduledTask(() -> {
                Stand stand = player.getCapability(StandHandler.CAPABILITY_STAND, null);
                stand.setName(message.standName);

                JojoCraft.NETWORK_INSTANCE.sendTo(new SetStandResponsePacket(stand.getName()), player);
            });

            return null;
        }
    }
}
