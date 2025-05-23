package com.github.konarjg.jojocraft.entity.stand;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.event.StandHandler;
import com.github.konarjg.jojocraft.objectholder.JojoDamageSources;
import com.github.konarjg.jojocraft.objectholder.JojoSounds;
import com.github.konarjg.jojocraft.stand.capability.Stand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public abstract class EntityStand extends Entity {
    protected UUID userId;
    protected Stand stand;
    protected boolean active;
    protected boolean controlled;
    protected BlockPos target;
    protected SoundEvent punchSound;

    private boolean punching;
    private int punchDelay;
    private int punchingDuration;

    protected boolean powerActive;
    protected int powerCooldown;

    public EntityStand(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {

    }

    public EntityStand(EntityPlayer user, World world, Stand stand, SoundEvent punchSound) {
        super(world);
        this.userId = user.getUniqueID();
        this.stand = stand;
        this.active = true;
        this.controlled = false;
        this.target = null;
        this.powerActive = false;
    }

    public abstract void usePower();
    protected abstract void power(EntityPlayer user);

    private void handleFollow(EntityPlayer user) {
        Vec3d look = user.getLook(0.1f);
        Vec3d forward = new Vec3d(look.x, 0, look.z);
        Vec3d up = new Vec3d(0, 1, 0);
        Vec3d right = forward.crossProduct(up).normalize();

        Vec3d target = forward.scale(1.5).add(right.scale(0.8)).add(user.posX, user.posY, user.posZ);
        setPosition(target.x, target.y + 0.5, target.z);
    }

    private void handleControl(EntityPlayer user) {
        if (target == null) {
            return;
        }

        Vec3d delta = new Vec3d(0, 0, 0).add(target.getX(), target.getY(), target.getZ()).subtract(posX, posY, posZ);
        double distance = Math.sqrt(delta.lengthSquared() - delta.y * delta.y);
        double speed = stand.getStats().getSpeed() / 10.0;

        if (distance > speed + 0.1) {
            posX += (delta.x / distance) * (stand.getStats().getSpeed() / 10.0);
            posZ += (delta.z / distance) * (stand.getStats().getSpeed() / 10.0);
            posY = world.getHeight(getPosition().getX(), getPosition().getZ()) + 0.5;
            return;
        }

        target = null;
    }

    private void handleRange(EntityPlayer user) {
        if (getDistance(user) > stand.getStats().getRange() * 4) {
            Vec3d forward = user.getLookVec();
            Vec3d up = new Vec3d(0, 1, 0);
            Vec3d right = forward.crossProduct(up);
            Vec3d target = forward.add(right).add(user.posX, user.posY, user.posZ);
            setPosition(target.x, user.posY + 0.5, target.z);
        }
    }

    private void handlePunching() {
        if (!punching) {
            return;
        }

        if (punchingDuration > 0) {
            if (punchDelay > 0) {
                punchDelay--;
            }
            else {
                punch();
                punchDelay = 7 - stand.getStats().getSpeed();
            }

            punchingDuration--;
            return;
        }

        punching = false;
        punchingDuration = 0;
    }

    public void startPunching() {
        if (punching) {
            return;
        }

        punchDelay = 7 - stand.getStats().getSpeed();
        punchingDuration = 100;
        world.playSound(null, posX, posY, posZ, punchSound, SoundCategory.MASTER, 1.0f, 1.0f);
        punching = true;
    }

    public void punch() {
        Vec3d look = this.getLook(0.1f);
        Vec3d forward = new Vec3d(look.x, 0, look.z);

        Vec3d hitboxStart = forward.add(posX, posY, posZ);
        Vec3d hitboxEnd = hitboxStart.add(forward.scale(3f)).add(0, 1, 0);

        AxisAlignedBB hitbox = new AxisAlignedBB(hitboxStart, hitboxEnd);
        List<Entity> potentialTargets = world.getEntitiesWithinAABB(Entity.class, hitbox);
        Entity target = null;
        double minDistance = Double.MAX_VALUE;

        for (Entity potentialTarget : potentialTargets) {
            if (potentialTarget.canBeAttackedWithItem() && getDistance(potentialTarget) < minDistance) {
                target = potentialTarget;
                minDistance = getDistance(potentialTarget);
            }
        }

        if (target == null) {
            return;
        }

        target.attackEntityFrom(JojoDamageSources.STAND_DAMAGE_SOURCE, stand.getStats().getPower());
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source != JojoDamageSources.STAND_DAMAGE_SOURCE) {
            return false;
        }

        EntityPlayer user = getUser();

        if (user == null) {
            return false;
        }

        double dodgeChance = 0.04 * stand.getStats().getPrecision();

        if (Math.random() < dodgeChance) {
            return false;
        }

        user.attackEntityFrom(JojoDamageSources.STAND_DAMAGE_SOURCE, amount);
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (world.isRemote || !active) {
            return;
        }

        EntityPlayer user = getUser();

        if (user == null) {
            return;
        }

        stand = user.getCapability(StandHandler.CAPABILITY_STAND, null);

        if (stand == null) {
            return;
        }

        if (powerActive) {
            power(user);
        } else if (powerCooldown > 0){
            powerCooldown--;
        } else {
            powerCooldown = 0;
        }

        handlePunching();
        handleRange(user);
        setRotation(user.rotationYaw, user.rotationPitch);

        if (controlled) {
            handleControl(user);
            return;
        }

        handleFollow(user);
    }

    public EntityPlayer getUser() {
        return world.getPlayerEntityByUUID(userId);
    }

    public Stand getStand() {
        return stand;
    }

    public void setUser(EntityPlayer user) {
        this.userId = user.getUniqueID();
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        if (active) {
            EntityPlayer user = getUser();
            Vec3d forward = user.getLookVec();
            Vec3d up = new Vec3d(0, 1, 0);
            Vec3d right = forward.crossProduct(up);
            Vec3d target = forward.add(right).add(user.posX, user.posY, user.posZ);
            setPositionAndUpdate(target.x, target.y, target.z);
        }

        setInvisible(!active);
        noClip = !active;
        this.active = active;
    }

    public boolean isControlled() {
        return controlled;
    }

    public void setControlled(boolean controlled) {
        this.controlled = controlled;
    }

    public BlockPos getTarget() {
        return target;
    }

    public void setTarget(BlockPos target) {
        this.target = target;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setUniqueId("user", userId);
        compound.setBoolean("active", active);
        compound.setInteger("powerCooldown", powerCooldown);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        userId = compound.getUniqueId("user");
        active = compound.getBoolean("active");
        powerCooldown = compound.getInteger("powerCooldown");
        noClip = !active;
        setInvisible(!active);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setUniqueId("user", userId);
        compound.setBoolean("active", active);
        compound.setInteger("powerCooldown", powerCooldown);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {
        super.deserializeNBT(compound);
        userId = compound.getUniqueId("user");
        active = compound.getBoolean("active");
        powerCooldown = compound.getInteger("powerCooldown");
        noClip = !active;
        setInvisible(!active);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        userId = compound.getUniqueId("user");
        active = compound.getBoolean("active");
        powerCooldown = compound.getInteger("powerCooldown");
        noClip = !active;
        setInvisible(!active);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setUniqueId("user", userId);
        compound.setBoolean("active", active);
        compound.setInteger("powerCooldown", powerCooldown);
        noClip = !active;
        setInvisible(!active);
    }
}
