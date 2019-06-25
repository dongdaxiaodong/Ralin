package ralin.query;
import ralin.Model;
import ralin.RalinUtils.handleAnno;

import ralin.RalinUtils.handleRef;

public class Insert<T extends Model> {
    private String queryStr;
    private static SqlHandle sqlHandle=new SqlHandle();
    private T type;
    public Insert(T type){
        this.type=type;
        String annoStr=handleAnno.objTableName(type);
        if(annoStr.equals("none")){
            this.queryStr="insert into "+this.type.getClass().getSimpleName()+" ";
        }
        else {
            this.queryStr="insert into "+annoStr+" ";
        }
    }
    public void commit(){

        try {
            String insertStr=handleRef.returnSqlByRef(this.type);
            String finalStr=this.queryStr+insertStr;
            Insert.sqlHandle.insertCommit(finalStr);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
