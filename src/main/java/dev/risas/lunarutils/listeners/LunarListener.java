package dev.risas.lunarutils.listeners;

import dev.risas.lunarutils.LunarUtils;
import dev.risas.lunarutils.manager.StaffModulesManager;
import dev.risas.lunarutils.manager.WaypointManager;
import net.mineaus.lunar.api.LunarClientAPI;
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

        if (LunarClientAPI.INSTANCE().isAuthenticated(player)) {

            for (String waypoint : WaypointManager.getWaypoints()) {
                WaypointManager.createPlayerWaypointLunar(player, waypoint);
            }
        }
    }

    @EventHandler
    private void onQuit(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (LunarClientAPI.INSTANCE().isAuthenticated(player)) {

            if (StaffModulesManager.inPlayerList(player)) {
                StaffModulesManager.removePlayerList(player);
            }
        }
    }
}
