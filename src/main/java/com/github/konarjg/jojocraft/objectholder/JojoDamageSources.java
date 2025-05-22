package com.github.konarjg.jojocraft.objectholder;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.damagesource.GoldenSpinDamageSource;
import com.github.konarjg.jojocraft.damagesource.HamonDamageSource;
import com.github.konarjg.jojocraft.damagesource.SpinDamageSource;
import com.github.konarjg.jojocraft.damagesource.StandDamageSource;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Tags.MOD_ID)
public class JojoDamageSources {
    public static DamageSource HAMON_DAMAGE_SOURCE = new HamonDamageSource();
    public static DamageSource SPIN_DAMAGE_SOURCE = new SpinDamageSource();
    public static DamageSource GOLDEN_SPIN_DAMAGE_SOURCE = new GoldenSpinDamageSource();
    public static DamageSource STAND_DAMAGE_SOURCE = new StandDamageSource();
}
