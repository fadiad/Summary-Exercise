package mainB.db;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class Converter {
    public static <T> String convertObjectToSqlInsert(Class<T> clazz,T obj)
    {
        StringBuilder stringBuilder =new StringBuilder("(");
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
    public static <T> List<T> mapResultSetToObject(ResultSet rs, Class<T> clazz) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<T> output=new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        Field []fields= clazz.getDeclaredFields();
        int counter=0;
        while(rs.next())
        {
            counter++;
            T obj = clazz.getConstructor(null).newInstance();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                String columnName = rsmd.getColumnName(i + 1);
                Object columnValue = rs.getObject(i + 1);
                for (Field field : fields) {
                        if (field.getName().equalsIgnoreCase(columnName)) {
                            setProperty(obj, field.getName(), columnValue);
                            break;
                        }
                }
            }
            output.add(obj);
        }
        System.out.println(counter);
        return output;
    }
    private static void setProperty(Object clazz, String fieldName, Object columnValue) {
        try {
            // get all fields of the class (including public/protected/private)
            Field field = clazz.getClass().getDeclaredField(fieldName);
            // this is necessary in case the field visibility is set at private
            field.setAccessible(true);
            field.set(clazz, columnValue);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
