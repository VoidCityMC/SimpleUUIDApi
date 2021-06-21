package com.voidcitymc.plugins.SimpleUUIDApi.common;

import com.voidcitymc.plugins.SimpleUUIDApi.Manager;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Config {
    private static InputStream inputStream;
    private static String configFileName = "SimpleUUIDApiConfig.properties";
    private static Properties configFileProperties;
    private static String configFilePath = "";

    private static boolean configExists() {
        return (new File(configFileName)).exists();
    }

    public static void setupConfig() {
        if (Manager.serverMode == ServerMode.Bungeecord || Manager.serverMode == ServerMode.Spigot) {
            configFilePath = "."+File.separator+"plugins"+File.separator+"SimpleUUIDApi"+File.separator;
        }

        String configFileFullPath = configFilePath+configFileName;
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
            configFileProperties = updateConfig(localConfigFileProperties);
            return;
        }
    }

    private static Properties updateConfig(Properties properties) {
        InputStream defaultConfig = Config.class.getResourceAsStream("/" + configFileName);
        Properties defaultConfigProperties = new Properties();

        try {
            defaultConfigProperties.load(defaultConfig);
        } catch (IOException exception) {

        }

        Set<Map.Entry<Object, Object>> defaultConfigPropertiesEntrySet = defaultConfigProperties.entrySet();
        Iterator<Map.Entry<Object, Object>> defaultConfigPropertiesIterator = defaultConfigPropertiesEntrySet.iterator();

        while (defaultConfigPropertiesIterator.hasNext()) {
            Map.Entry<Object, Object> currentEntry = defaultConfigPropertiesIterator.next();
            properties.putIfAbsent(currentEntry.getKey(), currentEntry.getValue());
        }
        return properties;
    }

    public static String getConfigProperty(String property) {
        return configFileProperties.getProperty(property);
    }
}