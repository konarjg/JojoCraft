package com.github.konarjg.jojocraft.gui;

import com.github.konarjg.jojocraft.stand.capability.Stand;
import ibxm.Player;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class GuiStandStats extends GuiScreen {
    private final EntityPlayer player;
    private final Stand stand;

    public GuiStandStats(EntityPlayer player, Stand stand) {
        this.player = player;
        this.stand = stand;
    }

    public String statAsLetter(int stat) {
        if (stat == 7) {
            return "S";
        }

        char letter = (char)('A' + (6 - stat));

        return String.valueOf(letter);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        this.drawCenteredString(this.fontRenderer, "Stand: " + stand.getName(), this.width / 2, this.height / 2 - 60, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, "User: " + player.getName(), this.width / 2, this.height / 2 - 40, 0xFFFFFF);

        if (stand.getName().equals("NONE")) {
            super.drawScreen(mouseX, mouseY, partialTicks);
            return;
        }

        int startY = this.height / 2 - 10;
        this.drawString(this.fontRenderer, "Power: " + statAsLetter(stand.getStats().getPower()), this.width / 2 - 50, startY, 0xFFFFFF);
        this.drawString(this.fontRenderer, "Speed: " + statAsLetter(stand.getStats().getSpeed()), this.width / 2 - 50, startY + 15, 0xFFFFFF);
        this.drawString(this.fontRenderer, "Range: " + statAsLetter(stand.getStats().getRange()), this.width / 2 - 50, startY + 30, 0xFFFFFF);
        this.drawString(this.fontRenderer, "Stamina: " + statAsLetter(stand.getStats().getStamina()), this.width / 2 - 50, startY + 45, 0xFFFFFF);
        this.drawString(this.fontRenderer, "Precision: " + statAsLetter(stand.getStats().getPrecision()), this.width / 2 - 50, startY + 60, 0xFFFFFF);
        this.drawString(this.fontRenderer, "Potential: " + statAsLetter(stand.getStats().getPotential()), this.width / 2 - 50, startY + 75, 0xFFFFFF);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
