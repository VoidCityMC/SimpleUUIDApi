package com.voidcitymc.plugins.SimpleUUIDApi;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.json.JSONObject;



public class GetUUID {
    public static String getUUID(String player, String token) {
        Essentials essentialsMain = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        if (essentialsMain != null && essentialsMain.getOfflineUser(player).getConfigUUID() != null) {
            return essentialsMain.getUserMap().getUser(player).getConfigUUID().toString();
        }

        System.out.println("mojang");
        //if essentials is false use mojang then.
        if (player.charAt(0) != '-') {
            String mojang = mojangUUIDLookup(player);
            if (mojang != null) {
                return mojang;
            }
        }
        //if mojang is false then use bedrock
        String bedrock = GetJsonText.readtextFromUrl("https://xapi.us/v2/xuid/"+player.replaceFirst("-", ""), token);
        return bedrock;

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
