package mainB;

import mainB.Entities.User;
import mainB.db.Repo;

import java.lang.reflect.Field;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
//        Repo repo = new Repo("sonoo");
//        repo.makeQuery("select * from emp");

//        Repo repo = new Repo("asd");
//        repo.makeDB();

        Field[] allFields = User.class.getDeclaredFields();
        Repo repo = new Repo("");

//        for (Field field : allFields) {
//            System.out.println(repo.getTypeOfDBTypes(field.getType().getSimpleName()));
//        }

        repo.makeTable(User.class);


    }
}
