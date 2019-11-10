package code.christ;

import code.christ.bean.MyObject;
import code.christ.config.AnotherConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by christmad on 2019/10/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest/*(classes = {ConfigPracticeApplication.class})*/
public class ConfigTest {

    @Autowired
    @Qualifier("obj2")
    private MyObject myObject;

    @Autowired
    @Qualifier("myObject")
    private MyObject obj;

    @Autowired
    private AnotherConfig anotherConfig;

    @Test
    public void testPropertyBean() {
        System.out.println("myObject=" + obj);
        System.out.println("obj2=" + myObject);
        System.out.println();
        System.out.println("another=" + anotherConfig);
    }
}
