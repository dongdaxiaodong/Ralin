package ralin;

import java.util.List;

//这个类用于实现对具体实体类的操作(insert,upload,delete,select)
//todo--keyWords have where,like,
public class Model {

    private String queryStr;
    private Class<? extends Model> returnType;
    private static SqlHandle sqlHandle = new SqlHandle();

    public Model() {

    }

    public Model(Class<? extends Model> returnType) {
        this.returnType = returnType;
        this.queryStr = "";
    }
}

