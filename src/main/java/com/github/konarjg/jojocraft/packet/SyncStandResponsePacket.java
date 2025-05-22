package com.github.konarjg.jojocraft.packet;

import com.github.konarjg.jojocraft.event.StandHandler;
import com.github.konarjg.jojocraft.stand.capability.Stand;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncStandResponsePacket implements IMessage {
    public Stand stand;

    public SyncStandResponsePacket() {

    }

    public SyncStandResponsePacket(Stand stand) {
        this.stand = stand;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        stand = new Stand();
        stand.setName(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, stand.getName());
    }

    public static class Handler implements IMessageHandler<SyncStandResponsePacket, IMessage> {

        @Override
        public IMessage onMessage(SyncStandResponsePacket message, MessageContext ctx) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            Stand stand = message.stand;

            Minecraft.getMinecraft().addScheduledTask(() -> {
                player.getCapability(StandHandler.CAPABILITY_STAND, null).setName(stand.getName());
            });

            return null;
        }
    }
}
