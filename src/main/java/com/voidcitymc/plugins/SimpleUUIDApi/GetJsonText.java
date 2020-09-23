package com.voidcitymc.plugins.SimpleUUIDApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetJsonText {
    public static String getJsonUsernameToUUID(String username, String uuid, String token) {
        JSONObject mainObject = new JSONObject();
        mainObject.put("id", GetUUID.getUUID(username, token));
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

    public static String readtextFromUrl(String url, String token) {
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            conn.setRequestProperty("X-Auth", token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();

            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                return readAll(rd);
            } finally {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
