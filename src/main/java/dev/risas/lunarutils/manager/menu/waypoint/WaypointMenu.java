package dev.risas.lunarutils.manager.menu.waypoint;

import dev.risas.lunarutils.manager.menu.waypoint.buttons.WaypointButton;
import dev.risas.lunarutils.manager.type.WaypointType;
import dev.risas.lunarutils.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WaypointMenu {

    public static void getWaypoint(Player player) {
        Inventory inv = Bukkit.createInventory(null, 18, CC.translate("&b&lWaypoint Color"));
        for (WaypointType waypointType : WaypointType.values()) {
            inv.addItem(waypointType.getItem());
        }
        inv.setItem(13, WaypointButton.getClose());
        player.openInventory(inv);
    }
}
