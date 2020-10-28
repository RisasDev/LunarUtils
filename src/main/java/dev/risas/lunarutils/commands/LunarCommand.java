package dev.risas.lunarutils.commands;

import dev.risas.lunarutils.files.WaypointFile;
import dev.risas.lunarutils.manager.CheckManager;
import dev.risas.lunarutils.manager.InventoryManager;
import dev.risas.lunarutils.manager.StaffModulesManager;
import dev.risas.lunarutils.manager.WaypointManager;
import dev.risas.lunarutils.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LunarCommand implements CommandExecutor {

    public LunarCommand() {
        Bukkit.getPluginCommand("lunar").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cNo console."));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("lunarutils.command.lunar")) {
            sender.sendMessage(CC.translate("&cYou don't have permission to execute this command."));
            return true;
        }

        if (args.length < 1) {
            this.getUtilsUsage(player, label);
            return true;
        }
        
        if (args[0].equalsIgnoreCase("staffmodules")) {
        	
        	StaffModulesManager.setStaffModules(player);
        	player.sendMessage(CC.translate("&7&oStaff Modules only work on the 1.7.10 Version of Lunar Client."));
        }
        else if (args[0].equalsIgnoreCase("waypoint")) {

            if (args.length < 2) {
                this.getWaypointUsage(player, label);
                return true;
            }

            if (args[1].equalsIgnoreCase("create")) {

                if (args.length < 3) {
                    player.sendMessage(CC.translate("&cUsage: /" + label + " waypoint create <name>"));
                    return true;
                }

                StringBuilder sb = new StringBuilder();

                for (int i = 2; i < args.length; ++i) {
                    sb.append(args[i]).append(' ');
                }

                WaypointManager.setWaypointName(sb.toString());

                if (WaypointFile.getConfig().getConfigurationSection("WAYPOINTS." + WaypointManager.getWaypointName()) != null) {
                    player.sendMessage(CC.translate("&c" + WaypointManager.getWaypointName() + "Waypoint is already created."));
                    return true;
                }

                player.openInventory(InventoryManager.getWaypoint());
            }
            else if (args[1].equalsIgnoreCase("delete")) {

                if (args.length < 3) {
                    player.sendMessage(CC.translate("&cUsage: /" + label + " waypoint delete <name>"));
                    return true;
                }

                StringBuilder sb = new StringBuilder();

                for (int i = 2; i < args.length; ++i) {
                    sb.append(args[i]).append(" ");
                }

                WaypointManager.setWaypointName(sb.toString());

                if (WaypointFile.getConfig().getConfigurationSection("WAYPOINTS." + WaypointManager.getWaypointName()) == null) {
                    player.sendMessage(CC.translate("&c" + WaypointManager.getWaypointName() + "Waypoint is already deleted."));
                    return true;
                }
                
                WaypointManager.deleteWaypointLunar(WaypointManager.getWaypointName());
                WaypointManager.deleteWaypointFile(WaypointManager.getWaypointName());

                player.sendMessage(CC.translate("&b" + WaypointManager.getWaypointName() + "Waypoint has been delete."));
            }
            else if (args[1].equalsIgnoreCase("list")) {
                WaypointManager.showWaypoints(player);
            }
            else {
                this.getWaypointUsage(player, label);
            }
        }
        else if (args[0].equalsIgnoreCase("check")) {
        	
        	if (args.length < 2) {
                this.getCheckUsage(player, label);
                return true;
            }
        	
        	Player target = Bukkit.getPlayer(args[1]);
        	
        	if (target == null) {
        		player.sendMessage(CC.translate("&c" + args[1] + " is not connected to the server."));
        		return true;
        	}
        	
        	CheckManager.LCAC(player, target);
        }
        else if (args[0].equalsIgnoreCase("online")) {
        	CheckManager.onlinePlayersLC(player);
        }
        else {
            this.getUtilsUsage(player, label);
        }
        return true;
    }

    private void getUtilsUsage(Player player, String label) {
        player.sendMessage(CC.translate("&3&m--------------------------------"));
        player.sendMessage(CC.translate("&b&lLunar Client Utils"));
        player.sendMessage(CC.translate(""));
        player.sendMessage(CC.translate("&b/" + label + " staffmodules"));
        player.sendMessage(CC.translate("&b/" + label + " waypoint"));
        player.sendMessage(CC.translate("&b/" + label + " check"));
        player.sendMessage(CC.translate("&b/" + label + " online"));
        player.sendMessage(CC.translate("&3&m--------------------------------"));
    }

    private void getWaypointUsage(Player player, String label) {
        player.sendMessage(CC.translate("&3&m--------------------------------"));
        player.sendMessage(CC.translate("&b&lLunar Client Waypoints"));
        player.sendMessage(CC.translate(""));
        player.sendMessage(CC.translate("&b/" + label + " waypoint create <name>"));
        player.sendMessage(CC.translate("&b/" + label + " waypoint delete <name>"));
        player.sendMessage(CC.translate("&b/" + label + " waypoint list"));
        player.sendMessage(CC.translate("&3&m--------------------------------"));
    }
    
    private void getCheckUsage(Player player, String label) {
        player.sendMessage(CC.translate("&3&m--------------------------------"));
        player.sendMessage(CC.translate("&b&lLunar Client Check"));
        player.sendMessage(CC.translate(""));
        player.sendMessage(CC.translate("&b/" + label + " check <player>"));
        player.sendMessage(CC.translate("&3&m--------------------------------"));
    }
}
