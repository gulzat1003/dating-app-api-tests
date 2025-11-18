package com.apitesting.datingapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestDataReader {
    private static final Properties testConfig = new Properties();
    private static final Properties testCases = new Properties();

    static {
        loadProperties("test-config.properties", testConfig);
        loadProperties("test-cases.properties", testCases);
    }

    private static void loadProperties(String fileName, Properties properties) {
        try (InputStream input = TestDataReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.out.println("Properties file not found: " + fileName + ", using defaults");
                return;
            }
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Failed to load properties file: " + fileName + ", using defaults");
        }
    }

    public static String getProperty(String key) {
        return testConfig.getProperty(key);
    }

    public static String getTestCaseProperty(String key) {
        return testCases.getProperty(key);
    }

    public static int getIntProperty(String key) {
        try {
            return Integer.parseInt(testConfig.getProperty(key));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Helper methods for specific test cases
    public static String getTestCaseName(String testCaseId) {
        return testCases.getProperty(testCaseId + ".name");
    }

    public static String getTestCaseDescription(String testCaseId) {
        return testCases.getProperty(testCaseId + ".description");
    }

    public static String getTestCaseParameter(String testCaseId, String param) {
        return testCases.getProperty(testCaseId + ".parameters." + param);
    }

    public static int getExpectedStatus(String testCaseId) {
        return Integer.parseInt(testCases.getProperty(testCaseId + ".expected.status", "200"));
    }

    public static int getActualStatus(String testCaseId) {
        return Integer.parseInt(testCases.getProperty(testCaseId + ".actual.status", "200"));
    }

    public static String getBugId(String testCaseId) {
        return testCases.getProperty(testCaseId + ".bug");
    }
}
