package mainB.db;

import com.google.gson.Gson;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConvertor {

    public final static Gson gson = new Gson();

    public static <T> String convertStringToJason(T obj, Class<T> tClass) throws IllegalAccessException {
        Field[] declaredFields = tClass.getDeclaredFields();
        int counter=declaredFields.length-1;
        StringBuilder stringBuilder=new StringBuilder();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (PrimitiveHelper.isPrimitiveType(field.getType())) {
                stringBuilder.append( format(field.get(obj)));

            } else {
                stringBuilder.append(format(gson.toJson(field.get(obj), field.getType())));
            }
           if(counter--!=0)
           {
               stringBuilder.append(", ");
           }
        }
        return stringBuilder.toString();
    }
   public static  <T>  Map<String,Object> fromObjectToMap(T obj, Class<T> tClass)
   {
       HashMap<String,Object> result=new HashMap<>();
       for (Field field:tClass.getDeclaredFields()) {
           field.setAccessible(true);
           try {
               Object value=field.get(obj);
               if(!PrimitiveHelper.isPrimitiveType(field.getType()))
               {
                   value=gson.toJson(value,field.getType());
               }
               result.put(field.getName(),format(value));
           } catch (IllegalAccessException e) {
              e.printStackTrace();
           }
       }
       return result;

   }
    public static <T> T convertJsonToObject(String json, Class<T> tClass) {
       return  gson.fromJson(json, tClass);
    }

    public static Object format(Object o) {
        if (o instanceof String)
            return "\'" + o + "\'";

        return o;
    }

}
