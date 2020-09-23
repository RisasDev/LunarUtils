package dev.risas.lunarutils.listeners;

import dev.risas.lunarutils.LunarUtils;
import dev.risas.lunarutils.manager.WaypointManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LunarListener implements Listener {

    public LunarListener(LunarUtils plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (String waypoint : WaypointManager.getWaypoints()) {
            WaypointManager.createPlayerWaypointLunar(player, waypoint);
        }
    }
}
