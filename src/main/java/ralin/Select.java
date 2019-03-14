package ralin;

import ralin.RalinUtils.handleAnno;

import java.util.List;
//todo--设计  将limit直接用于最后，这样就可以避免在其他方法中写入limit
public class Select {
    private String queryStr;
    private Class<? extends Model> returnType;
    private static SqlHandle sqlHandle=new SqlHandle();

    public Select(Class<? extends Model> returnType){
        this.returnType=returnType;
        this.queryStr="";
    }
    protected void selectStart(){
        String annoStr=handleAnno.tableName(this.returnType);
        if(annoStr.equals("none")){
            this.queryStr="select * from "+this.returnType.getSimpleName()+" ";
        }
        else {
            this.queryStr="select * from "+annoStr+" ";
        }
    }

    public Select where(String field,Object objValue){
        //        首先判断是否有差值比较
        String value=String.valueOf(objValue);
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

    private Select whereDifferent(String field,String value){
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

    public Select like(String field,Object objValue){
        String value=String.valueOf(objValue);
        if(this.queryStr.contains("where") || this.queryStr.contains("like")){
            this.queryStr=this.queryStr+" and "+field+" like '%"+value+"%' ";
        }
        else {
            this.queryStr=this.queryStr+" where "+field+" like '%"+value+"%' ";
        }
        return this;
    }

    public Select isNull(String field){
        if(this.queryStr.contains("where")){

                String isNullStr=" and "+field+" is null ";
                this.queryStr=this.queryStr+isNullStr;
        }
        else {
            String isNullStr=" where "+field+" is null ";
            this.queryStr+=isNullStr;
        }
        return this;
    }

    public Select isNotNull(String field){
        if(this.queryStr.contains("where")){

                String isNullStr=" and "+field+" is not null ";
                this.queryStr=this.queryStr+isNullStr;
        }
        else {
            String isNullStr=" where "+field+" is not null ";
            this.queryStr+=isNullStr;
        }
        return this;
    }

    public <V>Select in(String field,List<V> objects){
        String inStr="(";
        for(int i=0;i<objects.size();i+=1){
            if(i==objects.size()-1){
                inStr=inStr+"'"+String.valueOf(objects.get(i))+"')";
            }
            else {
                inStr=inStr+"'"+String.valueOf(objects.get(i))+"',";
            }
        }
        if(this.queryStr.contains("where")){
            this.queryStr=this.queryStr+" and "+field+" in "+inStr;
        }
        else {
            this.queryStr=this.queryStr+" where "+field+" in "+inStr;
        }
        return this;
    }
    public Select betWeen(String field,Object fObj,Object lObj){
        if(this.queryStr.contains("where")){
            this.queryStr=this.queryStr+" and "+field+" between "+"'"+String.valueOf(fObj)+"' and '"+String.valueOf(lObj)+"'";
        }
        else {
            this.queryStr=this.queryStr+" where "+field+" between "+"'"+String.valueOf(fObj)+"' and '"+String.valueOf(lObj)+"'";
        }
        return this;
    }

    public Select notEq(String field,Object objValue){
        String value=String.valueOf(objValue);
        if(this.queryStr.contains("where")){
            String notEqStr=" and "+field+" != "+value+" ";
            this.queryStr+=notEqStr;
        }
        else {
            String notEqStr=" where "+field+" != "+value+" ";
            this.queryStr+=notEqStr;
        }
        return this;
    }

    public <T extends Model> T firstone(){
        this.queryStr=this.queryStr+" limit 0,1";
        return (T)Select.sqlHandle.resultFirst(this.queryStr,this.returnType);
    }

    public long count(){
//        this.queryStr="select count(1) from "+this.returnType.getSimpleName();
        long resultCount=Select.sqlHandle.resultCount(this.queryStr,this.returnType).size();
        return resultCount;
    }

    public <T>List<T> all(){
        return Select.sqlHandle.resultAll(this.queryStr,this.returnType);
    }

}
