package mainB.db;

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

    public void insertInto(T obj) {
        String s = QueriesUtilities.insertObject(TableName, obj, myClass);
        try {
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<T> apply(String query) {
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

    public List<T> getObjectsByConditions(Map<String, Object> conditions) {
        String whereQuery = QueriesUtilities.preformWhereQuery(TableName, conditions);
        return apply(whereQuery);
    }

    public List<T> getAllObjects() {
        String getAllObjects = QueriesUtilities.getAllObjects(TableName);
        return apply(getAllObjects);
    }

    public Optional<T> getObjectById(int id) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("id", id);
        List<T> item = apply(QueriesUtilities.preformWhereQuery(TableName, condition));
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
