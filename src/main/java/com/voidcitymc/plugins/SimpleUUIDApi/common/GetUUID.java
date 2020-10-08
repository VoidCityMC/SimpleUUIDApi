package com.voidcitymc.plugins.SimpleUUIDApi.common;

import com.voidcitymc.plugins.SimpleUUIDApi.SimpleUUIDApi;
import org.json.JSONObject;



public class GetUUID {
    public static String getUUID(String player) {

        Storage db = new Storage();
        String cachedUUID = db.getUUIDNoDash(player);
        if (cachedUUID != null) {
            return cachedUUID;
        }

        //use api if internal cache is null
        String apiUUID = apiUUIDLookUpNoDash(player);
        if (apiUUID != null) {
            db.storeUUID(player, apiUUID);
            return apiUUID;
        }

        return null;
    }

    //skip internal cache
    public static String apiUUIDLookUpNoDash(String player) {
        if (player.charAt(0) != '-') {
            String electroid = electroidUUIDLookup(player);
            if (electroid != null) {
                return electroid;
            }

            String mojang = mojangUUIDLookup(player);
            if (mojang != null) {
                return mojang;
            }
        }
        //if mojang is false then use bedrock
        return bedrockUUIDLookup(player);

    }

    private static String mojangUUIDLookup(String player) {
        String textFromUrl = GetJsonText.jsonFromURL("https://api.mojang.com/users/profiles/minecraft/"+player);
        if (textFromUrl != null) {
            JSONObject jsonObject = new JSONObject(textFromUrl);
            return (String) jsonObject.get("id");
        }
        return null;
    }

    //https://github.com/Electroid/mojang-api
    private static String electroidUUIDLookup(String player) {
        String textFromUrl = GetJsonText.jsonFromURL("https://api.ashcon.app/mojang/v2/user/"+player);
        if (textFromUrl != null) {
            JSONObject jsonObject = new JSONObject(textFromUrl);
            return ((String) jsonObject.get("uuid")).replaceAll("-","");
        }
        return null;
    }

    private static String bedrockUUIDLookup(String player) {
        String xuid = GetJsonText.readtextFromUrl("https://xapi.us/v2/xuid/"+player.replaceFirst("-", ""), SimpleUUIDApi.token);
        if (xuid != null) {
            String bedrock = "0000000000000000000" + Long.toHexString(Long.parseLong(xuid));
            return bedrock;
        }
        return null;
    }
    private static String formatUUID(String uuidUnformatted) {
        return uuidUnformatted.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
    }
}
