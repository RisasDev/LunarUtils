package dev.risas.lunarutils.utilities.commands;

import dev.risas.lunarutils.LunarUtils;

public abstract class BaseCommand {

    public BaseCommand() {
        LunarUtils.getInstance().getCommandFramework().registerCommands(this, null);
    }

    public abstract void onCommand(CommandArgs command);
}
