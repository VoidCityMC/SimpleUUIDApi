package com.voidcitymc.plugins.SimpleUUIDApi.common;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.voidcitymc.plugins.SimpleUUIDApi.SimpleUUIDApi;
import com.voidcitymc.plugins.SimpleUUIDApi.bungeecord.Main;

import java.io.IOException;
import java.io.OutputStream;

public class Server implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        String requestParamValue = null;
        if("GET".equals(httpExchange.getRequestMethod())) {
            requestParamValue = handleGetRequest(httpExchange);
        }
        handleResponse(httpExchange, requestParamValue);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        if (httpExchange.getRequestURI().toString().contains("/users/profiles/minecraft/")) {
            return httpExchange.getRequestURI().toString().split("/users/profiles/minecraft/")[1];
        } else if (httpExchange.getRequestURI().toString().contains("/users/profiles/") && httpExchange.getRequestURI().toString().contains("/names")) {
            return httpExchange.getRequestURI().toString().split("/users/profiles/")[1];
        }
        return "null";
        //will return something, if there is content after /test/ ex: http://localhost:8001/test/hi
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();
        String htmlResponse = "null";

        // encode HTML content

        if (httpExchange.getRequestURI().toString().contains("/users/profiles/minecraft/")) {
            htmlResponse = GetJsonText.getJsonUsernameToUUID(requestParamValue, GetUUID.getUUID(requestParamValue));
        } else if (httpExchange.getRequestURI().toString().contains("/users/profiles/") && httpExchange.getRequestURI().toString().contains("/names")) {
            htmlResponse = GetJsonText.usernameHistory(requestParamValue.split("/names")[0]);
        }

        // this line is a must
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

}
