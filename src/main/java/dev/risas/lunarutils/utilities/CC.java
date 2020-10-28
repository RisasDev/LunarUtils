package dev.risas.lunarutils.utilities;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class CC {

    public static String translate(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static List<String> translate(List<String> lore) {
        return lore.stream().map(CC::translate).collect(Collectors.toList());
    }
}
