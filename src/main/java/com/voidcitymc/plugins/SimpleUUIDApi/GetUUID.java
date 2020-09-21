package com.voidcitymc.plugins.SimpleUUIDApi;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.json.JSONObject;



public class GetUUID {
    public static String getUUID(String player) {
        Essentials essentialsMain = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        if (essentialsMain != null && essentialsMain.getOfflineUser(player).getConfigUUID() != null) {
            return essentialsMain.getUserMap().getUser(player).getConfigUUID().toString();
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
