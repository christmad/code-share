package code.christ.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by christmad on 2019/10/23.
 */
@Component
@ConfigurationProperties("my.first")
public class MyObject {

    private String name;
    private int age;

    public MyObject() {
        System.out.println("MyObject 无参构造器 初始化");
    }

    public MyObject(String name, int age) {
        System.out.println("MyObject 全参数构造器 初始化");
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "MyObject{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
