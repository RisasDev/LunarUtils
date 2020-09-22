package dev.risas.lunarutils.utilities;

import org.bukkit.ChatColor;

public class CC {

    public static String translate(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
