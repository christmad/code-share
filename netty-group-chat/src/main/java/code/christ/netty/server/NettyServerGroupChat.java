package code.christ.netty.server;

import code.christ.netty.handler.ExceptionHandler;
import code.christ.netty.handler.IMIdleStateHandler;
import code.christ.netty.handler.IMProtocolSplitter;
import code.christ.netty.handler.UltimatePacketCodecHandler;
import code.christ.netty.server.handler.AuthHandler;
import code.christ.netty.server.handler.HeartBeatRequestHandler;
import code.christ.netty.server.handler.IMRequestHandler;
import code.christ.netty.server.handler.LoginRequestHandler;
import code.christ.netty.util.BindUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by christmad on 2019/11/5.
 */
public class NettyServerGroupChat {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                // 可共享的 handler 全用单例
                                .addLast(new IMIdleStateHandler())
                                // splitter 由于父类构造器显示指定不让共享，可能是在某些地方使用了有状态的方法
                                .addLast(new IMProtocolSplitter())
                                // 压缩 handler-合并编解码器
                                .addLast(UltimatePacketCodecHandler.INSTANCE)
                                .addLast(HeartBeatRequestHandler.INSTANCE)
                                .addLast(LoginRequestHandler.INSTANCE)
                                .addLast(AuthHandler.INSTANCE)
                                // 压缩 handler-合并平行 handler, 缩短事件传播路径. 群聊的很多操作只会进入一个，这些 handler 是平行的，可优化
                                .addLast(IMRequestHandler.INSTANCE)
                                .addLast(ExceptionHandler.INSTANCE)
                        ;

                    }
                });

        BindUtil.bind(serverBootstrap, 8000);
    }
}
