package dev.risas.lunarutils.manager;

import dev.risas.lunarutils.utilities.CC;
import net.mineaus.lunar.api.LunarClientAPI;
import net.mineaus.lunar.api.type.StaffModule;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaffModulesManager {

    public static List<Player> list = new ArrayList<>();

    public static boolean inPlayerList(Player player) {
        return list.contains(player);
    }

    public static void addPlayerList(Player player) {
        list.remove(player);
    }

    public static void removePlayerList(Player player) {
        list.remove(player);
    }

    public static void setStaffModules(Player player) throws IOException {

        if (LunarClientAPI.INSTANCE().isAuthenticated(player)) {

            if (inPlayerList(player)) {
                removePlayerList(player);
                disableStaffModules(player);
                player.sendMessage(CC.translate("&bYour StaffModules has been &cdisabled&b."));
            }
            else {
                addPlayerList(player);
                enableStaffModules(player);
                player.sendMessage(CC.translate("&bYour StaffModules has been &aenabled&b."));
            }
        }
        else {
            player.sendMessage(CC.translate("&cYou're not using LunarClient."));
        }
    }

    public static void enableStaffModules(Player player) throws IOException {
        LunarClientAPI.INSTANCE().toggleStaffModule(player, StaffModule.NAME_TAGS, true);
        LunarClientAPI.INSTANCE().toggleStaffModule(player, StaffModule.BUNNY_HOP, true);
        LunarClientAPI.INSTANCE().toggleStaffModule(player, StaffModule.XRAY, true);
    }

    public static void disableStaffModules(Player player) throws IOException {
        LunarClientAPI.INSTANCE().toggleStaffModule(player, StaffModule.NAME_TAGS, false);
        LunarClientAPI.INSTANCE().toggleStaffModule(player, StaffModule.BUNNY_HOP, false);
        LunarClientAPI.INSTANCE().toggleStaffModule(player, StaffModule.XRAY, false);
    }
}
