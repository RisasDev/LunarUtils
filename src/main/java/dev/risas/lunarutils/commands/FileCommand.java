package dev.risas.lunarutils.commands;

import dev.risas.lunarutils.files.WaypointFile;
import dev.risas.lunarutils.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FileCommand implements CommandExecutor {

    public FileCommand() {
        Bukkit.getPluginCommand("file").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("lunarutils.command.file")) {
            sender.sendMessage(CC.translate("&cYou don't have permission to execute this command."));
            return true;
        }

        if (args.length < 1) {
            this.getUsage(sender, label);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            WaypointFile.getConfig().reload();

            sender.sendMessage(CC.translate("&aAll files has been reloaded."));
        }
        else {
            this.getUsage(sender, label);
        }
        return true;
    }

    private void getUsage(CommandSender sender, String label) {
        sender.sendMessage(CC.translate("&cUsage: /" + label + " reload"));
    }
}
