package ralin;
import org.sql2o.Sql2o;
import ralin.query.*;

import javax.sql.DataSource;

//todo----异常检测！！
public class Ralin {
    public static Sql2o sql2o;
    public static void open(String url,String name,String password){
        Ralin.sql2o=new Sql2o(url,name,password);
    }

    public static void open(DataSource dataSource){
        Ralin.sql2o=new Sql2o(dataSource);
    }
    public static Select select(Class<? extends Model> returnType){
        Select startSelect=new Select(returnType);
        startSelect.selectStart();
        return startSelect;
    }

    public static BySql bySql(Class returnType){
        return new BySql(returnType);
    }


    public static Update update(Class<? extends Model> returnType){
        Update startUpdate=new Update(returnType);
        startUpdate.updateStart();
        return startUpdate;
    }

    public static Delete delete(Class<? extends Model> returnType){
            Delete startDelete=new Delete(returnType);
            startDelete.deleteStart();
            return startDelete;
        }


        @SuppressWarnings("unchecked")
        public static <T extends Model> Insert insert(T type){

            return new Insert(type);
    }
}
