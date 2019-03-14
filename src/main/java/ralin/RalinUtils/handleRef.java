package ralin.RalinUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class handleRef {

    public static <T>String returnSqlByRef(T model) throws Exception{
        Field[] tField=model.getClass().getDeclaredFields();
        ArrayList<String> nameList=new ArrayList<>();
        ArrayList<String> valueList=new ArrayList<>();
        for(int i=0;i<tField.length;i+=1){
            Field nowField=tField[i];
            nowField.setAccessible(true);
            String name=nowField.getName();
            Object value=nowField.get(model);
            String strValue=String.valueOf(value);
            nameList.add(name);
            valueList.add(strValue);
        }
        String resultStr="(";
        for(int i=0;i<nameList.size();i+=1){
            if(i ==(nameList.size()-1)){
                resultStr=resultStr+nameList.get(i)+") ";
            }
            else {
                resultStr=resultStr+nameList.get(i)+",";
            }
        }
        resultStr+="values (";
        for(int i=0;i<valueList.size();i+=1){
            if(i==(valueList.size()-1)){
                resultStr=resultStr+"'"+valueList.get(i)+"')";
            }
            else {
                resultStr=resultStr+"'"+valueList.get(i)+"',";
            }
        }
        System.out.println(resultStr);
    return resultStr;
    }
}
