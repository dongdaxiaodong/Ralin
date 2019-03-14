package ralin;

import java.util.List;

public class SqlHandle<T extends Model> {

    public  List<T> resultAll(String queryStr,Class<T> returnType){
       org.sql2o.Connection ralinCon=Ralin.sql2o.open();
       long nowTime=System.currentTimeMillis();
       List<T> allResults=ralinCon.createQuery(queryStr).executeAndFetch(returnType);
       System.out.println(System.currentTimeMillis()-nowTime);
       ralinCon.close();
       return allResults;
    }

    public List<T> resultCount(String queryStr,Class<T> returnType){
        org.sql2o.Connection ralinCon=Ralin.sql2o.open();
        List<T> allResults=ralinCon.createQuery(queryStr).executeAndFetch(returnType);
        ralinCon.close();
        return allResults;
    }

    public T resultFirst(String queryStr,Class<T> returnType){
        System.out.println(queryStr);
        org.sql2o.Connection ralinCon=Ralin.sql2o.open();
        List<T> firstResult=ralinCon.createQuery(queryStr).executeAndFetch(returnType);
        ralinCon.close();
        return firstResult.get(0);
    }

    public void updateCommit(String query,Class<T> returnType){
        System.out.println(query);
        org.sql2o.Connection ralinCon=Ralin.sql2o.open();
        ralinCon.createQuery(query).executeUpdate();
        ralinCon.close();
    }

    public void deleteCommit(String query,Class<T> returnType){
        org.sql2o.Connection ralinCon=Ralin.sql2o.open();
        ralinCon.createQuery(query).executeUpdate();
        ralinCon.close();
    }
    public void insertCommit(String query){
        org.sql2o.Connection ralinCon=Ralin.sql2o.open();
        ralinCon.createQuery(query).executeUpdate();
        ralinCon.close();
    }
}
