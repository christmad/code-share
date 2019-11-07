package code.christ.netty.util;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by christmad on 2019/10/31.
 */
public class ConnectUtil {

    private static final int MAX_RETRY = 5;

    // 为重连设计一个指数退避的重连间隔
    public static ChannelFuture connect(Bootstrap bootstrap, int port, int retry) {
        return bootstrap.connect("localhost", port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else if (retry == 0) {
                System.err.println("连接失败，重连次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = MAX_RETRY - retry + 1;
                // 本次重连间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连......");
                // Bootstrap 定时任务的调用方法
                bootstrap.config().group().schedule(() -> connect(bootstrap, port, retry - 1), delay, TimeUnit.SECONDS);

            }
        });
    }

}
