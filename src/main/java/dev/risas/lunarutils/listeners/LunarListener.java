package dev.risas.lunarutils.listeners;

import com.lunarclient.bukkitapi.LunarClientAPI;
import com.lunarclient.bukkitapi.object.LCCooldown;
import com.lunarclient.bukkitapi.object.TitleType;
import dev.risas.lunarutils.LunarUtils;
import dev.risas.lunarutils.files.ConfigFile;
import dev.risas.lunarutils.manager.menu.waypoint.buttons.WaypointButton;
import dev.risas.lunarutils.manager.type.WaypointType;
import dev.risas.lunarutils.utilities.CC;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LunarListener implements Listener {

    private final LunarUtils plugin = LunarUtils.getInstance();
    private final ConfigFile configFile = ConfigFile.getConfig();

    public LunarListener(LunarUtils plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        this.plugin.getWaypointManager().getWaypoints().forEach(waypoint ->
                this.plugin.getWaypointManager().createJoinWaypoint(player, waypoint));

        if (ConfigFile.getConfig().getBoolean("LUNAR-TITLE.ENABLE")) {
            String title = this.configFile.getString("LUNAR-TITLE.TITLE").replace("<player>", player.getName());
            String subtitle = this.configFile.getString("LUNAR-TITLE.SUBTITLE").replace("<player>", player.getName());
            int delay = this.configFile.getInt("LUNAR-TITLE.DELAY");

            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                PlaceholderAPI.setPlaceholders(player, title);
                PlaceholderAPI.setPlaceholders(player, subtitle);
            }

            LunarClientAPI.getInstance().sendTitle(player, TitleType.TITLE, CC.translate(title), Duration.ofSeconds(delay));
            LunarClientAPI.getInstance().sendTitle(player, TitleType.SUBTITLE, CC.translate(subtitle), Duration.ofSeconds(delay));
        }
    }

    @EventHandler
    private void onWaypointClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null || event.getInventory() != event.getClickedInventory()) return;

        if (event.getInventory().getTitle().equals(CC.translate("&b&lWaypoint Color"))) {
            event.setCancelled(true);

            ItemStack is = event.getCurrentItem();

            if (is != null && is.getType().equals(Material.AIR)) return;

            if (WaypointButton.getClose().isSimilar(is)) {
                player.closeInventory();
                return;
            }

            for (WaypointType waypointType : WaypointType.values()) {
                if (waypointType.getItem().isSimilar(is)) {
                    String waypointName = this.plugin.getWaypointManager().getName();

                    this.plugin.getWaypointManager().setColor(waypointType.getColor());
                    this.plugin.getWaypointManager().createWaypointFile(waypointName, player);
                    this.plugin.getWaypointManager().createWaypointOnline(waypointName);
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
                    player.closeInventory();
                    player.sendMessage(CC.translate("&a" + waypointName + "Waypoint has been created."));
                }
            }
        }
    }

    @EventHandler
    private void onPearl(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof EnderPearl) {
            if (ConfigFile.getConfig().getBoolean("LUNAR-COOLDOWNS.ENDERPEARL.ENABLE")) {
                Player player = (Player) event.getEntity().getShooter();

                int delay = ConfigFile.getConfig().getInt("LUNAR-COOLDOWNS.ENDERPEARL.DELAY");
                Material material = Material.valueOf(ConfigFile.getConfig().getString("LUNAR-COOLDOWNS.ENDERPEARL.ICON"));

                LunarClientAPI.getInstance().sendCooldown(player, new LCCooldown("Enderpearl", delay, TimeUnit.SECONDS, material));
            }
        }
    }

    @EventHandler
    private void onGrapple(PlayerItemConsumeEvent event) {
        if (event.getItem().getType().equals(Material.GOLDEN_APPLE)) {
            if (ConfigFile.getConfig().getBoolean("LUNAR-COOLDOWNS.GRAPPLE.ENABLE")) {
                Player player = event.getPlayer();

                int delay = ConfigFile.getConfig().getInt("LUNAR-COOLDOWNS.GRAPPLE.DELAY");
                Material material = Material.valueOf(ConfigFile.getConfig().getString("LUNAR-COOLDOWNS.GRAPPLE.ICON"));

                LunarClientAPI.getInstance().sendCooldown(player, new LCCooldown("Grapple", delay, TimeUnit.SECONDS, material));
            }
        }
    }
}
