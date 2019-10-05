package code.share.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by christmad on 2019/10/5.
 */
public class NettyClientBootstrap {

    private static final int MAX_RETRY = 5;

    public void minimalClient() {
        NioEventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 参考 NettyServerBootstrap, client启动同样也有几个类似的流程
                // 1.指定线程模型
                .group(worker)
                // 2.指定IO类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO处理逻辑
                // 这里的泛型官网demo用的也是 SocketChannel 接口, 实现类由上面的 channel() 方法指定
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                    }
                });
        /*
        客户端启动的其他方法
         */
        // attr方法，给 NioSocketChannel 绑定属性，通过 channel.attr() 取出属性
        bootstrap.attr(AttributeKey.newInstance("clientName"), "christmad-netty");
        // option方法，给客户端连接设置TCP底层属性.
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);


        // 4.建立连接——指数退避时间间隔的失败重连
        int port = 8000;
        int retry = 5;
        connect(bootstrap, port, retry);
    }

    // 为重连设计一个指数退避的重连间隔
    private void connect(Bootstrap bootstrap, int port, int retry) {
        bootstrap.connect("localhost", 8000).addListener(future -> {
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
                // Bootstrap 的定时任务
                bootstrap.config().group().schedule(() -> connect(bootstrap, port, retry - 1), delay, TimeUnit.SECONDS);

            }
        });
    }
}
