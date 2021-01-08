package dev.risas.lunarutils.commands;

import dev.risas.lunarutils.LunarUtils;
import dev.risas.lunarutils.files.ConfigFile;
import dev.risas.lunarutils.files.WaypointFile;
import dev.risas.lunarutils.manager.menu.waypoint.WaypointMenu;
import dev.risas.lunarutils.utilities.CC;
import dev.risas.lunarutils.utilities.commands.BaseCommand;
import dev.risas.lunarutils.utilities.commands.Command;
import dev.risas.lunarutils.utilities.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LunarCommand extends BaseCommand {

    private final LunarUtils plugin = LunarUtils.getInstance();

    @Command(name = "lunar", permission = "lunarutils.lunar", aliases = {"lunarutils", "lc"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String label = command.getLabel();
        String[] args = command.getArgs();

        if (args.length < 1) {
            this.getUsage(player, label);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "waypoint":
                if (!player.hasPermission("lunarutils.lunar.waypoint")) {
                    player.sendMessage(CC.translate("&cNo permission."));
                    return;
                }

                if (args.length < 2) {
                    this.getWaypointUsage(player, label);
                    return;
                }

                switch (args[1].toLowerCase()) {
                    case "create":
                        if (args.length < 3) {
                            player.sendMessage(CC.translate("&cUsage: /" + label + " waypoint create <name>"));
                            return;
                        }

                        StringBuilder sb = new StringBuilder();

                        for (int i = 2; i < args.length; ++i) {
                            sb.append(args[i]).append(' ');
                        }

                        this.plugin.getWaypointManager().setName(sb.toString());

                        if (WaypointFile.getConfig().getConfigurationSection("WAYPOINTS." + this.plugin.getWaypointManager().getName()) != null) {
                            player.sendMessage(CC.translate("&c" + this.plugin.getWaypointManager().getName() + "Waypoint is already created."));
                            return;
                        }

                        WaypointMenu.getWaypoint(player);
                        break;
                    case "delete":
                        if (args.length < 3) {
                            player.sendMessage(CC.translate("&cUsage: /" + label + " waypoint delete <name>"));
                            return;
                        }

                        StringBuilder stringBuilder = new StringBuilder();

                        for (int i = 2; i < args.length; ++i) {
                            stringBuilder.append(args[i]).append(" ");
                        }

                        this.plugin.getWaypointManager().setName(stringBuilder.toString());

                        String waypointName = this.plugin.getWaypointManager().getName();

                        if (WaypointFile.getConfig().getConfigurationSection("WAYPOINTS." + waypointName) == null) {
                            player.sendMessage(CC.translate("&c" + waypointName + "Waypoint is already deleted."));
                            return;
                        }

                        this.plugin.getWaypointManager().deleteWaypointOnline(waypointName);
                        this.plugin.getWaypointManager().deleteWaypointFile(waypointName);
                        player.sendMessage(CC.translate("&a" + waypointName + "Waypoint has been delete."));
                        break;
                    case "list":
                        this.plugin.getWaypointManager().availablesWaypoints(player);
                        break;
                    default:
                        this.getWaypointUsage(player, label);
                        break;
                }
                break;
            case "check":
                if (!player.hasPermission("lunarutils.lunar.check")) {
                    player.sendMessage(CC.translate("&cNo permission."));
                    return;
                }

                if (args.length < 2) {
                    this.getCheckUsage(player, label);
                    return;
                }

                Player target = Bukkit.getPlayer(args[1]);

                if (target == null) {
                    player.sendMessage(CC.translate("&c" + args[1] + " is not connected to the server."));
                    return;
                }

                this.plugin.getCheckManager().antiCheat(player, target);
                break;
            case "online":
                if (!player.hasPermission("lunarutils.lunar.online")) {
                    player.sendMessage(CC.translate("&cNo permission."));
                    return;
                }

                this.plugin.getCheckManager().lunarPlayers(player);
                break;
            case "reload":
                if (!player.hasPermission("lunarutils.lunar.reload")) {
                    player.sendMessage(CC.translate("&cNo permission."));
                    return;
                }

                ConfigFile.getConfig().reload();
                WaypointFile.getConfig().reload();
                player.sendMessage(CC.translate("&aAll files has been reloaded."));
                break;
            default:
                this.getUsage(player, label);
                break;
        }
    }

    private void getUsage(Player player, String label) {
        player.sendMessage(CC.translate("&3&m--------------------------------"));
        player.sendMessage(CC.translate("&b&lLunar Client Utils"));
        player.sendMessage(CC.translate(""));
        player.sendMessage(CC.translate("&b/" + label + " waypoint"));
        player.sendMessage(CC.translate("&b/" + label + " check"));
        player.sendMessage(CC.translate("&b/" + label + " online"));
        player.sendMessage(CC.translate("&b/" + label + " reload"));
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
