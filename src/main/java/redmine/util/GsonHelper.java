package redmine.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {
    public static Gson getGson(){
       Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTimeS.class, new LocalDateTimeS())
               .registerTypeAdapter(LocalDateTimeD.class, new LocalDateTimeD())
               .create();
       return gson;
    }
}
