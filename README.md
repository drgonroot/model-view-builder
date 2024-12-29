
# model-view-builder: model到view的构建器

[![maven central](https://img.shields.io/maven-central/v/com.btye102/model-view-builder.svg?label=Maven%20Central)](https://github.com/drgonroot/model-view-builder)   [![License](https://img.shields.io/:license-MulanPSL2-blue.svg)](https://license.coscl.org.cn/MulanPSL2)
[![jdk](https://img.shields.io/badge/JDK-8+-green.svg)](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)

-------

## 📚简介

支持语言: java8+

解决了后端返回Json数据给客户端，需要大量代码手工拼装数据问题。简单定义视图类即可。
* 定义一个视图接口或者抽象类，标识为@View的注解，便可以快速将model的数据传递至View上
* 方法上添加@RelationView或者@RelationModel便于获取外部关联数据

## 🚀使用指南
```java
@View(modelClass = User.class, modelDaoClass = UserDAO.class)
public interface UserView {
    String getName();
}

public class UserDAO implements ModelDaoFactory, GetModel<Integer, User> {

    public User accept(Integer id) {
        return new User("用户名称");
    }

    @Override
    public User getModel(Integer integer) {
        return new User("用户名称");
    }

    @Override
    public <T> T getModelDao(Class<T> modelDaoClass) {
        return (T)new UserDAO();
    }
}

public class TestModel {
    private final String name;
    private final Integer userId;

    public TestModel(String name, Integer userId) {
        this.name = name;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public Integer getUserId() {
        return userId;
    }
}

@View(modelClass = TestModel.class, proxyType = ProxyType.CGLIB)
public abstract class TestView {
    public abstract String getName();

    @RelationView(bindModelAttrGetMethod = "getUserId()", viewClass = UserView.class)
    public abstract UserView getUser();

    @RelationModel(bindModelAttrGetMethod = "getUserId()", modelDaoClass= UserDAO.class, modelDaoMethod = "accept(java.lang.Integer)")
    public abstract User getUser1();

    public  String getOwner() {
        return "getOwner";
    }

    public  String getOwner2() {
        return getOwner() + "2";
    }
}

class ModelViewBuilderTest {
    @Test
    public void build() throws Exception {
        ModelViewBuilder modelViewBuilder = new ModelViewBuilder(new UserDAO());
        TestView testView = modelViewBuilder.build(new TestModel("testName", 1), TestView.class, new BuildContext());
        System.out.println(testView.getName());
        System.out.println(testView.getOwner());
        System.out.println(testView.getOwner2());
        UserView userView = testView.getUser();
        System.out.println(userView);
        System.out.println(userView.getName());
        System.out.println(testView.getUser1());
    }
}
```
### 🌓注解说明
* View
标识字段为视图
* RelationView
标识属性关联其他属性
* RelationModel
标识属性关联模型数据

## 📦安装

### 🍊Maven
在项目的pom.xml的dependencies中加入以下内容:

```xml
<dependency>
    <groupId>com.btye102</groupId>
    <artifactId>model-view-builder</artifactId>
    <version>1.0.0</version>
</dependency>
```