package ralin.RalinUtils;

import ralin.Model;

import java.lang.annotation.Annotation;

public class handleAnno {
    public static String tableName(Class<? extends Model> model){
        return tableInfor(model,0);
    }

    public static String tablePk(Class<? extends Model> model){
        return tableInfor(model,1);
    }

    public static String tableInfor(Class<? extends Model> model,int type){
        String tableName="none";
        String pkName="none";
        Annotation[] annotations=model.getAnnotations();
        if(annotations.length!=0){
            String toStringAnnotation=annotations[0].toString();
            if(toStringAnnotation.contains("pk") && toStringAnnotation.contains("name")){
                int pkIndex=toStringAnnotation.indexOf("pk");
                int RightParenthesis=toStringAnnotation.indexOf(")");
                pkName=toStringAnnotation.substring(pkIndex+4,RightParenthesis-1);
                int commaIndex=toStringAnnotation.indexOf(",");
                int firstEqualIndex=toStringAnnotation.indexOf("=");
                tableName=toStringAnnotation.substring(firstEqualIndex+2,commaIndex-1);
            }


        }
        if(type==0){
            return tableName;
        }
        else{
            return pkName;
        }
    }
    public static <T> String objTableName(T model){
        Annotation[] annotations=model.getClass().getAnnotations();
        if(annotations.length==0){
            return "none";
        }
        else {
            String toStringAnnotation=annotations[0].toString();
            int fontIndex=toStringAnnotation.indexOf("(");
            int lastIndex=toStringAnnotation.indexOf(")");
            String anStr=toStringAnnotation.substring(fontIndex+1,lastIndex).split("=")[1];
            anStr=anStr.substring(1,anStr.length()-1);
            return anStr;
        }
    }
}
