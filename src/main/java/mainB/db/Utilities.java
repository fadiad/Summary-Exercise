package mainB.db;

import mainB.annotations.FixedSize;
import mainB.annotations.autoIncrementation;
import mainB.annotations.primaryKey;
import mainB.annotations.unique;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Utilities {
    public static final Logger logger = LogManager.getLogger(Utilities.class);

    public static <T> String generateTable(Class<T> myClass) {
        StringBuilder stringBuilder = new StringBuilder();
        Field[] fields = myClass.getDeclaredFields();

        stringBuilder.append("CREATE TABLE IF NOT EXISTS " + myClass.getSimpleName() + "(");

        int counter = 0, length = myClass.getDeclaredFields().length;
        for (Field field : fields) {
            if (field.getType() == String.class && isAnnotated(field, FixedSize.class)) {
                stringBuilder.append(field.getName() + " varchar(" + field.getAnnotation(FixedSize.class).size() + ") ");
            } else {
                stringBuilder.append(
                        field.getName() + " " + getTypeOfDBTypes(field.getType().getSimpleName())
                );
            }

            if (isAnnotated(field, unique.class))
                stringBuilder.append(" unique ");
            if (isAnnotated(field, autoIncrementation.class))
                stringBuilder.append(" AUTO_INCREMENT ");
            if (counter < length - 1)
                stringBuilder.append(" , ");
            counter++;
        }

        primaryKeyAnnotation(stringBuilder, fields);
        //    PRIMARY KEY (ID, NAME)
        stringBuilder.append(");");

        return stringBuilder.toString();
    }


    private static void primaryKeyAnnotation(StringBuilder stringBuilder, Field[] fields) {
        StringBuilder fieldUser = new StringBuilder(" , PRIMARY KEY(");
        boolean flag = false;
        for (Field field : fields) {

            if (isAnnotated(field, primaryKey.class)) {
                if (!flag) {
                    flag = true;
                    fieldUser.append(field.getName());
                } else {
                    fieldUser.append("," + field.getName());
                }
            }
        }
        if (flag) stringBuilder.append(fieldUser.toString() + ")");
    }


    public static boolean isAnnotated(Field field, Class<? extends Annotation> clazz) {
        return field.isAnnotationPresent(clazz);
    }

    public static String getTypeOfDBTypes(String type) {
        switch (type) {
            case "int":
            case "boolean":
            case "float":
            case "double":
                return type + " not null";
            case "char":
                return "varchar(1) not null";
            case "byte":
            case "short":
                return "tinyint not null";
            case "long":
                return "bigint not null";
            case "String":
                return "text";
            case "Integer":
                return "int";
            case "Boolean":
            case "Float":
            case "Double":
                return type.toLowerCase();
            case "Long":
                return "bigint";
            case "Character":
                return "varchar(1)";
            case "Byte":
            case "Short":
                return "tinyint";
            default:
                return "text";
        }
    }
}
