package code.christ;

import code.christ.bean.MyObject;
import code.christ.config.AnotherConfig;
import code.christ.config.MyConfig;
import code.dev.arch.DifferentLevelObject;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by christmad on 2019/10/23.
 */
@SpringBootApplication
@EnableConfigurationProperties(value = {AnotherConfig.class})
public class ConfigPracticeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConfigPracticeApplication.class, args);
        System.out.println("hello");
        try {
            Object hello = context.getBean("hello");
            Object world = context.getBean("world");
            System.out.println(hello + " " + world);
        } catch (BeansException e) {
            System.out.println("hello world not found");
        }

        SameLevelObject sameLevelObject = context.getBean("sameLevelObject", SameLevelObject.class);
        System.out.println("sameLevelObject = " + sameLevelObject);
        try {
            DifferentLevelObject bean = context.getBean(DifferentLevelObject.class);
            System.out.println("differentLevelObj = " + bean);
        } catch (BeansException e) {
            System.out.println("differentLevelObj can't handle");
        }
        MyConfig myConfig = context.getBean(MyConfig.class);
        System.out.println("get by type，myConfig = " + myConfig);
        MyConfig myConfig1 = (MyConfig) context.getBean("myConfig");
        System.out.println("get by name，myConfig1 = " + myConfig1);
        MyObject obj = context.getBean("myObject", MyObject.class);
        System.out.println("myObject=" + obj);
        MyObject myObject = context.getBean("obj2", MyObject.class);
        System.out.println("obj22222=" + myObject);
        AnotherConfig anotherConfig = context.getBean("anotherConfig", AnotherConfig.class);
        System.out.println("another=" + anotherConfig);
    }
}
