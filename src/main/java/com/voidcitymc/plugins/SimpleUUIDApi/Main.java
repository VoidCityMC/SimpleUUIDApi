package com.voidcitymc.plugins.SimpleUUIDApi;

import com.sun.net.httpserver.HttpServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main extends JavaPlugin {
    private HttpServer server;

    //enabled
    @Override
    public void onEnable() {
        try {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8001), 0);
            server.createContext("/", new  Server());
            server.setExecutor(threadPoolExecutor);
            server.start();
            System.out.println("Server started on port 8001");
        } catch (IOException e) {
            System.out.println("Could not start server");
            System.out.println(e.getCause().toString());
        }
    }

    //disabled
    @Override
    public void onDisable() {
        System.out.println("Stopping server on port 8001");
        server.stop(0);
        System.out.println("Server stopped");
    }

}
