package code.christ.netty.util;

import io.netty.bootstrap.ServerBootstrap;

import java.util.concurrent.TimeUnit;

/**
 * Created by christmad on 2019/10/31.
 */
public class BindUtil {
    public static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("成功绑定端口[" + port + "]");
            } else {
                System.out.println("绑定端口[" + port + "]失败");
                // 闭包里面不需要 catch exception？
                TimeUnit.MILLISECONDS.sleep(100);
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
