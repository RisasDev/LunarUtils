package dev.risas.lunarutils.manager;

import dev.risas.lunarutils.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InventoryManager {

    public static Inventory getWaypoint() {
        Inventory inv = Bukkit.createInventory(null, 18, CC.translate("&b&lWaypoint Color"));

        inv.setItem(0, getColor("&f&lWhite", (short)0));
        inv.setItem(1, getColor("&5&lMagenta", (short)2));
        inv.setItem(2, getColor("&b&lLight Blue", (short)3));
        inv.setItem(3, getColor("&e&lYellow", (short)4));
        inv.setItem(4, getColor("&a&lGreen", (short)5));
        inv.setItem(5, getColor("&7&lLight Gray", (short)8));
        inv.setItem(6, getColor("&1&lBlue", (short)11));
        inv.setItem(7, getColor("&4&lRed", (short)14));
        inv.setItem(8, getColor("&0&lBlack", (short)15));
        inv.setItem(13, getClose());

        return inv;
    }

    public static ItemStack getColor(String color, short data) {
        ItemStack item = new ItemStack(Material.WOOL, 1, data);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(CC.translate(color + " Color"));
        meta.setLore(CC.translate(Arrays.asList("", "&7&oClick to select this color.")));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getClose() {
        ItemStack item = new ItemStack(Material.REDSTONE, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(CC.translate("&cClose"));
        item.setItemMeta(meta);
        return item;
    }
}
