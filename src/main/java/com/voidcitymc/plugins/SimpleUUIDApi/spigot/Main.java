package com.voidcitymc.plugins.SimpleUUIDApi.spigot;

import com.voidcitymc.plugins.SimpleUUIDApi.Manager;
import com.voidcitymc.plugins.SimpleUUIDApi.common.ServerMode;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private Manager manager = new Manager();

    //enabled
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(), this);
        manager.start(ServerMode.Spigot);
    }

    //disabled
    @Override
    public void onDisable() {
        manager.stop();
    }

}
