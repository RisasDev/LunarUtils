package dev.risas.lunarutils.files;

import dev.risas.lunarutils.LunarUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class WaypointFile extends YamlConfiguration {

    private static WaypointFile config;
    private Plugin plugin;
    private File configFile;

    public static WaypointFile getConfig() {
        if (WaypointFile.config == null) {
            WaypointFile.config = new WaypointFile();
        }
        return WaypointFile.config;
    }

    private Plugin main() {
        return LunarUtils.getInstance();
    }

    public WaypointFile() {
        this.plugin = this.main();
        this.configFile = new File(this.plugin.getDataFolder(), "waypoints.yml");
        this.saveDefault();
        this.reload();
    }

    public void reload() {
        try {
            super.load(this.configFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            super.save(this.configFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDefault() {
        this.plugin.saveResource("waypoints.yml", false);
    }
}