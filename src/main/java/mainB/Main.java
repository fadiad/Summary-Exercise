package mainB;

import mainB.db.Repo;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
//        Repo repo = new Repo("sonoo");
//        repo.makeQuery("select * from emp");

//        Repo repo = new Repo("asd");
//        repo.makeDB();


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sonoo", "root", "");
//here sonoo is database name, root is username and password
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from emp");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }


    }
}
