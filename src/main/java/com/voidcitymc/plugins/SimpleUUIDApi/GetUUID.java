package com.voidcitymc.plugins.SimpleUUIDApi;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.json.JSONObject;



public class GetUUID {
    public static String getUUID(String player, String token) {
        Essentials essentialsMain = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        System.out.println(essentialsMain != null && essentialsMain.getOfflineUser(player).getConfigUUID() != null);
        System.out.println("rfrg3hj57h4grfewdrhtjy5k");
        if (essentialsMain != null && essentialsMain.getOfflineUser(player).getConfigUUID() != null) {
            return essentialsMain.getUserMap().getUser(player).getConfigUUID().toString();
        }

        System.out.println("mojang");
        //if essentials is false use mojang then.
        System.out.println(player.charAt(0));
        System.out.println(player.charAt(0) != '-');
        if (player.charAt(0) != '-') {
            String mojang = mojangUUIDLookup(player);
            if (mojang != null) {
                return mojang;
            }
        }
        //if mojang is false then use bedrock
        System.out.println("bed");
        String bedrock = GetJsonText.readtextFromUrl("https://xapi.us/v2/xuid/"+player.replaceFirst("-", ""), token);
        System.out.println(bedrock);
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
