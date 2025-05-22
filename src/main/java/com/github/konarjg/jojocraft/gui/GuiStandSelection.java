package com.github.konarjg.jojocraft.gui;

import com.github.konarjg.jojocraft.JojoCraft;
import com.github.konarjg.jojocraft.event.StandHandler;
import com.github.konarjg.jojocraft.objectholder.JojoStands;
import com.github.konarjg.jojocraft.packet.SetStandPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;
import java.util.stream.Collectors;

public class GuiStandSelection extends GuiScreen {
    private final EntityPlayer player;
    private List<String> standNames = JojoStands.STAND_STATS.keySet().stream().collect(Collectors.toList());
    private int scrollOffset = 0;
    private int maxVisible = 5;

    public GuiStandSelection(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void initGui() {
        for (int i = 0; i < Math.min(standNames.size(), maxVisible); i++) {
            this.buttonList.add(new GuiButton(i, this.width / 2 - 100, 50 + (i * 25), 200, 20, standNames.get(i)));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (scrollOffset > 0) {
            this.buttonList.add(new GuiButton(100, this.width / 2 - 100, 30, 200, 20, "Scroll Up"));
        }
        if (scrollOffset + maxVisible < standNames.size()) {
            this.buttonList.add(new GuiButton(101, this.width / 2 - 100, 180, 200, 20, "Scroll Down"));
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 100) {
            scrollOffset = Math.max(0, scrollOffset - 1);
        } else if (button.id == 101) {
            scrollOffset = Math.min(standNames.size() - maxVisible, scrollOffset + 1);
        } else {
            String selectedStand = standNames.get(button.id);
            JojoCraft.NETWORK_INSTANCE.sendToServer(new SetStandPacket(selectedStand));
            this.mc.displayGuiScreen(null);
        }
    }
}
