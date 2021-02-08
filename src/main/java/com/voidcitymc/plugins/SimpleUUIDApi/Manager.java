package com.voidcitymc.plugins.SimpleUUIDApi;

import com.sun.net.httpserver.HttpServer;
import com.voidcitymc.plugins.SimpleUUIDApi.common.Config;
import com.voidcitymc.plugins.SimpleUUIDApi.common.Server;
import com.voidcitymc.plugins.SimpleUUIDApi.common.ServerMode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Manager {
    private HttpServer server;

    public static ServerMode serverMode;

    public static String MojangUsernameToUUIDUrl;
    public static String MojangUUIDToUsername;
    public static String XApiXUIDToGamertag;
    public static String XApiGamertagToXUID;
    public static String XApiToken;
    public static String ElectroidUUIDApi;
    public static boolean BedrockEditionSupport;
    public static String BedrockPlayerPrefix;
    public static boolean ServerOnlineMode;
    public static int ServerPort;
    public static String ServerHostname;



    public void start(ServerMode serverMode) {
        initVariables();
        this.serverMode = serverMode;

        try {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            server = HttpServer.create(new InetSocketAddress(ServerHostname, ServerPort), 0);
            server.createContext("/", new Server());
            server.setExecutor(threadPoolExecutor);
            server.start();
            System.out.println("Server started on port "+ServerPort);
        } catch (IOException e) {
            System.out.println("Could not start server");
            System.out.println(e.getCause().toString());
        }

    }

    public void stop() {
        System.out.println("Stopping server on port "+server);
        server.stop(0);
        System.out.println("Server stopped");
    }

    private void initVariables() {
        Config.setupConfig();
        MojangUsernameToUUIDUrl = Config.getConfigProperty("MojangUsernameToUUIDUrl");
        MojangUUIDToUsername = Config.getConfigProperty("MojangUUIDToUsername");
        XApiXUIDToGamertag = Config.getConfigProperty("XApiXUIDToGamertag");
        XApiGamertagToXUID = Config.getConfigProperty("XApiGamertagToXUID");
        XApiToken = Config.getConfigProperty("XApiToken");
        ElectroidUUIDApi = Config.getConfigProperty("ElectroidUUIDApi");
        BedrockEditionSupport = Boolean.parseBoolean(Config.getConfigProperty("BedrockEditionSupport"));
        BedrockPlayerPrefix = Config.getConfigProperty("BedrockPlayerPrefix");
        ServerOnlineMode = Boolean.parseBoolean(Config.getConfigProperty("ServerOnlineMode"));
        ServerPort = Integer.parseInt(Config.getConfigProperty("ServerPort"));
        ServerHostname = Config.getConfigProperty("ServerHostname");
    }

}
