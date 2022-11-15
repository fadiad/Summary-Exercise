import mainB.Entities.User;
import mainB.db.Repo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class QueriesTests {
    static  String DBName="ORMDB";
    @Test
    public void createTable_should_connect_to_db() throws SQLException {
        Repo<User> repo=new Repo<>(User.class);
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DBName, "root", "");
        Statement stmt = conn.createStatement();
        Assertions.assertTrue(stmt!=null,"The application has connection ");
    }

}
