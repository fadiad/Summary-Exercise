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

        User user = new User(4, "t", "t");
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("id", 2);
        Repo repo = new Repo(User.class);
        repo.insertInto(user);
        List<User> allObjects = repo.getAllObjects();
        System.out.println(allObjects);

        //List<User> objectsByConditions = repo.getObjectsByConditions(conditions);

//        System.out.println(repo.deleteObjectsByConditions(conditions));

        System.out.println(repo.getAllObjects());

//        delete from ORMDB.User where id=10;

//        Map<String, Object> updates = new HashMap<>();
//        updates.put("email", "yal");
//
//        System.out.println("update line :  " + repo.update(conditions, updates));
//        for (User us : allObjects) {
//            System.out.println(us);
//        }
//        Utilities.handleAnnotations(User.class);
//        System.out.println(repo.getObjectById(10).get());

    }
}
