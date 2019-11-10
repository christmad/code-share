package code.christ.config;

import code.christ.bean.MyObject;
import code.christ.importselector.annotation.PackagesImporter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by christmad on 2019/10/23.
 */
@Configuration
@PackagesImporter(packages = {"code.dev.arch", "code.christ.model", "code.christ.notconfig"})
public class MyConfig { // 被 @Configuration 注解也会生成一个 bean, 默认 bean name 和 @Component 规则一样——驼峰

    public MyConfig() {
        System.out.println("MyConfig 被 @Configuration 初始化了");
    }

    @Bean(name = "obj2")
    @ConfigurationProperties("my.second")   // 还不能和类上面的 prefix 重复
    public MyObject myObject() {
        return new MyObject();
    }

}
