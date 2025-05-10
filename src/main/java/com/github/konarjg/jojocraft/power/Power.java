package com.github.konarjg.jojocraft.power;

public class Power {
    private PowerType type;

    public Power() {
        type = PowerType.NONE;
    }

    public PowerType getType() {
        return type;
    }

    public void setType(PowerType type) {
        this.type = type;
    }
}
