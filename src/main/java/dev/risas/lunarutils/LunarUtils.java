package dev.risas.lunarutils;

import dev.risas.lunarutils.commands.LunarCommand;
import dev.risas.lunarutils.listeners.LunarListener;
import dev.risas.lunarutils.manager.CheckManager;
import dev.risas.lunarutils.manager.WaypointManager;
import dev.risas.lunarutils.utilities.commands.CommandFramework;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class LunarUtils extends JavaPlugin {

    @Getter
    private static LunarUtils instance;
    private CommandFramework commandFramework;
    private CheckManager checkManager;
    private WaypointManager waypointManager;

    @Override
    public void onEnable() {
        instance = this;

        this.loadManagers();
        this.loadCommands();
        this.loadListeners();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void loadManagers() {
        this.commandFramework = new CommandFramework(this);
        this.checkManager = new CheckManager();
        this.waypointManager = new WaypointManager();
    }

    private void loadCommands() {
        new LunarCommand();
    }

    private void loadListeners() {
        new LunarListener(this);
    }
}
