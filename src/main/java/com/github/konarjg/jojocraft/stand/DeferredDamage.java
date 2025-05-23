package com.github.konarjg.jojocraft.stand;

import net.minecraft.util.DamageSource;

import java.util.UUID;

public class DeferredDamage {
    private UUID entityId;
    private DamageSource source;
    private float damage;

    public DeferredDamage(UUID entityId, DamageSource source, float damage) {
        this.entityId = entityId;
        this.source = source;
        this.damage = damage;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public DamageSource getSource() {
        return source;
    }

    public void setSource(DamageSource source) {
        this.source = source;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
