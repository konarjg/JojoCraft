package com.github.konarjg.jojocraft.damagesource;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class GoldenSpinDamageSource extends DamageSource {
    public GoldenSpinDamageSource() {
        super("golden_spin");
    }

    @Override
    public boolean canHarmInCreative() {
        return true;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entity) {
        return new TextComponentString(entity.getName() + " was obliterated by Golden Spin!");
    }
}
