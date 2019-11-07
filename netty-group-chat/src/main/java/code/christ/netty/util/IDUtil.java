package code.christ.netty.util;

import java.util.UUID;

/**
 * Created by christmad on 2019/11/5.
 */
public class IDUtil {
    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
