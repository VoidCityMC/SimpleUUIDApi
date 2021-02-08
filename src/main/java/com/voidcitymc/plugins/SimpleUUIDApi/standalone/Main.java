package com.voidcitymc.plugins.SimpleUUIDApi.standalone;

import com.voidcitymc.plugins.SimpleUUIDApi.Manager;
import com.voidcitymc.plugins.SimpleUUIDApi.common.ServerMode;

public class Main  {

    private static Manager manager = new Manager();

    public static void main(String[] args) {
        manager.start(ServerMode.Standalone);
    }
}
