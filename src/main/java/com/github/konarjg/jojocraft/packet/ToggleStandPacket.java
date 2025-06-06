package com.github.konarjg.jojocraft.packet;

import com.github.konarjg.jojocraft.entity.stand.EntityStand;
import com.github.konarjg.jojocraft.event.StandHandler;
import com.github.konarjg.jojocraft.event.TimeHandler;
import com.github.konarjg.jojocraft.stand.StandFactory;
import com.github.konarjg.jojocraft.stand.capability.Stand;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ToggleStandPacket implements IMessage {

    public ToggleStandPacket(){}

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<ToggleStandPacket, IMessage> {

        @Override
        public IMessage onMessage(ToggleStandPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            WorldServer world = player.getServerWorld();
            Stand stand = player.getCapability(StandHandler.CAPABILITY_STAND, null);

            if (TimeHandler.timeStopperUser != null && !TimeHandler.timeStopperUser.getUniqueID().equals(player)) {
                return null;
            }

            if (stand.getName() == "NONE") {
                return null;
            }

            if (stand.getEntityId() != null) {
                EntityStand entity = (EntityStand)world.getEntityFromUuid(stand.getEntityId());

                if (entity != null) {
                    entity.setActive(!entity.isActive());
                    return null;
                }

                return null;
            }

            Vec3d look = player.getLook(0.1f);
            Vec3d forward = new Vec3d(look.x, 0, look.z);
            Vec3d up = new Vec3d(0, 1, 0);
            Vec3d right = forward.crossProduct(up);

            Vec3d target = forward.scale(1f).add(right.scale(1f)).add(player.posX, player.posY, player.posZ);
            StandFactory.createStand(world, player, stand, target);

            return null;
        }
    }
}
