package mainB;

import mainB.Entities.User;
import mainB.db.Repo;
import mainB.db.Utilities;

import java.lang.reflect.Field;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException {

       User user=new User(10,"Messi1","Ronaldo1");
        Repo repo = new Repo(User.class);
        repo.insertInto(user);
        List<User> allObjects = repo.getAllObjects();
       /* for (User user: allObjects) {
            System.out.println(user);
        }*/
//        Utilities.handleAnnotations(User.class);
    }
}
