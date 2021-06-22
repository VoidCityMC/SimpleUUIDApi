package com.voidcitymc.plugins.SimpleUUIDApi.common;

import com.voidcitymc.plugins.SimpleUUIDApi.Manager;

import java.io.*;
import java.util.Properties;

public class Config {
    private static InputStream inputStream;
    private static String configFileName = "SimpleUUIDApiConfig.properties";
    private static Properties configFileProperties;
    private static String configFilePath = "";
    private static String configFileFullPath = "";

    private static boolean configExists() {
        return (new File(configFileFullPath)).exists();
    }

    public static void setupConfig() {
        if (Manager.serverMode == ServerMode.Bungeecord || Manager.serverMode == ServerMode.Spigot) {
            configFilePath = "."+File.separator+"plugins"+File.separator+"SimpleUUIDApi"+File.separator;
        }

        configFileFullPath = configFilePath+configFileName;
        InputStream defaultConfig = Config.class.getResourceAsStream("/" + configFileName);
        Properties defaultConfigProperties = new Properties();

        try {
            defaultConfigProperties.load(defaultConfig);
        } catch (IOException exception) {
            System.out.println("SimpleUUIDApi is unable to load internal config file - Critical Error!");
            return;
        }

        if (!configExists()) {
            try {
                (new File(configFileFullPath)).createNewFile();
                defaultConfigProperties.store(new FileOutputStream(configFileFullPath), null);
            } catch (IOException e) {
                System.out.println("SimpleUUIDApi is unable to create a config file - Critical Error!");
            }
            configFileProperties = defaultConfigProperties;
            return;
        } else {
            try {
                inputStream = new FileInputStream(configFileFullPath);
            } catch (FileNotFoundException ignored) {
            }
            Properties localConfigFileProperties = new Properties();
            try {
                localConfigFileProperties.load(inputStream);
            } catch (IOException exception) {
                System.out.println("SimpleUUIDApi is unable to load the config file - Critical Error!");
                configFileProperties = defaultConfigProperties;
                return;
            }

            if (!localConfigFileProperties.getProperty("BuildTimestamp").equals(defaultConfigProperties.getProperty("BuildTimestamp"))) {
                configFileProperties = updateConfig(localConfigFileProperties, defaultConfigProperties);
                try {
                    configFileProperties.store(new FileOutputStream(configFileFullPath), null);
                } catch (IOException ignored) {
                }
            } else {
                configFileProperties = localConfigFileProperties;
            }
        }
    }

    private static Properties updateConfig(Properties properties, Properties defaultConfigProperties) {
        Properties newConfigProperties = defaultConfigProperties;
        for (String currentKey: defaultConfigProperties.stringPropertyNames()) {
            String currentEntry = properties.getProperty(currentKey);
            if (currentEntry != null && !currentKey.equals("BuildTimestamp")) {
                newConfigProperties.setProperty(currentKey, currentEntry);
            }
        }
        return newConfigProperties;
    }

    public static String getConfigProperty(String property) {
        return configFileProperties.getProperty(property);
    }
}