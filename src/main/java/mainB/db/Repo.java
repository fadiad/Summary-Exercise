package mainB.db;

import java.sql.*;

public class Repo<T> {
    private final String DB_URL = "jdbc:mysql://localhost:3306/";
    private final String USER = "root";
    private final String PASS = "";

    private final Class<T> myClass;

    private static Connection conn;
    private static Statement stmt;

    private static final String DBNAME = "ORMDB";

    public Repo(Class<T> myClass) {
        this.myClass = myClass;
        start();
    }

    public void start() {
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
