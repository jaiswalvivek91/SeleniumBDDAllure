package utility;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.nio.file.Paths;
import java.nio.file.Paths;
import java.io.FileReader;


public class ConfigReader {

    public static String getAppUrl() {

        String appConfigPath = Paths.get("src", "test", "resources", "config", "application_config.json").toString();
        return readJson(appConfigPath, "appUrl").toString();

    }

    public static String[] getCredentials(String role) {
        try {
            JSONParser parser = new JSONParser();


            String userCredPath = Paths.get("src", "test", "resources", "config", "user_credentials.json").toString();
            JSONObject json = (JSONObject) parser.parse(new FileReader(userCredPath));


            JSONArray users = (JSONArray) json.get("users");
            for (Object o : users) {
                JSONObject user = (JSONObject) o;
                if (user.get("role").equals(role)) {
                    return new String[]{user.get("username").toString(), user.get("password").toString()};
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object readJson(String path, String key) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(new FileReader(path));
            return json.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
