package com.voidcitymc.plugins.SimpleUUIDApi;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.json.JSONObject;


public class GetUUID {
    public static String getUUID(String player) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
        if (offlinePlayer.hasPlayedBefore()) {
            return offlinePlayer.getUniqueId().toString();
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
