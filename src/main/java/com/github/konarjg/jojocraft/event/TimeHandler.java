package com.github.konarjg.jojocraft.event;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.entity.stand.EntityStand;
import com.github.konarjg.jojocraft.packet.SetGrayFogPacket;
import com.github.konarjg.jojocraft.stand.DeferredDamage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.util.*;

@Mod.EventBusSubscriber
public class TimeHandler {
    public static EntityStand timeStopper = null;
    public static EntityPlayer timeStopperUser = null;

    public static boolean grayFog = false;

    private static Map<UUID, Vec3d> velocities = new HashMap<>();
    private static final List<DeferredDamage> damages = new ArrayList<>();
    private static final Map<UUID, Integer> creeperFuses = new HashMap<>();
    private static final List<TileEntity> tileEntities = new ArrayList<>();

    private static Field timeSinceIgnitedField = null;


    static {
        try {
            try {
                timeSinceIgnitedField = EntityCreeper.class.getDeclaredField("timeSinceIgnited");
            } catch (NoSuchFieldException e) {
                timeSinceIgnitedField = EntityCreeper.class.getDeclaredField("field_70833_d");
            }

            timeSinceIgnitedField.setAccessible(true);
        } catch (NoSuchFieldException e) {

        }
    }

    public static void setGrayFog(boolean grayFog) {
        TimeHandler.grayFog = grayFog;

        JojoCraft.NETWORK_INSTANCE.sendTo(new SetGrayFogPacket(grayFog), (EntityPlayerMP) timeStopperUser);
    }

    private static int getCreeperFuse(EntityCreeper creeper) {
        if (timeSinceIgnitedField != null) {
            try {
                return timeSinceIgnitedField.getInt(creeper);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    private static void setCreeperFuse(EntityCreeper creeper, int fuse) {
        if (timeSinceIgnitedField != null) {
            try {
                timeSinceIgnitedField.setInt(creeper, fuse);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (event.world.isRemote) {
            return;
        }

        if (timeStopper == null) {
            event.world.tickableTileEntities.addAll(tileEntities);

            for (Entity entity : new ArrayList<>(event.world.loadedEntityList)) {
                Vec3d previousVelocity = velocities.get(entity.getUniqueID());

                if (entity instanceof EntityCreeper && creeperFuses.get(entity.getUniqueID()) != null) {
                    setCreeperFuse((EntityCreeper) entity, creeperFuses.get(entity.getUniqueID()));
                }

                float totalDamage = 0f;

                for (DeferredDamage damage : damages) {
                    if (damage.getEntityId().equals(entity.getUniqueID())) {
                        entity.attackEntityFrom(damage.getSource(), damage.getDamage());
                        entity.hurtResistantTime = 0;
                        totalDamage += damage.getDamage();
                    }
                }

                entity.setNoGravity(false);

                if (totalDamage != 0f) {
                    Vec3d look = entity.getLook(0.1f).normalize().scale(3f + totalDamage / 10f);
                    entity.motionX -= look.x;
                    entity.motionY -= look.y;
                    entity.motionZ -= look.z;
                    JojoCraft.LOGGER.error(totalDamage);
                    entity.velocityChanged = true;
                }

                if (previousVelocity == null) {
                    continue;
                }

                entity.motionX += previousVelocity.x;
                entity.motionY += previousVelocity.y;
                entity.motionZ += previousVelocity.z;

                if (entity instanceof EntityArrow) {
                    ((EntityArrow) entity).onGround = false;
                }

                entity.velocityChanged = true;
            }

            velocities.clear();
            damages.clear();
            tileEntities.clear();
            return;
        }

        for (TileEntity tileEntity : event.world.tickableTileEntities) {
            if (tileEntities.contains(tileEntity)) {
                continue;
            }

            tileEntities.add(tileEntity);
        }

        event.world.tickableTileEntities.clear();

        for (Entity entity : event.world.loadedEntityList) {
            if (entity.getUniqueID().equals(timeStopper.getUniqueID()) || entity.getUniqueID().equals(timeStopperUser.getUniqueID())) {
                continue;
            }

            if (entity instanceof EntitySkeleton) {
                ((EntitySkeleton) entity).setAttackTarget(null);
            }

            if (entity instanceof EntityCreeper) {
                int fuse = getCreeperFuse((EntityCreeper) entity);

                if (creeperFuses.get(entity.getUniqueID()) == null) {
                    creeperFuses.put(entity.getUniqueID(), fuse);
                    return;
                }

                setCreeperFuse((EntityCreeper) entity, creeperFuses.get(entity.getUniqueID()));
            }

            if (velocities.get(entity.getUniqueID()) != null) {
                continue;
            }

            velocities.put(entity.getUniqueID(), new Vec3d(entity.motionX, entity.motionY, entity.motionZ));
            entity.motionX = 0;
            entity.motionY = 0;
            entity.motionZ = 0;
            entity.setNoGravity(true);
            entity.velocityChanged = true;

            if (entity instanceof EntityArrow) {
                ((EntityArrow) entity).arrowShake = 0;
                ((EntityArrow) entity).onGround = false;
                ((EntityArrow) entity).isAirBorne = true;
            }
        }
    }

    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity().world.isRemote) {
            return;
        }

        if (timeStopper == null) {
            return;
        }

        if (event.getEntity().getUniqueID().equals(timeStopper.getUniqueID()) || event.getEntity().getUniqueID().equals(timeStopperUser.getUniqueID())) {
            return;
        }

        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event) {
        if (timeStopper == null) {
            return;
        }

        UUID entityId = event.getEntity().getUniqueID();
        DamageSource source = event.getSource();
        float damage = event.getAmount();

        damages.add(new DeferredDamage(entityId, source, damage));
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onPlayerInteractLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (timeStopper != null && timeStopperUser != null &&
                !event.getEntityPlayer().getUniqueID().equals(timeStopperUser.getUniqueID())) {
            event.setCanceled(true);
        } else if (timeStopper != null && timeStopperUser == null) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (timeStopper != null && timeStopperUser != null &&
                !event.getEntityPlayer().getUniqueID().equals(timeStopperUser.getUniqueID())) {
            event.setCanceled(true);
        } else if (timeStopper != null && timeStopperUser == null) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (timeStopper != null && timeStopperUser != null &&
                !event.getEntityPlayer().getUniqueID().equals(timeStopperUser.getUniqueID())) {
            event.setCanceled(true);
        } else if (timeStopper != null && timeStopperUser == null) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        if (timeStopper != null && timeStopperUser != null &&
                !event.getEntityPlayer().getUniqueID().equals(timeStopperUser.getUniqueID())) {
            event.setCanceled(true);
        } else if (timeStopper != null && timeStopperUser == null) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractEntitySpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        if (timeStopper != null && timeStopperUser != null &&
                !event.getEntityPlayer().getUniqueID().equals(timeStopperUser.getUniqueID())) {
            event.setCanceled(true);
        } else if (timeStopper != null && timeStopperUser == null) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player != null) {
            if (timeStopper != null && timeStopperUser != null &&
                    !player.getUniqueID().equals(timeStopperUser.getUniqueID())) {
                event.setCanceled(true);
            } else if (timeStopper != null && timeStopperUser == null) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.PlaceEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player != null) {
            if (timeStopper != null && timeStopperUser != null &&
                    !player.getUniqueID().equals(timeStopperUser.getUniqueID())) {
                event.setCanceled(true);
            } else if (timeStopper != null && timeStopperUser == null) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onMultiBlockPlace(BlockEvent.MultiPlaceEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player != null) {
            if (timeStopper != null && timeStopperUser != null &&
                    !player.getUniqueID().equals(timeStopperUser.getUniqueID())) {
                event.setCanceled(true);
            } else if (timeStopper != null && timeStopperUser == null) {
                event.setCanceled(true);
            }
        }
    }


    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player != null) {
            if (timeStopper != null && timeStopperUser != null &&
                    !player.getUniqueID().equals(timeStopperUser.getUniqueID())) {
                event.setCanceled(true);
            } else if (timeStopper != null && timeStopperUser == null) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerPickupItem(EntityItemPickupEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if (player != null) {
            if (timeStopper != null && timeStopperUser != null &&
                    !player.getUniqueID().equals(timeStopperUser.getUniqueID())) {
                event.setCanceled(true);
            } else if (timeStopper != null && timeStopperUser == null) {
                event.setCanceled(true);
            }
        }
    }
}