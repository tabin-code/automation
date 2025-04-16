package helperTools;

// Ensure the org.json library is added to the project dependencies

import org.openqa.selenium.json.Json;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class saveUserData {
    /**
     * Method to save user JSON details to a properties file
     *
     * @param userJson The JSON string containing user details
     * @param fileName The name of the properties file to save the details
     * @throws IOException If an error occurs while writing to the file
     */
    public static void saveUserDetailsToPropertiesFile(String userJson, String fileName) throws IOException {
        // Use Selenium's Json API to parse JSON string into a Map
        Json jsonParser = new Json();
        Map<String, Object> userMap = jsonParser.toType(userJson, Map.class);

        // Create a Properties object to store the key-value pairs
        Properties properties = new Properties();

        // Add user details into the properties object (keys and values)
        properties.setProperty("id", (String) userMap.get("_id"));
        properties.setProperty("first_name", (String) userMap.get("first_name"));
        properties.setProperty("last_name", (String) userMap.get("last_name"));
        properties.setProperty("email", (String) userMap.get("email"));
        properties.setProperty("google_id", (String) userMap.get("google_id"));
        properties.setProperty("image_url", (String) userMap.get("image_url"));
        properties.setProperty("admin", String.valueOf(userMap.get("admin")));
        properties.setProperty("paid", String.valueOf(userMap.get("paid")));
        properties.setProperty("createdAt", (String) userMap.get("createdAt"));
        properties.setProperty("updatedAt", (String) userMap.get("updatedAt"));

        // Save the properties to a file
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            properties.store(fileOut, "User Details");
        }
    }
}
