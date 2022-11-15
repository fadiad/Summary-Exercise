import mainB.Entities.User;
import mainB.db.Repo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ArgumentRepoTest {
    static Repo<User> repo;

    @BeforeAll
    public static void beforeAll() {
        repo = new Repo<>(User.class);
    }

    @Test
    public void addNullItem() {
        Assertions.assertThrows(NullPointerException.class, () -> repo.insertInto(null));
    }

    @Test
    public void insertManyItems() {
        Assertions.assertEquals(repo.insertMany(new ArrayList<>()), 0);
    }
    @Test
    public void deletedItem() {
        Assertions.assertThrows(NullPointerException.class, () -> repo.deleteObject(null));
    }
    @Test
    public void replaceItem() {
        Assertions.assertThrows(NullPointerException.class, () -> repo.replaceItem(null, new User(34,"Itnd", "1234")));
    }


}
