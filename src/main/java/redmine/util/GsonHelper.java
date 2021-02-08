package redmine.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

public class GsonHelper {
    public static Gson getGson(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeS())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeD())
                .create();
        return gson;
    }
}
