package code.christ.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by christmad on 2019/10/23.
 */
@Configuration
@ConfigurationProperties("another")
public class AnotherConfig {

    /*
     * @Configuration
     * @ConfigurationProperties("another")
     * 上面这两个注解会帮我们在 spring IOC 里面注册两个 bean, 没错, 是两个
     * 两个 bean name 分别为：
     * (1) anotherConfig (2) another-code.christ.config.AnotherConfig
     * 第1个 bean name 来由应该是因为 @Configuration 本身引用了 @Component 注解的缘故
     * 第2个 bean name 应该是 @ConfigurationProperties("another") 这个注解帮我们注册进去的, 在spring boot文档中的 External Configuration 章节搜 <prefix>-<fqn> 就可以看到了
     */

    // 需要提供 setter 方法给spring赋值
    private int start;
    private int end;

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "AnotherConfig{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
