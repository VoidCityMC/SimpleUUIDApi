package com.voidcitymc.plugins.SimpleUUIDApi.common;

import com.voidcitymc.plugins.SimpleUUIDApi.Manager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;


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

    public static String getUsername(String uuid) {
        Storage db = new Storage();
        String username = db.getUsername(uuid);

        if (username == null) {
            String mojangUsernameHistory = mojangUsernameHistory(uuid);
            if (!mojangUsernameHistory.isEmpty()) {
                username = ((JSONObject)(new JSONArray(mojangUsernameHistory).get(0))).get("name").toString();
                (new Storage()).storeUUID(username, uuid);
                return username;
            } else {
                if (isBedrockUUID(uuid) && Manager.BedrockEditionSupport) {
                    System.out.println("is bed uuid");
                    System.out.println(xuidFromUUID(uuid));
                    username = Manager.BedrockPlayerPrefix+bedrockGamertagLookup(xuidFromUUID(uuid));
                    (new Storage()).storeUUID(username, uuid);
                    return username;
                } else {
                    //not a valid uuid
                    return null;
                }
            }
        }

        return username;
    }

    //skip internal cache
    public static String apiUUIDLookUpNoDash(String player) {
        if (player.charAt(0) != Manager.BedrockPlayerPrefix.toCharArray()[0]) {
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
        if (Manager.BedrockEditionSupport) {
            return bedrockUUIDLookup(player);
        }
        return null;

    }

    private static String mojangUUIDLookup(String player) {
        String textFromUrl = GetJsonText.jsonFromURL(Manager.MojangUsernameToUUIDUrl.replace("{}", player));
        if (textFromUrl != null) {
            JSONObject jsonObject = new JSONObject(textFromUrl);
            return (String) jsonObject.get("id");
        }
        return null;
    }

    //https://github.com/Electroid/mojang-api
    private static String electroidUUIDLookup(String player) {
        String textFromUrl = GetJsonText.jsonFromURL(Manager.ElectroidUUIDApi.replace("{}", player));
        if (textFromUrl != null) {
            JSONObject jsonObject = new JSONObject(textFromUrl);
            return ((String) jsonObject.get("uuid")).replaceAll("-","");
        }
        return null;
    }

    private static String bedrockUUIDLookup(String player) {
        String xuid = GetJsonText.readtextFromUrl(Manager.XApiGamertagToXUID.replace("{}", player.replaceFirst(Manager.BedrockPlayerPrefix, "")), Manager.XApiToken);
        if (xuid != null) {
            String bedrock = "0000000000000000000" + Long.toHexString(Long.parseLong(xuid));
            return bedrock;
        }
        return null;
    }

    private static boolean isBedrockUUID(String uuid) {
        return uuid.startsWith("00000000");
    }

    private static long xuidFromUUID(String uuid) {
        return Long.parseLong(uuid.replaceAll("-", "").substring(19),16);
    }


    public static String mojangUsernameHistory(String uuid) {
        try {
            return GetJsonText.readJsonFromUrl(Manager.MojangUUIDToUsername.replace("{}", uuid));
        } catch (IOException e) {
            return "invalid uuid or webserver error";
        }
    }

    private static String bedrockGamertagLookup(Long XUID) {
        return GetJsonText.readtextFromUrl(Manager.XApiXUIDToGamertag.replace("{}", XUID.toString()), Manager.XApiToken);
    }

    private static String formatUUID(String uuidUnformatted) {
        return uuidUnformatted.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
    }
}
