package mainB.db;

import mainB.queries.MySQLMethods;
import mainB.queries.QueriesUtilities;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;


public class Repo<T> {
    private final Statement stmt;

    private final String TableName;

    private final Class<T> myClass;

    public Repo(Class<T> myClass) {
        this.myClass = myClass;
        this.TableName = myClass.getSimpleName();
        stmt = ConnectionManger.getInstance().makeTable(myClass);
    }


    public int insertInto(T obj) {
        String s = QueriesUtilities.insertObject(TableName, obj, myClass);
        try {
            Utilities.logger.debug(s);
            return stmt.executeUpdate(s);
        } catch (SQLException e) {
            Utilities.logger.error("sql error caz : " + e.getErrorCode());
            if (e.getErrorCode() == 1062)
                System.out.println("You are trying to insert a user with an existed id");
            else if (e.getErrorCode() == 1064)
                System.out.println(e.getMessage());
        }
        return -1;
    }


    public void insertMany(List<T> list) {
        for (T obj : list) {
            insertInto(obj);
        }
    }

    public void replaceItem(T oldObject, T newObject) {
        deleteObject(oldObject);
        insertInto(newObject);
    }

    public int deleteObject(T obj) {
        Map<String, Object> con = JsonConvertor.fromObjectToMap(obj, myClass);
        String deleteQuery = QueriesUtilities.selectAndDeleteQueries(TableName, con, MySQLMethods.DELETE);
        return Applies.applyUpdate(stmt, deleteQuery);

    }


    public int update(Map<String, Object> conditions, Map<String, Object> updateValues) {
        String updateQuery = QueriesUtilities.updateQuery(TableName, conditions, MySQLMethods.UPDATE, updateValues);
        System.out.println(updateQuery);
        return Applies.applyUpdate(stmt, updateQuery);
    }


    public int deleteObjectsByConditions(Map<String, Object> conditions) {
        String whereQuery = QueriesUtilities.selectAndDeleteQueries(TableName, conditions, MySQLMethods.DELETE);
        System.out.println(whereQuery);
        return Applies.applyUpdate(stmt, whereQuery);
    }

    public List<T> getObjectsByConditions(Map<String, Object> conditions) {
        String whereQuery = QueriesUtilities.selectAndDeleteQueries(TableName, conditions, MySQLMethods.SELECT);
        return Applies.apply(stmt, whereQuery, myClass);
    }


    public List<T> getAllObjects() {
        String getAllObjects = QueriesUtilities.getAllObjects(TableName);
        return Applies.apply(stmt, getAllObjects, myClass);
    }

    public Optional<T> getObjectById(int id) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("id", id);
        String query = QueriesUtilities.selectAndDeleteQueries(TableName, condition, MySQLMethods.SELECT);
        List<T> item = Applies.apply(stmt, query, myClass);
        if (item.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(item.get(0));
        }
    }

}
