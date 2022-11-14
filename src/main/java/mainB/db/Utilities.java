package mainB.db;

import java.lang.reflect.Field;

public class Utilities {

    public static <T> String generateTable(Class<T> myClass) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE " + myClass.getSimpleName() + "(");

        int counter = 0, length = myClass.getDeclaredFields().length;
        for (Field field : myClass.getDeclaredFields()) {
            stringBuilder.append(
                    field.getName() + " " + getTypeOfDBTypes(field.getType().getSimpleName())
            );

            if (counter < length - 1)
                stringBuilder.append(" , ");

            counter++;
        }
        stringBuilder.append(");");

        return stringBuilder.toString();
    }

    public static String getTypeOfDBTypes(String type) {
        switch (type) {
            case "int":
                return "int";
            case "String":
                return "text";
            default:
                System.out.println(type);
        }
        return "";
    }
}
