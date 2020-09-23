package dev.risas.lunarutils.manager;

import dev.risas.lunarutils.utilities.CC;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.lunarclient.bukkitapi.LunarClientAPI;

public class StaffModulesManager {

    public static List<String> players = new ArrayList<>();
    
    static {
    	players = new ArrayList<>();
    }

    public static void setStaffModules(Player player) {

    	if (LunarClientAPI.getInstance().isRunningLunarClient(player)) {

            if (players.contains(player.getName())) {
                players.remove(player.getName());
                
                LunarClientAPI.getInstance().disableAllStaffModules(player);
                player.sendMessage(CC.translate("&bYour StaffModules has been &cdisabled&b."));
            }
            else {
                players.add(player.getName());
                
                LunarClientAPI.getInstance().giveAllStaffModules(player);
                player.sendMessage(CC.translate("&bYour StaffModules has been &aenabled&b."));
            }
        }
        else {
            player.sendMessage(CC.translate("&cYou're not using LunarClient."));
        }
    }
}
