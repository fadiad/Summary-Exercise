package mainB;

import mainB.db.Repo;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
//        Repo repo = new Repo("sonoo");
//        repo.makeQuery("select * from emp");

        Repo repo = new Repo("asd");
        repo.makeDB();


    }
}
