package ralin;
import ralin.RalinUtils.handleAnno;

public class Update {

    private String queryStr;
    private Class<? extends Model> returnType;
    private static SqlHandle sqlHandle=new SqlHandle();
    public Update(Class<? extends Model> returnType){
        this.returnType=returnType;
        this.queryStr="";
    }
    protected void updateStart(){
        String annoStr=handleAnno.tableName(this.returnType);
        if(annoStr.equals("none")){
            this.queryStr="update "+this.returnType.getSimpleName()+" ";
        }
        else {
            this.queryStr="update "+annoStr+" ";
        }
    }
    public Update set(String field,String value){
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
    private Update byId(int keyNum){
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

    private Update whereDifferent(String field,String value){
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

    public Update like(String field,String value){
        if(field.contains("where") || field.contains("like")){
            this.queryStr=this.queryStr+" and "+field+" like '%"+value+"%' ";
        }
        else {
            this.queryStr=this.queryStr+" where "+field+" like '%"+value+"%' ";
        }
        return this;
    }
    public void commit(){
        Update.sqlHandle.updateCommit(this.queryStr,this.returnType);
    }
}
