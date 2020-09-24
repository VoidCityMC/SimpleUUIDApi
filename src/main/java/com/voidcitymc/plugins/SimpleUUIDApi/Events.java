package com.voidcitymc.plugins.SimpleUUIDApi;


import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class Events implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(LoginEvent event) {
        Storage db = new Storage();
        String uuid = event.getConnection().getUniqueId().toString().replaceAll("-", "");
        String username = event.getConnection().getName();
        String uuidFromDB = db.getUUID(username);
        if (uuidFromDB != null) {
            if (!uuidFromDB.equals(uuid)) {
                String realUUID = GetUUID.apiUUIDLookUpNoDash(username);
                if (realUUID != null) {
                    //update internal db
                    db.storeUUID(username, realUUID);
                }
                if (realUUID != null && !realUUID.equals(uuid)) {
                    //player is using a non mojang uuid
                    event.setCancelled(true);
                }
                if (realUUID == null) {
                    //player's account doesn't exist
                    event.setCancelled(true);
                }
            }
        } else {
            String realUUID = GetUUID.apiUUIDLookUpNoDash(username);
            if (realUUID == null) {
                //players account doesn't exist
                event.setCancelled(true);
            } else {
                if (realUUID.equals(uuid)) {
                    db.storeUUID(username, uuid);
                } else {
                    //player is using non mojang uuid
                    event.setCancelled(true);
                }
            }
        }
    }
}
