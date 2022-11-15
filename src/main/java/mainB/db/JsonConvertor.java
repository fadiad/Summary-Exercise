package mainB.db;

import com.google.gson.Gson;

public class JsonConvertor {

    public final static Gson gson = new Gson();

    public static <T> String convertStringToJason(T obj, Class<T> tClass) {
        return gson.toJson(obj, tClass);
    }

    public static <T> T convertJsonToObject(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

}
