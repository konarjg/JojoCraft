package com.github.konarjg.jojocraft.packet;

import com.github.konarjg.jojocraft.event.TimeHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SetGrayFogPacket implements IMessage {
    public boolean grayFog;

    public SetGrayFogPacket() {}

    public SetGrayFogPacket(boolean grayFog) {
        this.grayFog = grayFog;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        grayFog = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(grayFog);
    }

    public static class Handler implements IMessageHandler<SetGrayFogPacket, IMessage> {

        @Override
        public IMessage onMessage(SetGrayFogPacket message, MessageContext ctx) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            TimeHandler.grayFog = message.grayFog;
            return null;
        }
    }
}