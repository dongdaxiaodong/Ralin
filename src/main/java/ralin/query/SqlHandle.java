package ralin.query;

import ralin.Model;
import ralin.Ralin;

import java.util.ArrayList;
import java.util.List;

public class SqlHandle<T extends Model> {

    public  List<T> resultAll(String queryStr,Class<T> returnType){
       org.sql2o.Connection ralinCon= Ralin.sql2o.open();
       List<T> allResults=ralinCon.createQuery(queryStr).executeAndFetch(returnType);
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
        org.sql2o.Connection ralinCon=Ralin.sql2o.open();
        List<T> firstResult=ralinCon.createQuery(queryStr).executeAndFetch(returnType);
        ralinCon.close();
        return (firstResult.size()==0)?null:firstResult.get(0);
    }


    public List<T> sqlCommit(Class returnType,String sql){
        org.sql2o.Connection ralinCon=Ralin.sql2o.open();
        List<T> firstResult=ralinCon.createQuery(sql).executeAndFetch(returnType);
        ralinCon.close();
        return firstResult;
    }
    public List<T> changeSqlCommit(String sql){
        org.sql2o.Connection ralinCon=Ralin.sql2o.open();
        ralinCon.createQuery(sql).executeUpdate();
        ralinCon.close();
        return new ArrayList<>();
    }

    public void updateCommit(String query,Class<T> returnType){
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
