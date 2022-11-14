package mainB.queries;

import java.lang.reflect.Field;
import java.util.List;

public class QueriesUtilities {
    public static String getAllObjects(String tableName)
    {
        return "select * from "+ tableName;
    }
    public static <T> String insertObject(String tableName,T obj,Class<T> clazz)
    {
        StringBuilder stringBuilder =new StringBuilder(String.format("INSERT INTO  %s (",tableName));
        Field[] declaredFields = clazz.getDeclaredFields();
        Object [] values=new Object[declaredFields.length];
        int counter=0;
        for (Field field: declaredFields) {
            stringBuilder.append(field.getName());
            field.setAccessible(true);
            try {
                values[counter]=field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(counter!= values.length-1)
            {
                stringBuilder.append(",");
            }
            counter++;
        }
        counter=0;
        stringBuilder.append(") VALUES (");
        for (Object value: values) {
            if(value instanceof String)
            {
                stringBuilder.append("\"");
                stringBuilder.append(value);
                stringBuilder.append("\"");
            }
            else stringBuilder.append(value);
            if(counter!= values.length-1)
            {
                stringBuilder.append(",");
            }
            counter++;
        }
        stringBuilder.append(");");
        return stringBuilder.toString();
    }

}
