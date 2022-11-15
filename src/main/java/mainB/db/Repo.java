package mainB.db;

import mainB.queries.MySQLMethods;
import mainB.queries.QueriesUtilities;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class Repo<T> {
    private final String DB_URL = "jdbc:mysql://localhost:3306/";
    private final String USER = "root";
    private final String PASS = "";
    private final String TableName;

    private final Class<T> myClass;

    private static Connection conn;
    private static Statement stmt;

    private static final String DBNAME = "ORMDB";

    public Repo(Class<T> myClass) {
        this.myClass = myClass;
        this.TableName = myClass.getSimpleName();
        start();
    }

    private void start() {
        makeConnection("");
        makeDB();
        makeTable();
    }


    private void makeConnection(String name) {
        try {
            conn = DriverManager.getConnection(DB_URL + name, USER, PASS);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void makeDB() {
        try {
            String makeTableQuery = "CREATE DATABASE IF NOT EXISTS " + DBNAME;
            stmt.executeUpdate(makeTableQuery);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertInto(T obj) {
        String s = QueriesUtilities.insertObject(TableName, obj, myClass);
        try {
            Utilities.logger.debug(s);
            return stmt.executeUpdate(s);
        } catch (SQLException e) {
            Utilities.logger.error("sql error caz : " + e.getErrorCode());
            if (e.getErrorCode() == 1062)
                System.out.println("You are trying to insert a user with an existed id");
            else if (e.getErrorCode() == 1064)
                System.out.println(e.getMessage());
        }
        return -1;
    }


    public void insertMany(List<T> list) {
        for (T obj : list) {
            insertInto(obj);
        }
    }

    public void replaceItem(T oldObject, T newObject) {

    }

    public int deleteObject(T obj) {
        Map<String, Object> con = JsonConvertor.fromObjectToMap(obj,myClass);
        String deleteQuery= QueriesUtilities.selectAndDeleteQueries(TableName, con, MySQLMethods.DELETE);
        return applyUpdate(deleteQuery);

    }


    public int update(Map<String, Object> conditions, Map<String, Object> updateValues) {
        String updateQuery = QueriesUtilities.updateQuery(TableName, conditions, MySQLMethods.UPDATE, updateValues);
        System.out.println(updateQuery);
        return applyUpdate(updateQuery);
    }

    private int applyUpdate(String query) {
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


    private List<T> apply(String query) {
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

    public int deleteObjectsByConditions(Map<String, Object> conditions) {
        String whereQuery = QueriesUtilities.selectAndDeleteQueries(TableName, conditions, MySQLMethods.DELETE);
        System.out.println(whereQuery);
        return applyUpdate(whereQuery);
    }

    public List<T> getObjectsByConditions(Map<String, Object> conditions) {
        String whereQuery = QueriesUtilities.selectAndDeleteQueries(TableName, conditions, MySQLMethods.SELECT);
        return apply(whereQuery);
    }


    public List<T> getAllObjects() {
        String getAllObjects = QueriesUtilities.getAllObjects(TableName);
        return apply(getAllObjects);
    }

    public Optional<T> getObjectById(int id) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("id", id);
        List<T> item = apply(QueriesUtilities.selectAndDeleteQueries(TableName, condition, MySQLMethods.SELECT));
        if (item.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(item.get(0));
        }
    }


    private void makeTable() {
        try {
            conn = DriverManager.getConnection(DB_URL + DBNAME, USER, PASS);
            stmt = conn.createStatement();
            String makeTableQuery = Utilities.generateTable(myClass);
            System.out.println(makeTableQuery);
            stmt.executeUpdate(makeTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String deleteTable() {
        return "DROP TABLE IF EXISTS " + TableName + ";";
    }

}
