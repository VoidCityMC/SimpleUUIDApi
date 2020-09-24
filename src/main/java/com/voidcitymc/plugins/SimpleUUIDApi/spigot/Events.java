package com.voidcitymc.plugins.SimpleUUIDApi.spigot;


import com.voidcitymc.plugins.SimpleUUIDApi.common.GetUUID;
import com.voidcitymc.plugins.SimpleUUIDApi.common.Storage;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class Events implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        Storage db = new Storage();
        String uuid = event.getUniqueId().toString().replaceAll("-", "");
        String username = event.getName();
        String uuidFromDB = db.getUUIDNoDash(username);
        if (uuidFromDB != null) {
            if (!uuidFromDB.equals(uuid)) {
                String realUUID = GetUUID.apiUUIDLookUpNoDash(username);
                if (realUUID != null) {
                    //update internal db
                    db.storeUUID(username, realUUID);
                }
                if (realUUID != null && !realUUID.equals(uuid)) {
                    //player is using a non mojang uuid
                    event.setKickMessage("You are using a non mojang/xbox uuid");
                }
                if (realUUID == null) {
                    //player's account doesn't exist
                    event.setKickMessage("Your minecraft account doesn't exist");
                }
            }
        } else {
            String realUUID = GetUUID.apiUUIDLookUpNoDash(username);
            if (realUUID == null) {
                //players account doesn't exist
                event.setKickMessage("Your minecraft account doesn't exist");
            } else {
                if (realUUID.equals(uuid)) {
                    db.storeUUID(username, uuid);
                } else {
                    //player is using non mojang uuid
                    event.setKickMessage("You are using a non mojang/xbox uuid");
                }
            }
        }
    }
}
