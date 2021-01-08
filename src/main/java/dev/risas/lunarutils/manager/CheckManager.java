package dev.risas.lunarutils.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.lunarclient.bukkitapi.LunarClientAPI;

import dev.risas.lunarutils.utilities.CC;

public class CheckManager {
	
	public void antiCheat(Player player, Player target) {
		if (LunarClientAPI.getInstance().isRunningLunarClient(target)) {
			player.sendMessage(CC.translate("&b" + target.getName() + " is using LunarClient with AntiCheat " 
				+ (LunarClientAPI.getInstance().isRunningAntiCheat(target) ? "&aON" : "&cOFF") + "&b."));
		}
		else {
			player.sendMessage(CC.translate("&c" + target.getName() + " is not using LunarClient."));
		}
	}
	
	public void lunarPlayers(Player player) {
		player.sendMessage(CC.translate("&3&m--------------------------------"));
		player.sendMessage(CC.translate("&b&lLunar Client Online"));
		player.sendMessage(CC.translate(""));

		Bukkit.getOnlinePlayers().forEach(online -> {
			if (LunarClientAPI.getInstance().isRunningLunarClient(online)) {
				player.sendMessage(CC.translate(" &3\u2746 &b" + online.getName()));
			}
		});

		player.sendMessage(CC.translate(""));
		player.sendMessage(CC.translate("&3&m--------------------------------"));
	}
}
