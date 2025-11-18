package com.apitesting.datingapp.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = TestConfig.class.getClassLoader()
                .getResourceAsStream("test-config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find test-config.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("api.base.url",
                "https://hr-challenge.dev.tapyou.com/api/test");
    }

    public static int getTimeout() {
        return Integer.parseInt(properties.getProperty("api.timeout.ms", "5000"));
    }

    public static boolean isLoggingEnabled() {
        return Boolean.parseBoolean(properties.getProperty("logging.enabled", "true"));
    }
}