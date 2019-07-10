package ralin.query;

import java.util.List;

public class BySql {
    private Class returnType;
    private String queryStr;
    private static SqlHandle sqlHandle=new SqlHandle();

    public BySql(){};

    public BySql(Class returnType){
        this.returnType=returnType;
    }

    public BySql setSql(String sql){
        this.queryStr=sql;
        return this;
    }

    public BySql setParam(Object obj){
        String objParam=String.valueOf(obj);
        this.queryStr=this.queryStr.replaceFirst("\\?","'"+objParam+"'");
        return this;
    }

    public<T> List<T> commit(){
        return (this.returnType!=null)?sqlHandle.sqlCommit(this.returnType,this.queryStr):sqlHandle.changeSqlCommit(this.queryStr);
    }

}
