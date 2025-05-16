package com.github.konarjg.jojocraft.objectholder;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.damagesource.HamonDamageSource;
import com.github.konarjg.jojocraft.damagesource.SpinDamageSource;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Tags.MOD_ID)
public class JojoDamageSources {
    public static DamageSource HAMON_DAMAGE_SOURCE = new HamonDamageSource();
    public static DamageSource SPIN_DAMAGE_SOURCE = new SpinDamageSource();
}
