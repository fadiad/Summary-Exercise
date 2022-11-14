package mainB;

import mainB.Entities.User;
import mainB.db.Repo;
import mainB.db.Utilities;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException {

        //User user=new User(10,"Messi1","Ronaldo1");
        Map<String,Object> conditions =new HashMap<>();
        conditions.put("id",11);
        Repo repo = new Repo(User.class);
        //repo.insertInto(user);
        List<User> allObjects = repo.getAllObjects();
        List<User> objectsByConditions = repo.getObjectsByConditions(conditions);
        System.out.println("hello");
        /* for (User user: allObjects) {
            System.out.println(user);
        }*/
//        Utilities.handleAnnotations(User.class);
    }
}
