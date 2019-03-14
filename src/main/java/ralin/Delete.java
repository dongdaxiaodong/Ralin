package ralin;

import ralin.RalinUtils.handleAnno;
public class Delete {
    private String queryStr;
    private Class<? extends Model> returnType;
    private static SqlHandle sqlHandle=new SqlHandle();

    public Delete(Class<? extends Model> returnType){
        this.returnType=returnType;
        this.queryStr="";
    }

    protected void deleteStart(){
            String annoStr=handleAnno.tableName(this.returnType);
            if(annoStr.equals("none")){
                this.queryStr="delete from "+this.returnType.getSimpleName()+" ";
            }
            else {
                this.queryStr="delete from "+annoStr+" ";
            }
    }

    public Delete where(String field,String value){
        //        首先判断是否有差值比较
        if(field.contains("?")){
            this.whereDifferent(field,value);
        }
        else {
            if(this.queryStr.contains("where")){
                this.queryStr=this.queryStr+"and "+field+"= '"+value+"'";
            }
            else {
                this.queryStr=this.queryStr+"where "+field+"= '"+value+"'";
            }
//            将参数放入queryPar,以及加上空格
            this.queryStr+=" ";
        }
        return this;
    }
    private Delete byId(int idNum){
        if(this.queryStr.contains("where")){
            this.queryStr=this.queryStr+" and id="+idNum;
        }
        else {
            this.queryStr=this.queryStr+" where id="+idNum;
        }
        return this;
    }

    private Delete whereDifferent(String field,String value){
        int index=field.indexOf("?");
        if(this.queryStr.contains("where")){
            this.queryStr=this.queryStr+"and "+field.substring(0,index)+" '"+value+"'";
        }
        else {
            this.queryStr=this.queryStr+"where "+field.substring(0,index)+" '"+value+"'";
        }
        this.queryStr+=" ";
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

    public void commit(){
        Delete.sqlHandle.deleteCommit(this.queryStr,this.returnType);
    }
}
