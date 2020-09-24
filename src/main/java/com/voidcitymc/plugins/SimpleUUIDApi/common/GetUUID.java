package com.voidcitymc.plugins.SimpleUUIDApi.common;

import com.voidcitymc.plugins.SimpleUUIDApi.SimpleUUIDApi;
import com.voidcitymc.plugins.SimpleUUIDApi.bungeecord.Main;
import org.json.JSONObject;



public class GetUUID {
    public static String getUUID(String player, String token) {

        Storage db = new Storage();
        String cachedUUID = db.getUUID(player);
        if (cachedUUID != null) {
            return cachedUUID;
        }

        //if essentials is false use mojang then.
        if (player.charAt(0) != '-') {
            String mojang = mojangUUIDLookup(player);
            if (mojang != null) {
                db.storeUUID(player, mojang);
                return formatUUID(mojang);
            } else {
                return null;
            }
        }
        //if mojang is false then use bedrock
        String xuid = GetJsonText.readtextFromUrl("https://xapi.us/v2/xuid/"+player.replaceFirst("-", ""), token);
        if (xuid != null) {
            String bedrock = "0000000000000000000" + Long.toHexString(Long.parseLong(xuid));
            db.storeUUID(player, bedrock);
            return formatUUID(bedrock);
        }
        return null;

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

    public static String apiUUIDLookUpNoDash(String player) {
        if (player.charAt(0) != '-') {
            return mojangUUIDLookup(player);
        }
        //if mojang is false then use bedrock
        String xuid = GetJsonText.readtextFromUrl("https://xapi.us/v2/xuid/"+player.replaceFirst("-", ""), SimpleUUIDApi.token);
        if (xuid != null) {
            return "0000000000000000000" + Long.toHexString(Long.parseLong(xuid));
        }
        return null;
    }

    private static String formatUUID(String uuidUnformatted) {
        return uuidUnformatted.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
    }
}
