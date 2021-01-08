package dev.risas.lunarutils.manager;

import dev.risas.lunarutils.LunarUtils;
import dev.risas.lunarutils.files.WaypointFile;
import dev.risas.lunarutils.utilities.CC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.lunarclient.bukkitapi.LunarClientAPI;
import com.lunarclient.bukkitapi.object.LCWaypoint;

import java.util.Set;

@Getter
@Setter
public class WaypointManager {

    private String name;
    private int color;

    public WaypointManager() {
        this.name = null;
        this.color = 0;
    }

    public void createWaypointFile(String waypoint, Player player) {
        WaypointFile.getConfig().set("WAYPOINTS." + waypoint + ".WORLD", player.getWorld().getName());
        WaypointFile.getConfig().set("WAYPOINTS." + waypoint + ".X", player.getLocation().getBlockX());
        WaypointFile.getConfig().set("WAYPOINTS." + waypoint + ".Y", player.getLocation().getBlockY());
        WaypointFile.getConfig().set("WAYPOINTS." + waypoint + ".Z", player.getLocation().getBlockZ());
        WaypointFile.getConfig().set("WAYPOINTS." + waypoint + ".COLOR", color);
        WaypointFile.getConfig().saveAll();
    }

    public void deleteWaypointFile(String waypoint) {
        WaypointFile.getConfig().set("WAYPOINTS." + waypoint, null);
        WaypointFile.getConfig().saveAll();
    }

    public void createWaypointOnline(String waypoint) {
        Bukkit.getOnlinePlayers().forEach(online -> {
            if (LunarClientAPI.getInstance().isRunningLunarClient(online)) {
                LunarClientAPI.getInstance().sendWaypoint(online, this.getWaypoint(waypoint));
            }
        });
    }

    public void deleteWaypointOnline(String waypoint) {
        Bukkit.getOnlinePlayers().forEach(online -> {
            if (LunarClientAPI.getInstance().isRunningLunarClient(online)) {
                LunarClientAPI.getInstance().removeWaypoint(online, this.getWaypoint(waypoint));
            }
        });
    }

    public void createJoinWaypoint(Player player, String waypoint) {
    	new BukkitRunnable() {
			@Override
			public void run() {
                if (LunarClientAPI.getInstance().isRunningLunarClient(player)) {
                    LunarClientAPI.getInstance().sendWaypoint(player, getWaypoint(waypoint));
                }
			}
		}.runTaskLaterAsynchronously(LunarUtils.getInstance(), 1L);
    }

    public void availablesWaypoints(Player player) {
        player.sendMessage(CC.translate("&bAvailables Waypoints &7(&f" + this.getWaypoints().size() + "&7)"));
        player.sendMessage(CC.translate(""));
        if (this.getWaypoints().size() < 1) {
            player.sendMessage(CC.translate("&bWaypoints have not been created."));
        }
        else {
            this.getWaypoints().forEach(waypoint ->
                    player.sendMessage(CC.translate(" &3\u2746 &b" + waypoint + "Waypoint")));
        }
        player.sendMessage(CC.translate(""));
    }

    public int getWaypointColor(String waypoint) {
        return WaypointFile.getConfig().getInt("WAYPOINTS." + waypoint + ".COLOR");
    }
    
    public LCWaypoint getWaypoint(String name) {
		return new LCWaypoint(name, getWaypointLocation(name), getWaypointColor(name), true, true);
    }

    public World getWaypointWorld(String waypoint) {
        return Bukkit.getWorld(WaypointFile.getConfig().getString("WAYPOINTS." + waypoint + ".WORLD"));
    }

    public int getWaypointX(String waypoint) {
        return WaypointFile.getConfig().getInt("WAYPOINTS." + waypoint + ".X");
    }

    public int getWaypointY(String waypoint) {
        return WaypointFile.getConfig().getInt("WAYPOINTS." + waypoint + ".Y");
    }

    public int getWaypointZ(String waypoint) {
        return WaypointFile.getConfig().getInt("WAYPOINTS." + waypoint + ".Z");
    }

    public Location getWaypointLocation(String waypoint) {
        return new Location(
                this.getWaypointWorld(waypoint),
                this.getWaypointX(waypoint),
                this.getWaypointY(waypoint),
                this.getWaypointZ(waypoint)
        );
    }

    public Set<String> getWaypoints() {
        return WaypointFile.getConfig().getConfigurationSection("WAYPOINTS").getKeys(false);
    }
}
