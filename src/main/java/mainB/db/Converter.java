package mainB.db;

import mainB.annotations.myEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class Converter {
    public static <T> List<T> mapResultSetToObject(ResultSet rs, Class<T> clazz) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<T> output = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        Field[] fields = clazz.getDeclaredFields();
        int counter = 0;
        while (rs.next()) {
            counter++;
            T obj = clazz.getConstructor(null).newInstance();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                String columnName = rsmd.getColumnName(i + 1);
                Object columnValue = rs.getObject(i + 1);
                for (Field field : fields) {
                    if (field.getName().equalsIgnoreCase(columnName) && !PrimitiveHelper.isPrimitiveType(field.getType())) {
                        setProperty(obj, field, JsonConvertor.convertJsonToObject((String)columnValue,field.getType()));
                    } else if (field.getName().equalsIgnoreCase(columnName)) {
                        setProperty(obj, field, columnValue);
                        break;
                    }
                }
            }
            output.add(obj);
        }
//        System.out.println(counter);
        return output;
    }


    private static void setProperty(Object clazz, Field field, Object columnValue) {
        try {
            field.setAccessible(true);
            field.set(clazz, columnValue);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
