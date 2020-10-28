package dev.risas.lunarutils.listeners;

import dev.risas.lunarutils.LunarUtils;
import dev.risas.lunarutils.manager.WaypointManager;
import dev.risas.lunarutils.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

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

    @EventHandler
    private void onWaypointClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null || event.getInventory() != event.getClickedInventory()) {
            return;
        }

        if (event.getInventory().getTitle().equals(CC.translate("&b&lWaypoint Color"))) {

            event.setCancelled(true);

            ItemStack is = event.getCurrentItem();

            if (is != null && is.getType() == Material.AIR) {
                return;
            }

            switch (event.getSlot()) {
                case 0:
                    WaypointManager.setWaypointColor(-1);
                    break;
                case 1:
                    WaypointManager.setWaypointColor(-65281);
                    break;
                case 2:
                    WaypointManager.setWaypointColor(-16711681);
                    break;
                case 3:
                    WaypointManager.setWaypointColor(-256);
                    break;
                case 4:
                    WaypointManager.setWaypointColor(-16711936);
                    break;
                case 5:
                    WaypointManager.setWaypointColor(-3355444);
                    break;
                case 6:
                    WaypointManager.setWaypointColor(-16776961);
                    break;
                case 7:
                    WaypointManager.setWaypointColor(-65536);
                    break;
                case 8:
                    WaypointManager.setWaypointColor(-16777216);
                    break;
                case 13:
                    player.closeInventory();
                    return;
            }
            WaypointManager.createWaypointFile(WaypointManager.getWaypointName(), player);
            WaypointManager.createPlayersWaypointLunar(WaypointManager.getWaypointName());
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
            player.sendMessage(CC.translate("&b" + WaypointManager.getWaypointName() + "Waypoint has been created."));
            player.closeInventory();
        }
    }
}
