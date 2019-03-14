package ralin;
import org.sql2o.Sql2o;

import javax.sql.DataSource;

//todo----异常检测！！
public class Ralin {
    protected static Sql2o sql2o;
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

        public static <T extends Model> Insert insert(T type){
            Insert startInsert=new Insert(type);
            return startInsert;
    }
}
