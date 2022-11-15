package mainB.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Applies {
   static int applyUpdate(Statement stmt, String query) {
        if (query == null) {
            Utilities.logger.error("query was null !");
            return 0;
        }

        try {
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            Utilities.logger.error("Wrong sql Syntax.\n" + e.getMessage());
        }
        return 0;
    }


     static <T> List<T> apply(Statement stmt,String query,Class<T> myClass) {
        if (query == null) {
            Utilities.logger.error("query was null !");
            return new ArrayList<>();
        }

        try {

            return Converter.mapResultSetToObject(stmt.executeQuery(query), myClass);
        } catch (SQLException e) {
            Utilities.logger.error("Wrong sql Syntax.\n" + e.getMessage());
        } catch (NoSuchMethodException e) {
            Utilities.logger.error(String.format("You must have a default constructor in %s. \n", myClass.getSimpleName()) + e.getMessage());
        } catch (InvocationTargetException e) {
            Utilities.logger.error(String.format("An error has occurred when trying to run the default constructor in  %s class. \n", myClass.getSimpleName()) + e.getMessage());
        } catch (InstantiationException e) {
            Utilities.logger.error(String.format("An error has occurred when trying to create an instance of %s class. \n", myClass.getSimpleName()) + e.getMessage());
        } catch (IllegalAccessException e) {
            Utilities.logger.error(String.format("The default constructor is private %s class. \n", myClass.getSimpleName()) + e.getMessage());
        }
        return new ArrayList<>();
    }
}
