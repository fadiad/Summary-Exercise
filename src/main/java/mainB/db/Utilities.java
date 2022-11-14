package mainB.db;

import mainB.annotations.primaryKey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Utilities {

    public static <T> String generateTable(Class<T> myClass) {
        StringBuilder stringBuilder = new StringBuilder();
        Field[] fields = myClass.getDeclaredFields();

        stringBuilder.append("CREATE TABLE IF NOT EXISTS " + myClass.getSimpleName() + "(");

        int counter = 0, length = myClass.getDeclaredFields().length;
        for (Field field : fields) {
            stringBuilder.append(
                    field.getName() + " " + getTypeOfDBTypes(field.getType().getSimpleName())
            );

            if (counter < length - 1)
                stringBuilder.append(" , ");


            counter++;
        }

        StringBuilder fieldUser = new StringBuilder(" , PRIMARY KEY(");
        boolean flag = false;
        for (Field field : fields) {

            if (handleAnnotations(field)) {
                if (!flag) {
                    flag = true;
                    fieldUser.append(field.getName());
                } else {
                    fieldUser.append("," + field.getName());
                }
            }
        }
         if(flag) stringBuilder.append(fieldUser.toString()+")");
        //    PRIMARY KEY (ID, NAME)
        stringBuilder.append(");");

        return stringBuilder.toString();
    }


    public static boolean handleAnnotations(Field field) {
        return field.isAnnotationPresent(primaryKey.class);
    }


    public static String getTypeOfDBTypes(String type) {
        switch (type) {
            case "int":
                return "int NOT NULL";
            case "String":
                return "text";
            default:
                System.out.println(type);
        }
        return "";
    }
}
