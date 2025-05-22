package com.github.konarjg.jojocraft.stand;

import com.github.konarjg.jojocraft.objectholder.JojoStands;

public class StandStatsStarPlatinum extends StandStats {
    public StandStatsStarPlatinum() {
        setPower(6);
        setSpeed(6);
        setRange(4);
        setStamina(6);
        setPrecision(6);
        setPotential(6);
        JojoStands.STAND_STATS.put("Star Platinum", this);
    }
}
