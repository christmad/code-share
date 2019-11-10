package code.christ.notconfig;

import org.springframework.context.annotation.Bean;

/**
 * Created by christmad on 2019/11/10.
 * 不使用 @Configuration 标记，@Bean 注解不会生效
 */
public class NoConfigButNoteBean {

    @Bean
    public String hello() {
        return "hello";
    }

    @Bean
    public String world() {
        return "world";
    }
}
