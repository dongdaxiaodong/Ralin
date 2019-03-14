package ralin.RalinUtils;

import ralin.Model;

import java.lang.annotation.Annotation;

public class handleAnno {
    public static String tableName(Class<? extends Model> model){
        Annotation[] annotations=model.getAnnotations();
        if(annotations.length==0){
            return "none";
        }
        else {
            String toStringAnnotation=annotations[0].toString();
            int fontIndex=toStringAnnotation.indexOf("(");
            int lastIndex=toStringAnnotation.indexOf(")");
            String anStr=toStringAnnotation.substring(fontIndex+1,lastIndex).split("=")[1];
            return anStr;
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
            return anStr;
        }
    }
}
