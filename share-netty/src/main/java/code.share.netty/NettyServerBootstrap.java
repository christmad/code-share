package code.share.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * Created by christmad on 2019/10/5.
 */
public class NettyServerBootstrap {

    public void minimalServer() {
        // 两大线程组，boss线程组负责监听端口、accept新连接；worker线程组负责处理连接上数据的读写
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        // 服务端引导类，直接 new 出来用
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                // 1.给引导类配置线程模型
                .group(boss, worker)
                // 2.指定服务端IO模型为 NIO——如果要配置为 BIO，可以选择 OioServerSocketChannel
                .channel(NioServerSocketChannel.class)
                // 3. IO处理逻辑
                // 方法的 child 怎么理解呢？这是因为底层 accept 返回的连接和监听的端口会使用不同的端口，因此 childHandler 就是为新连接的读写所设置的 handler
                // 新连接中对应的泛型抽象是 NioSocketChannel, 该对象标识一条客户端连接
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                    }
                });
        // 以上就是 netty 最小化参数配置，需要三类属性：1.线程模型 2.IO模型 3.连接读写处理逻辑
        // 有了这三类属性，调用 ServerBootstrap#bind 方法就可以在本地运行起来

        /*
        服务端启动的其他方法
         */
        // handler方法和 childHandler方法对应起来。那么 handler方法就对应服务器自身启动时的一些逻辑
        serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
            @Override
            protected void initChannel(NioServerSocketChannel ch) throws Exception {
                System.out.println("服务器启动中");
            }
        });
        // attr方法可以给服务端的 channel 也就是 NioServerSocketChannel 对象指定一些自定义属性，说简单点就是给它维护一个 map
        // 通过 channel.attr() 取出属性
        serverBootstrap.attr(AttributeKey.newInstance("serverName"), "nettyServer");
        // childAttr方法 和 attr方法对应，那么前者就是为每一条新连接指定自定义属性，依然通过 channel.attr() 取出属性
        serverBootstrap.childAttr(AttributeKey.newInstance("clientKey"), "clientValue");
        // 设置TCP底层相关的属性，同样也有两个对应的方法：option() 和 childOption()
        // 服务端最常见的TCP底层属性，表示系统用于临时存放已完成三次握手的请求的连接队列的最大长度。如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        // 为每一条新连接设置的 TCP底层属性：
        // SO_KEEPALIVE：是否使用心跳机制；
        // TCP_NODELAY：nagle算法开关, TCP 用来处理 small packet problem 的算法，字面意思为是否立马发送。如果要求实时性高该值设为 true，如 redis 就是这么做的；
        //              通常默认操作系统该值为 false, 这允许 TCP 在未发送数据中最多可以有一个未被确认的小分组，
        //              举例来说客户端每次发送一个字节，将 HELLO 分五次发给服务端，客户端在应用层会调用 5次send方法。
        //              Nagle 算法会将 HELLO 分为 H 和 LLEO 两个分组，即 H 发送出去时 LLEO 在 nagle算法处理下被合并，在 H 的 ACK回来之后 LLEO 才被发出去，总共发送两次
        //              于是开启 Nagle算法后应用层调用 5次send 实际在 TCP 层面只发了两个包。如果 TCP_NODELAY 为 true，即禁用 Nagle算法，那么 HELLO 就会分成 5个 TCP packet，每个包都占 41字节，比较浪费带宽
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        // 修改为自动绑定递增端口
//        serverBootstrap.bind(8000);
        bind(serverBootstrap, 8000);
    }

    private void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("成功绑定端口[" + port + "]");
            } else {
                System.out.println("绑定端口[" + port + "]失败");
                bind(serverBootstrap, port + 1);
            }
        });
    }

}
