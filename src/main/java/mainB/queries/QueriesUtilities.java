package mainB.queries;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

import java.util.Map;


public class QueriesUtilities {
    public static  final Logger logger = LogManager.getLogger(QueriesUtilities.class);
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

    public static String preformWhereQuery(String tableName, Map<String,Object> conditions)
    {
        if(conditions==null || conditions.isEmpty()) return null;
        StringBuilder stringBuilder=new StringBuilder("select * from " + tableName + " where " );
        boolean flag=false;
        for (String key:conditions.keySet()) {
            if(!flag)
            {
                stringBuilder.append(key+" = " + conditions.get(key));
                flag=true;
            }
            else stringBuilder.append(", "+ key+" = " + conditions.get(key));
        }
        stringBuilder.append(";");
        String result = stringBuilder.toString();
        logger.debug("gets the following where query: "+result );
        return result;
    }
}
