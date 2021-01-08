package dev.risas.lunarutils.manager.menu.waypoint.buttons;

import dev.risas.lunarutils.utilities.item.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WaypointButton {

    public static ItemStack getColor(String color, int data) {
        return new ItemCreator(Material.WOOL)
                .setDurability(data)
                .setName(color + " Color")
                .setLore("", "&7Click to select this color.")
                .get();
    }

    public static ItemStack getClose() {
        return new ItemCreator(Material.REDSTONE)
                .setName("&c&lCLOSE")
                .get();
    }
}
