package ralin.query;

import ralin.Model;
import ralin.RalinUtils.handleAnno;

public class Delete {
    private String queryStr;
    private Class<? extends Model> returnType;
    private static SqlHandle sqlHandle=new SqlHandle();

    public Delete(Class<? extends Model> returnType){
        this.returnType=returnType;
        this.queryStr="";
    }

    public void deleteStart(){
            String annoStr=handleAnno.tableName(this.returnType);
            if(annoStr.equals("none")){
                this.queryStr="delete from "+this.returnType.getSimpleName()+" ";
            }
            else {
                this.queryStr="delete from "+annoStr+" ";
            }
    }

    public Delete where(String field,String value){
        this.queryStr= QueryHandle.whereHandle(field,value,this.queryStr);
        return this;
    }
    public Delete byId(int idNum){
        if(this.queryStr.contains("where")){
            this.queryStr=this.queryStr+" and id="+idNum;
        }
        else {
            this.queryStr=this.queryStr+" where id="+idNum;
        }
        return this;
    }

    public Delete like(String field,String value){
        if(field.contains("where") || field.contains("like")){
            this.queryStr=this.queryStr+" and "+field+" like '%"+value+"%' ";
        }
        else {
            this.queryStr=this.queryStr+" where "+field+" like '%"+value+"%' ";
        }
        return this;
    }


    @SuppressWarnings("unchecked")
    public void commit(){
        Delete.sqlHandle.deleteCommit(this.queryStr,this.returnType);
    }
}
