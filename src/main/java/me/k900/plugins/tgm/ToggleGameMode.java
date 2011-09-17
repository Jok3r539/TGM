package me.k900.plugins.tgm;

import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class ToggleGameMode extends JavaPlugin {

    public void onDisable() {
        System.out.println("[TGM] TGM 1.0 is now disabled!");
    }

    public void onEnable() {
        Configuration config;
        config = getConfiguration();
        config.load();
        config.save();
        TGMCommand command = new TGMCommand(getServer(), config);
        getCommand("tgm").setExecutor(command);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_TELEPORT, command, Event.Priority.Highest, this);
        System.out.println("[TGM] TGM 1.0 is now enabled!");
    }

}
