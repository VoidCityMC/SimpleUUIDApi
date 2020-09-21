package com.voidcitymc.plugins.SimpleUUIDApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetJsonText {
    public static String getJsonUsernameToUUID(String username, String uuid) {
        JSONObject mainObject = new JSONObject();
        mainObject.put("id", GetUUID.getUUID(username));
        mainObject.put("name", username);
        return mainObject.toString();
    }

    public static String UsernameHistory(String uuid) {
        try {
            return readJsonFromUrl("https://api.mojang.com/user/profiles/"+uuid+"/names");
        } catch (IOException e) {
            return "invalid uuid or webserver error";
        }
    }

    public static String jsonFromURL(String url) {
        try {
            return readJsonFromUrl(url);
        } catch (IOException e) {
            return null;
        }
    }

    private static String readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return readAll(rd);
        } finally {
            is.close();
        }
    }
    private static String readAll(BufferedReader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
