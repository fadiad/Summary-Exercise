package mainB.db;

import mainB.queries.QueriesUtilities;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.List;

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
        this.TableName=myClass.getSimpleName();
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
    public void insertInto(T obj)
    {
        String s = "insert into " + TableName + Converter.convertObjectToSqlInsert(myClass, obj);
        try {
            System.out.println(s);
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<T> getAllObjects()
    {
        String getAllObjects= QueriesUtilities.getAllObjects(TableName);
        makeQuery(getAllObjects);
        try {
            return Converter.mapResultSetToObject(stmt.executeQuery(getAllObjects), myClass);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public void makeQuery(String query) {
        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
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

}
