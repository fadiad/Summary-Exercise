package mainB;

import mainB.Entities.User;
import mainB.db.Repo;
import mainB.db.Utilities;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException {

        Repo repo = new Repo(User.class);
//        Utilities.handleAnnotations(User.class);
    }
}
