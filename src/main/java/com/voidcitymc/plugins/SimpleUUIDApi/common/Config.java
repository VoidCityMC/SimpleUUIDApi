package com.voidcitymc.plugins.SimpleUUIDApi.common;

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

                inputStream = Config.class.getClassLoader().getResourceAsStream(configFileName);
                InputStream defaultConfig = Config.class.getResourceAsStream("/" + configFileName);

                Properties configFilePropertiesFromDisk = new Properties();
                configFilePropertiesFromDisk.load(defaultConfig);
                configFilePropertiesFromDisk.store(new FileOutputStream(configFileName), null);
                configFileProperties = configFilePropertiesFromDisk;
            } catch (IOException e) {
                return;
            }
        } else {
            inputStream = Config.class.getClassLoader().getResourceAsStream(configFileName);
        }
    }

    public static String getConfigProperty(String property) {
        if (configFileProperties != null) {
            return configFileProperties.getProperty(property);
        } else {
            return null;
        }
    }
}
