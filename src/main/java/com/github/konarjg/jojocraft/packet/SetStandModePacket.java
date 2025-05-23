package com.github.konarjg.jojocraft.packet;

import com.github.konarjg.jojocraft.entity.stand.EntityStand;
import com.github.konarjg.jojocraft.event.StandHandler;
import com.github.konarjg.jojocraft.stand.capability.Stand;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SetStandModePacket implements IMessage {
    public boolean manualMode;

    public SetStandModePacket() {

    }

    public SetStandModePacket(boolean manualMode) {
        this.manualMode = manualMode;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        manualMode = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(manualMode);
    }

    public static class Handler implements IMessageHandler<SetStandModePacket, IMessage> {

        @Override
        public IMessage onMessage(SetStandModePacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            WorldServer world = player.getServerWorld();
            Stand stand = player.getCapability(StandHandler.CAPABILITY_STAND, null);

            if (stand.getEntityId() == null) {
                return null;
            }

            Entity entity = world.getEntityFromUuid(stand.getEntityId());

            if (entity == null) {
                return null;
            }

            EntityStand entityStand = (EntityStand) entity;

            if (!entityStand.isActive()) {
                return null;
            }

            entityStand.setControlled(message.manualMode);

            if (message.manualMode) {
                Vec3d start = player.getLook(0.1f).add(player.posX, player.posY + player.eyeHeight, player.posZ);
                Vec3d end = start.add(player.getLookVec().scale(stand.getStats().getRange() * 4));
                RayTraceResult result = world.rayTraceBlocks(start, end);

                if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                    BlockPos target = result.getBlockPos();
                    entityStand.setTarget(target);
                }
            }
            return null;
        }
    }
}
