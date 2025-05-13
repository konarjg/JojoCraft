package com.github.konarjg.jojocraft.command;

import com.github.konarjg.jojocraft.event.PowerHandler;
import com.github.konarjg.jojocraft.power.Power;
import com.github.konarjg.jojocraft.power.PowerType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import java.util.Arrays;
import java.util.List;

public class CommandSetPower extends CommandBase {

    @Override
    public String getName() {
        return "setPower";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/setPower <power> - Sets the power of this player";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, net.minecraft.util.math.BlockPos pos) {
        if (args.length == 1) {
            return Arrays.asList("HAMON", "VAMPIRE", "NONE");
        }

        return super.getTabCompletions(server, sender, args, pos);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) {
            return;
        }

        Power power = ((EntityPlayer)sender).getCapability(PowerHandler.CAPABILITY_POWER, null);
        ((EntityPlayer)sender).curePotionEffects(new ItemStack(Items.MILK_BUCKET));
        power.setType(PowerType.valueOf(args[0]));
    }
}
