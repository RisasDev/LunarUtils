package dev.risas.lunarutils;

import dev.risas.lunarutils.commands.FileCommand;
import dev.risas.lunarutils.commands.LunarCommand;
import dev.risas.lunarutils.listeners.LunarListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class LunarUtils extends JavaPlugin {

    @Getter
    private static LunarUtils instance;

    public LunarUtils() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.registerCommands();
        this.registerListeners();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void registerCommands() {
        new FileCommand();
        new LunarCommand();
    }

    private void registerListeners() {
        new LunarListener(this);
    }
}
