package mainB.db;

import java.lang.reflect.Field;
import java.sql.*;

public class Repo {
    private final String DB_URL = "jdbc:mysql://localhost:3306/";
    private final String USER = "root";
    private final String PASS = "";
    private static Connection conn;
    private static Statement stmt;

    public Repo(String DBName) {
//        makeConnection("");
//        makeDB();
//        makeTable("stam");
    }

    public void makeConnection(String name) {
        try {
            conn = DriverManager.getConnection(DB_URL + name, USER, PASS);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void makeDB() {
        try {
            String makeTableQuery = "CREATE DATABASE  IF NOT EXISTS ORMDB";
            stmt.executeUpdate(makeTableQuery);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
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


    public <T> void makeTable(Class<T> myClass) {
        try {
            conn = DriverManager.getConnection(DB_URL + "ORMDB", USER, PASS);
            stmt = conn.createStatement();
            String makeTableQuery = Utilities.generateTable(myClass);
            System.out.println(makeTableQuery);
             stmt.executeUpdate(makeTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
