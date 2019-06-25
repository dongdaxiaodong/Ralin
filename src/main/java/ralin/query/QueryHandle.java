package ralin.query;

public class QueryHandle {

    public static String whereHandle(String field,Object objValue,String queryStr){
        String value=String.valueOf(objValue);
        if(field.contains("?")){
            int index=field.indexOf("?");
            if(queryStr.contains("where")){
                queryStr=queryStr+"and "+field.substring(0,index)+" '"+value+"'";
            }
            else {
                queryStr=queryStr+"where "+field.substring(0,index)+" '"+value+"'";
            }
        }
        else{
            if(queryStr.contains("where")){

                queryStr=queryStr+"and "+field+"= '"+value+"'";
            }
            else {
                queryStr=queryStr+"where "+field+"= '"+value+"'";
            }
//            将参数放入queryPar,以及加上空格
        }
        queryStr+=" ";
        return queryStr;
    }
}
