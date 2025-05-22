package com.github.konarjg.jojocraft.packet;

import com.github.konarjg.jojocraft.event.StandHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SetStandResponsePacket implements IMessage {
    public String selectedStand;

    public SetStandResponsePacket() {}

    public SetStandResponsePacket(String selectedStand) {
        this.selectedStand = selectedStand;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        selectedStand = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, selectedStand);
    }

    public static class Handler implements IMessageHandler<SetStandResponsePacket, IMessage> {

        @Override
        public IMessage onMessage(SetStandResponsePacket message, MessageContext ctx) {
            EntityPlayer player = Minecraft.getMinecraft().player;

            Minecraft.getMinecraft().addScheduledTask(() -> {
                player.getCapability(StandHandler.CAPABILITY_STAND, null).setName(message.selectedStand);
                player.sendMessage(new TextComponentString("Your Stand name is " + message.selectedStand));
            });

            return null;
        }
    }
}
