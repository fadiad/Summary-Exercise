package mainB.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class ConnectionManger {
    private final String DB_URL = "jdbc:mysql://localhost:3306/";
    private final String USER = "root";
    private final String PASS = "";
    private static Connection conn;
    private static Statement stmt;
    private static final String DBNAME = "ORMDB";

    private static ConnectionManger instance;
    public  static ConnectionManger getInstance()
    {
        if(instance==null) instance=new ConnectionManger();
        return  instance;
    }
    private ConnectionManger(){
        makeConnection("");
        makeDB();
    }
    public Statement makeTable(Class<?> myClass) {
        try {
            conn = DriverManager.getConnection(DB_URL + DBNAME, USER, PASS);
            stmt = conn.createStatement();
            String makeTableQuery = Utilities.generateTable(myClass);
            Utilities.logger.debug(makeTableQuery);
            stmt.executeUpdate(makeTableQuery);
            return stmt;
        } catch (SQLException e) {
            throw new IllegalStateException("There was problems when trying to create the table. " ,e);
        }
    }
    public String deleteTable(String TableName) {
        return "DROP TABLE IF EXISTS " + TableName + ";";
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
    private void makeConnection(String name) {
        try {
            conn = DriverManager.getConnection(DB_URL + name, USER, PASS);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
