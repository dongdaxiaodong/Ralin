package ralin.query;
import ralin.Model;
import ralin.RalinUtils.handleAnno;

public class Update {

    private String queryStr;
    private Class<? extends Model> returnType;
    private static SqlHandle sqlHandle=new SqlHandle();
    public Update(Class<? extends Model> returnType){
        this.returnType=returnType;
        this.queryStr="";
    }
    public void updateStart(){
        String annoStr=handleAnno.tableName(this.returnType);
        if(annoStr.equals("none")){
            this.queryStr="update "+this.returnType.getSimpleName()+" ";
        }
        else {
            this.queryStr="update "+annoStr+" ";
        }
    }
    public Update set(String field,Object fieldValue){
        String value=String.valueOf(fieldValue);
//        接下来需要判断以及切割
        if(this.queryStr.contains("where")){
            int indexWhere=this.queryStr.indexOf("where");
            String fontStr=this.queryStr.substring(0,indexWhere);
            String backStr=this.queryStr.substring(indexWhere);
            if(fontStr.contains("set")){
                fontStr=fontStr+", "+field+"='"+value+"'";
            }
            else {
                fontStr=fontStr+" set "+field+"='"+value+"'";
            }
            this.queryStr=fontStr+backStr;
        }
        else {
            if(this.queryStr.contains("set")){
                this.queryStr=this.queryStr+", "+field+"='"+value+"'";
            }
            else {
                this.queryStr=this.queryStr+" set "+field+"='"+value+"'";
            }
        }
        return this;
    }
    public Update where(String field,String value){
        this.queryStr= QueryHandle.whereHandle(field,value,this.queryStr);

        return this;
    }
    public Update byId(int keyNum){
        if(this.queryStr.contains("where")){
            String updateStr=" and id="+keyNum+" ";
            this.queryStr=this.queryStr+updateStr;
        }
        else {
            String updateStr=" where id="+keyNum+" ";
            this.queryStr=this.queryStr+updateStr;
        }
        return this;
    }



    public Update like(String field,String value){
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
        Update.sqlHandle.updateCommit(this.queryStr,this.returnType);
    }
}
