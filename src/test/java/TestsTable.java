import mainB.db.Repo;
import org.junit.jupiter.api.*;

import TestEntities.simpleEntity;

import java.util.List;
import java.util.stream.Collectors;

public class TestsTable {
    public static Repo<simpleEntity> repo;
    @BeforeEach
    public void beforeEach()
    {
        repo =new Repo<>(simpleEntity.class);
    }
    @AfterEach
    public void afterEach()
    {
        repo.deleteTable();
    }
    @Test
    public void checkPrimaryKey_id_should_be_Primary_key()
    {
        int id=24;
        simpleEntity val = new simpleEntity(id, "val");
        repo.insertInto(val);
        repo.insertInto(new simpleEntity(id,"anotherVal"));
        List<simpleEntity> list = repo.getAllObjects().stream().filter(simple -> simple.getId() == id).collect(Collectors.toList());
        Assertions.assertTrue(list.size()==1);
        Assertions.assertTrue(val.equals(list.get(0)));
    }
}
