package com.voidcitymc.plugins.SimpleUUIDApi;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.json.JSONObject;


public class GetUUID {
    public static String getUUID(String player) {
        String playerUUID = null;
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();
            User user = api.getUserManager().getUser(player);
            if (user != null) {
                playerUUID = user.getUniqueId().toString();
            }
        }
        //if luckperms is false use mojang then.
        return mojangUUIDLookup(player);
    }


    //very very bad
    private static String mojangUUIDLookup(String player) {
        String textFromUrl = GetJsonText.jsonFromURL("https://api.mojang.com/users/profiles/minecraft/"+player);
        if (textFromUrl != null) {
            JSONObject jsonObject = new JSONObject(textFromUrl);
            return (String) jsonObject.get("id");
        }
        return null;
    }
}
