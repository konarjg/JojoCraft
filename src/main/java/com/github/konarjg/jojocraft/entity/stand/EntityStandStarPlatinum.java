package com.github.konarjg.jojocraft.entity.stand;

import com.github.konarjg.jojocraft.event.TimeHandler;
import com.github.konarjg.jojocraft.objectholder.JojoPotions;
import com.github.konarjg.jojocraft.objectholder.JojoSounds;
import com.github.konarjg.jojocraft.stand.capability.Stand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class EntityStandStarPlatinum extends EntityStand {
    private int timeStopDuration;
    private int powerActivationTime;

    public EntityStandStarPlatinum(World world) {
        super(world);
        punchSound = JojoSounds.STAR_PLATINUM_PUNCH;
    }

    public EntityStandStarPlatinum(EntityPlayer user, World world, Stand stand) {
        super(user, world, stand, JojoSounds.STAR_PLATINUM_PUNCH);
    }


    @Override
    public void usePower() {
        if (stand == null || powerCooldown > 0 | powerActive) {
            return;
        }

        EntityPlayer user = getUser();

        if (user == null) {
            return;
        }

        world.playSound(null, posX, posY, posZ, JojoSounds.STAR_PLATINUM_TIME_STOP_ACTIVATE, SoundCategory.MASTER, 1.0f, 1.0f);
        timeStopDuration = 60;
        powerActivationTime = 70;
        powerCooldown = stand.getStats().getPotential() * 100;
        powerActive = true;
    }

    @Override
    public void power(EntityPlayer user) {
        if (powerActivationTime > 0) {
            powerActivationTime--;
            return;
        }

        if (timeStopDuration == 60) {
            TimeHandler.timeStopper = this;
            TimeHandler.timeStopperUser = user;
            user.addPotionEffect(new PotionEffect(JojoPotions.TIME_STOP_EFFECT, timeStopDuration));
        }

        if (timeStopDuration > 0) {
            timeStopDuration--;
        } else {
            timeStopDuration = 0;
            powerActive = false;
            TimeHandler.timeStopper = null;
            TimeHandler.timeStopperUser = null;
            world.playSound(null, posX, posY, posZ, JojoSounds.TIME_STOP_DEACTIVATE, SoundCategory.MASTER, 1.0f, 1.0f);
            user.addPotionEffect(new PotionEffect(JojoPotions.POWER_COOLDOWN_EFFECT, powerCooldown));
            return;
        }

        TimeHandler.timeStopper = this;
        TimeHandler.timeStopperUser = user;
    }
}