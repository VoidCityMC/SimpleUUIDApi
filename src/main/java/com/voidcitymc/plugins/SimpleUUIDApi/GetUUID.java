package com.voidcitymc.plugins.SimpleUUIDApi;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.json.JSONObject;



public class GetUUID {
    public static String getUUID(String player, String token) {
        Essentials essentialsMain = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        if (essentialsMain != null && essentialsMain.getOfflineUser(player) != null) {
            return essentialsMain.getUserMap().getUser(player).getConfigUUID().toString();
        }

        //if essentials is false use mojang then.
        if (player.charAt(0) != '-') {
            String mojang = mojangUUIDLookup(player);
            if (mojang != null) {
                return formatUUID(mojang);
            }
        }
        //if mojang is false then use bedrock
        System.out.println(GetJsonText.readtextFromUrl("https://xapi.us/v2/xuid/"+player.replaceFirst("-", ""), token));
        String bedrock = Integer.toHexString(Integer.parseInt(GetJsonText.readtextFromUrl("https://xapi.us/v2/xuid/"+player.replaceFirst("-", ""), token)));
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

    private static String formatUUID(String uuidUnformatted) {
        return uuidUnformatted.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
    }
}
