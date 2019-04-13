Ralin->一个简单实用的java数据库orm框架

如何使用?

Ralin核心封装了sql2o,所以需要向你的java项目中导入ralin-1.0-SNAPSHOT.jar和sql2o-1.5.4.jar

接着,就可以调用Ralin提供的api极其方便的进行增删查改了

### 打开数据库(以mysql为例)
`        Ralin.open("jdbc:mysql://localhost:3306/practice","root","123456789");
`
如果报错，则切换mysql-connector-java版本或者在数据库名称后面加上`useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC`

当然Ralin也连接池`Ralin.open(datasource)`

### 定义模型(下面建立一个boy类用于说明api)

```
import ralin.Model;
public class boy extends Model{
    private int id;
    private int weight;
    private String look;

    public boy(int weight,String look){
        this.weight=weight;
        this.look=look;
    }}
```
getter,setter省略
你需要使用Ralin进行增删查改的类需要继承Model类，同时需要注意的是类名默认和数据库table名一致，如果不一致使用注解`@Table(name="")`,同样的，属性也尽量和数据库中字段一致

### 添加
`Ralin.insert(new boy(30,"handsome"))`,insert方法中参数是一个boy对象(如果不想注明id,则建表时就应该将其设置为递增)

`
boy me=new boy(20,"nice");
Ralin.insert(me).commit()
`,注意要加上.commit()

### 查询

Ralin的api设计和java8中stream的操作类似可以实现select(boy.class).where().like().between().notEq()......


| api | 说明 | 解析为的sql语句 |
| ------ | ------ | ------ |
| where | select(boy.class).where(look,"good") | where look = 'good' |
| where | select(boy.class).where(weight>?,20) | where weight > 20 |
| like | select(boy.class).like(name,"colin") | where name like '%colin%' |
| between | select(boy.class).between(weight,10,20) |  where weight between 10 and 20 |
| count | 获取数量 |  |
| all | 一般用于表达式结尾，返回查询到底对象列表 |  |,还有很多api如`in`,`isNull`,`isNotNull`,`notEq`等等

### 修改
修改和查询类似，只是最后要加上.commit()
一般的修改语句为`Ralin.update(boy.class).set().where()`,当然Ralin中也提供了其他的api

### 删除
`Ralin.delete(boy.class).where().commit()`。
小贴士:可以先通过select获取到需要删除的数据的id,然后通过Ralin.delete().byId(id).commit()进行删除
