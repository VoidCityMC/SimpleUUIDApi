package com.voidcitymc.plugins.SimpleUUIDApi.bungeecord;

import com.voidcitymc.plugins.SimpleUUIDApi.Manager;
import com.voidcitymc.plugins.SimpleUUIDApi.common.ServerMode;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

    private Manager manager = new Manager();

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new Events());
        manager.start(ServerMode.Bungeecord);
    }

    @Override
    public void onDisable() {
        manager.stop();
    }

}
