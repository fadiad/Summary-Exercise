import TestEntities.simpleEntity;
import mainB.db.Repo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestApi {
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
    public void insertOne()
    {
        int id=24;
        simpleEntity val = new simpleEntity(id, "val");
        repo.insertInto(val);
        List<simpleEntity> list = repo.getAllObjects().stream().filter(simple -> simple.getId() == id).collect(Collectors.toList());
        Assertions.assertTrue(list.size()==1);
        Assertions.assertTrue(val.equals(list.get(0)));
    }
    @Test
    public void insertMany()
    {
        int many=10;
        List<simpleEntity> list=new ArrayList<>();
        for (int i = 0; i <many ; i++) {
            simpleEntity val = new simpleEntity(i, "val");
        list.add(val);
        }
        repo.insertMany(list);
        List<simpleEntity> AllObjects = repo.getAllObjects();
        Assertions.assertTrue(AllObjects.size()==many);
        Assertions.assertArrayEquals(list.toArray(),AllObjects.toArray(),"Insert many elements work");
    }
}
