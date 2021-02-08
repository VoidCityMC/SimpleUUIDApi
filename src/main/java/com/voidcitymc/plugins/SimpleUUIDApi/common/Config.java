package com.voidcitymc.plugins.SimpleUUIDApi.common;

import com.voidcitymc.plugins.SimpleUUIDApi.Manager;

import java.io.*;
import java.util.Properties;

public class Config {
    private static InputStream inputStream;
    private static String configFileName = "SimpleUUIDApiConfig.properties";
    private static Properties configFileProperties;

    public static boolean configExists() {
        return (new File(configFileName)).exists();
    }

    public static void setupConfig() {
        if (!configExists()) {
            try {
                (new File(configFileName)).createNewFile();

                inputStream = new FileInputStream(configFileName);
                InputStream defaultConfig = Config.class.getResourceAsStream("/" + configFileName);

                Properties configFilePropertiesFromDisk = new Properties();
                configFilePropertiesFromDisk.load(defaultConfig);
                configFilePropertiesFromDisk.store(new FileOutputStream(configFileName), null);
            } catch (IOException e) {
                return;
            }
        } else {
            try {
                inputStream = new FileInputStream(configFileName);
            } catch (FileNotFoundException e) {
            }
            configFileProperties = new Properties();
            try {
                configFileProperties.load(inputStream);
            } catch (IOException e) {
            }
        }
    }

    public static String getConfigProperty(String property) {
        return configFileProperties.getProperty(property);
    }
}
