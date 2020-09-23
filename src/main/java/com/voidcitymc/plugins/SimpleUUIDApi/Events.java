package com.voidcitymc.plugins.SimpleUUIDApi;


import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class Events implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(LoginEvent event) {
        Storage db = new Storage();
        String uuid = event.getConnection().getUniqueId().toString();
        String username = event.getConnection().getName();
        if (!db.getUUID(username).equals(uuid)) {
            db.storeUUID(username, uuid);
        }
    }
}
