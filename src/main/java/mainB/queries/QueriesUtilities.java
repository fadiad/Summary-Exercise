package mainB.queries;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

import java.util.Map;


public class QueriesUtilities {
    public static final Logger logger = LogManager.getLogger(QueriesUtilities.class);

    public static String getAllObjects(String tableName) {
        return "select * from " + tableName;
    }

    public static <T> String insertObject(String tableName, T obj, Class<T> clazz) {
        StringBuilder stringBuilder = new StringBuilder(String.format("INSERT INTO  %s (", tableName));
        Field[] declaredFields = clazz.getDeclaredFields();
        Object[] values = new Object[declaredFields.length];
        int counter = 0;
        for (Field field : declaredFields) {
            stringBuilder.append(field.getName());
            field.setAccessible(true);
            try {
                values[counter] = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (counter != values.length - 1) {
                stringBuilder.append(",");
            }
            counter++;
        }
        counter = 0;
        stringBuilder.append(") VALUES (");
        for (Object value : values) {
            if (value instanceof String) {
                stringBuilder.append("\"");
                stringBuilder.append(value);
                stringBuilder.append("\"");
            } else stringBuilder.append(value);
            if (counter != values.length - 1) {
                stringBuilder.append(",");
            }
            counter++;
        }
        stringBuilder.append(");");
        return stringBuilder.toString();
    }

    public static String updateQuery(String tableName, Map<String, Object> conditions, MySQLMethods method, Map<String, Object> updateValue) {
        if (method != MySQLMethods.UPDATE) {
            logger.error("this function treats only with updates .");
            return null;
        }

        String prefix = requestFactory(tableName, method) + " set ";

        boolean flag = true;

        for (String key : updateValue.keySet()) {
            if (flag) {
                prefix += key + " =" + format(updateValue.get(key));
                flag = false;
            } else
                prefix += ", " + key + " = " + updateValue.get(key);
        }
        return addWhereConditions(conditions, prefix);
    }


    public static String selectAndDeleteQueries(String tableName, Map<String, Object> conditions, MySQLMethods method) {

        if (method == MySQLMethods.UPDATE) {
            logger.error("this function does not support an update method .");
            return null;
        }

        String prefix = requestFactory(tableName, method);

        return addWhereConditions(conditions, prefix);
    }

    private static String addWhereConditions(Map<String, Object> conditions, String str) {
        if (conditions == null || conditions.isEmpty()) {
            logger.error("conditions is empty , you must enter a condition .");
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder(str + " where ");
        boolean flag = false;
        for (String key : conditions.keySet()) {
            if (!flag) {
                stringBuilder.append(key + " = " + conditions.get(key));
                flag = true;
            } else stringBuilder.append(", " + key + " = " + conditions.get(key));
        }
        stringBuilder.append(";");
        String result = stringBuilder.toString();
        logger.debug("gets the following where query: " + result);
        return result;
    }

    private static String requestFactory(String tableName, MySQLMethods method) {
        String str = "";
        switch (method) {
            case SELECT:
                str = "select * from " + tableName;
                break;
            case DELETE:
                str = "delete from " + tableName;
                break;
            case UPDATE:
                str = "update " + tableName;
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return str;
    }


    public static Object format(Object o) {
        if (o instanceof String)
            return "\'" + o + "\'";

        return o;
    }


}
