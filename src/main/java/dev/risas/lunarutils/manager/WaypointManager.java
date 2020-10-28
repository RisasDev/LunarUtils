package dev.risas.lunarutils.manager;

import dev.risas.lunarutils.LunarUtils;
import dev.risas.lunarutils.files.WaypointFile;
import dev.risas.lunarutils.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.lunarclient.bukkitapi.LunarClientAPI;
import com.lunarclient.bukkitapi.object.LCWaypoint;

import java.util.Set;

public class WaypointManager {

    public static String waypointName;
    public static int waypointColor;

    public WaypointManager() {
        waypointName = null;
        waypointColor = 0;
    }

    public static void createWaypointFile(String waypoint, Player player) {
        WaypointFile.getConfig().set("WAYPOINTS." + waypoint + ".WORLD", player.getWorld().getName());
        WaypointFile.getConfig().set("WAYPOINTS." + waypoint + ".X", player.getLocation().getBlockX());
        WaypointFile.getConfig().set("WAYPOINTS." + waypoint + ".Y", player.getLocation().getBlockY());
        WaypointFile.getConfig().set("WAYPOINTS." + waypoint + ".Z", player.getLocation().getBlockZ());
        WaypointFile.getConfig().set("WAYPOINTS." + waypoint + ".COLOR", waypointColor);
        WaypointFile.getConfig().save();
        WaypointFile.getConfig().reload();
    }

    public static void createPlayersWaypointLunar(String waypoint) {
        for (Player online : Bukkit.getOnlinePlayers()) {
        	
        	if (LunarClientAPI.getInstance().isRunningLunarClient(online)) {
        		LunarClientAPI.getInstance().sendWaypoint(online, getWaypoint(waypoint));
            }
        }
    }

    public static void createPlayerWaypointLunar(Player player, String waypoint) {
    	new BukkitRunnable() {
			
			@Override
			public void run() {
                if (LunarClientAPI.getInstance().isRunningLunarClient(player)) {
                    LunarClientAPI.getInstance().sendWaypoint(player, getWaypoint(waypoint));
                }
			}
		}.runTaskLater(LunarUtils.getInstance(), 20L);
    }

    public static void deleteWaypointFile(String waypoint) {
        WaypointFile.getConfig().set("WAYPOINTS." + waypoint, null);
        WaypointFile.getConfig().save();
        WaypointFile.getConfig().reload();
    }

    public static void deleteWaypointLunar(String waypoint) {
        for (Player online : Bukkit.getOnlinePlayers()) {
        	
        	if (LunarClientAPI.getInstance().isRunningLunarClient(online)) {
            	LunarClientAPI.getInstance().removeWaypoint(online, getWaypoint(waypoint));
            }
        }
    }

    public static String getWaypointName() {
        return waypointName;
    }

    public static void setWaypointName(String name) {
        waypointName = name;
    }

    public static int getWaypointColor(String waypoint) {
        return WaypointFile.getConfig().getInt("WAYPOINTS." + waypoint + ".COLOR");
    }

    public static void setWaypointColor(int color) {
        waypointColor = color;
    }
    
    public static LCWaypoint getWaypoint(String name) {
		return new LCWaypoint(name, getWaypointLocation(name), getWaypointColor(name), true, true);
    }

    public static World getWaypointWorld(String waypoint) {
        return Bukkit.getWorld(WaypointFile.getConfig().getString("WAYPOINTS." + waypoint + ".WORLD"));
    }

    public static int getWaypointX(String waypoint) {
        return WaypointFile.getConfig().getInt("WAYPOINTS." + waypoint + ".X");
    }

    public static int getWaypointY(String waypoint) {
        return WaypointFile.getConfig().getInt("WAYPOINTS." + waypoint + ".Y");
    }

    public static int getWaypointZ(String waypoint) {
        return WaypointFile.getConfig().getInt("WAYPOINTS." + waypoint + ".Z");
    }

    public static Location getWaypointLocation(String waypoint) {
        return new Location(
                getWaypointWorld(waypoint),
                getWaypointX(waypoint),
                getWaypointY(waypoint),
                getWaypointZ(waypoint)
        );
    }

    public static Set<String> getWaypoints() {
        return WaypointFile.getConfig().getConfigurationSection("WAYPOINTS").getKeys(false);
    }

    public static int getWaypointSize() {
        return WaypointFile.getConfig().getConfigurationSection("WAYPOINTS").getKeys(false).size();
    }

    public static void showWaypoints(Player player) {
        player.sendMessage(CC.translate("&bAvailables Waypoints &7(&f" + getWaypointSize() + "&7)"));
        player.sendMessage(CC.translate(""));


        if (getWaypointSize() < 1) {
            player.sendMessage(CC.translate("&bWaypoints have not been created."));
        }
        else {
            for (String waypoints : getWaypoints()) {
                player.sendMessage(CC.translate(" &3\u2746 &b" + waypoints + "Waypoint"));
            }
        }

        player.sendMessage(CC.translate(""));
    }
}
