package com.voidcitymc.plugins.SimpleUUIDApi.common;

import org.json.JSONArray;
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
    public static String getJsonUsernameToUUID(String username, String uuid) {
        JSONObject mainObject = new JSONObject();
        mainObject.put("id", uuid);
        mainObject.put("name", username);
        return mainObject.toString();
    }

    public static String getJsonUUIDToUsername(String username, String uuid) {
        return (new JSONArray().put(0, new JSONObject().put("name", username))).toString();
    }

    public static String jsonFromURL(String url) {
        try {
            return readJsonFromUrl(url);
        } catch (IOException e) {
            return null;
        }
    }

    public static String readJsonFromUrl(String url) throws IOException, JSONException {
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
        } catch (IOException ignored) {
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
