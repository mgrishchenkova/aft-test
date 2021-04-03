package redmine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {
    private static Properties property = new Properties();

    static {
        try {
            property.load(new FileInputStream("src/test/resources/local.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStringProperties(String key) {
        return property.getProperty(key);
    }
    public static Integer getIntegerProperty(String key){
        return Integer.parseInt(getStringProperties(key));
    }

    public static Boolean getBooleanProperty(String key){
        return Boolean.parseBoolean(getStringProperties(key));
    }
}
