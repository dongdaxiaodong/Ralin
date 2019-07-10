## Ralin : 一个简单实用的java数据库操作库

### 特性
1. 单表操作不写sql语句
2. 优化多表操作时设置参数的编码体验以及优化返回结果集

如何使用? 创建一个maven项目 然后在pom.xml中放入下面的xml代码即可

```
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://www.jitpack.io</url>
		</repository>
</repositories>

<dependencies>
<dependency>
	    <groupId>com.github.dongdaxiaodong</groupId>
	    <artifactId>Ralin</artifactId>
	    <version>1.6</version>
</dependency>
</dependencies>

```

接着,就可以调用Ralin提供的api极其方便的进行增删查改了

### 打开数据库(以mysql为例,数据库名为practice,密码为123456789)
```
Ralin.open("jdbc:mysql://localhost:3306/practice?useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","123456789");
```
如果报错，可以切换mysql-connector-java版本

当然Ralin也可以连接数据库连接池`Ralin.open(datasource)`,下面以阿里巴巴开源的druid为例,实际使用注意修改数据库名称和密码
```
DruidDataSource dataSource = new DruidDataSource();
dataSource.setDriverClassName("com.mysql.jdbc.Driver");
 
dataSource.setUrl("jdbc:mysql://localhost:3306/testralin?useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
 
dataSource.setUsername("root");
dataSource.setPassword("123456789");
Ralin.open(dataSource);
```

### 定义模型(下面建立两个模型用于说明api)

```
--Teacher.java
import ralin.Model
public class Teacher extends Model{
    private int id;
    private String name;
    private String course;
    
    public Teacher(){};
    
    public Teacher(String name,String course){
    	this.name=name;
	this.course;
    }
}

--Student.java
import ralin.Model;
public class Student extends Model{
    private int id;
    private String name;
    private int gender;
    private int tid;
    
    public Student(){};

    public Student(String name,int gender,int tid){
        this.name=name;
	this.gender=gender;
	this.tid=tid;
    }}
```
getter,setter省略

### 约定
1. 创建table时，设置一个主键，并且让其自增!
2. 你需要使用Ralin进行增删查改的类需要继承Model类，同时需要注意的是类名默认和数据库table名一致，如果不一致使用注解`@Table(name="")`,同样的，属性也应该和数据库中字段一致

### 添加
`Ralin.insert(new Student("colin",0,1)).commit()`,insert方法中参数是一个Student对象,注意加上commit()
 


### 查询

Ralin的api设计和java8中stream的操作类似可以实现select(boy.class).where().like().between().notEq()......


| api | 说明 | 解析为的sql语句 |
| ------ | ------ | ------ |
| where | select(Student.class).where(name,"colin") | where name = 'colin' |
| where | select(Student.class).where(id>?,20) | where id > 20 |
| like | select(Student.class).like(name,"colin") | where name like '%colin%' |
| between | select(Student.class).between(tid,10,20) |  where tid between 10 and 20 |
| count | 获取数量 |  |
| all | 一般用于表达式结尾，返回查询到底对象列表 |  |,还有很多api如`in`,`isNull`,`isNotNull`,`notEq`等等

### 修改
修改和查询类似，只是最后要加上.commit()
一般的修改语句为`Ralin.update(Student.class).set().where()`,当然Ralin中也提供了其他的api

### 删除
`Ralin.delete(Student.class).where().commit()`。
小贴士:可以先通过select获取到需要删除的数据的id,然后通过Ralin.delete().byId(id).commit()进行删除

## bySql
上面的例子都是使用ralin进行的单表操作，接下来，我们使用ralin的bySql实现多表操作

联合查询示例:
```
一:
	List<Student> students=Ralin.bySql(Student.class).
                setSql("select s.id,s.name from student s join teacher t on s.tid=t.id where t.course='math'").
                commit();
        for(Student student:students){
            System.out.println(student.getId()+" and "+student.getName());
        }
	
	
二:
 	int gender=2;
        String subject="chinese";
        List<Teacher> teachers=Ralin.bySql(Teacher.class).
            setSql("select t.id,t.name,t.course from teacher t,student s where s.tid=t.id and s.gender=? and t.course=?").
            setParam(gender).
            setParam(subject).
            commit();
        for(Teacher teacher:teachers){
            System.out.println(teacher.getId()+" and "+teacher.getName()+" and "+teacher.getCourse());
        }	
```

### 关于bySql模块
1. bySql()方法的参数是执行结果的返回类型
2. bySql模块不支持同时获取多表的属性即(select t.id,s.name......)
3. **如果使用bysql进行增，删，改，则将bySql()的参数设为null,Ralin.bySql(null).setSql(.....).setParam(...).commit()**
4. 注意加上.commit()
