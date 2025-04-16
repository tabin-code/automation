package helperTools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class propertiesLoader {
    private static Properties apiProperties = new Properties();
    private static Properties oauthGoogleProperties = new Properties();
    private static Properties tabinProperties = new Properties();

    static {
        try {
            // Load from classpath
            try (InputStream apiInput = propertiesLoader.class.getClassLoader().getResourceAsStream("api-config.properties");
                 InputStream oauthInput = propertiesLoader.class.getClassLoader().getResourceAsStream("oauth-config.properties");
                 InputStream tabinInput = propertiesLoader.class.getClassLoader().getResourceAsStream("price-analytics-config.properties")) {

                if (apiInput == null) {
                    throw new IllegalStateException("Missing api-config.properties in classpath.");
                }
                if (oauthInput == null) {
                    throw new IllegalStateException("Missing oauth-config.properties in classpath.");
                }
                if (tabinInput == null) {
                    throw new IllegalStateException("Missing price-analytics-config.properties in classpath.");
                }

                // Load properties
                apiProperties.load(apiInput);
                oauthGoogleProperties.load(oauthInput);
                tabinProperties.load(tabinInput);

                // Resolve placeholders in all loaded properties
                resolvePlaceholders(apiProperties);
                resolvePlaceholders(oauthGoogleProperties);
                resolvePlaceholders(tabinProperties);
            }
        } catch (Exception e) {
            System.err.println("Failed to load configuration files: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error initializing static properties loader.", e);
        }
    }

    /**
     * Resolves placeholders (e.g., ${api.base.url}) in property values.
     */
    private static void resolvePlaceholders(Properties properties) {
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            if (value != null && value.contains("${")) {
                String resolvedValue = resolveValue(value, properties);
                properties.setProperty(key, resolvedValue);
            }
        }
    }

    /**
     * Recursively resolves placeholders in a single property value.
     */
    private static String resolveValue(String value, Properties properties) {
        while (value.contains("${")) {
            int startIndex = value.indexOf("${") + 2; // Start of placeholder
            int endIndex = value.indexOf("}", startIndex); // End of placeholder
            if (endIndex == -1) {
                throw new IllegalStateException("Invalid placeholder syntax in value: " + value);
            }
            String placeholder = value.substring(startIndex, endIndex);
            String placeholderValue = properties.getProperty(placeholder);
            if (placeholderValue == null) {
                throw new IllegalStateException("Missing property for placeholder: " + placeholder);
            }
            value = value.replace("${" + placeholder + "}", placeholderValue);
        }
        return value;
    }

    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(fileName)) {
            properties.load(fis);
            resolvePlaceholders(properties); // Resolve placeholders for dynamically loaded properties
        } catch (IOException e) {
            throw new RuntimeException("Failed to load property file: " + fileName, e);
        }
        return properties;
    }

    public static String getApiProperty(String key) {
        String value = apiProperties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Missing or empty property: " + key);
        }
        return value;
    }

    public static String getOauthGoogleProperty(String key) {
        String value = oauthGoogleProperties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Missing or empty property: " + key);
        }
        return value;
    }

    public static String getTabinProperty(String key) {
        String value = tabinProperties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Missing or empty property: " + key);
        }
        return value;
    }
}