package mainB.queries;

import java.util.List;

public class QueriesUtilities {
    public static String getAllObjects(String tableName)
    {
        return "select * from "+ tableName;
    }
    public static String insertObject(String tableName,Object T)
    {
        return "insert Into "+tableName +" Values";
    }

}
