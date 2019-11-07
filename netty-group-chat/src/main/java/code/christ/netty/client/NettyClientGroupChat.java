package code.christ.netty.client;

import code.christ.netty.client.handler.HeartBeatTimerHandler;
import code.christ.netty.client.handler.IMResponseHandler;
import code.christ.netty.client.handler.LoginResponseHandler;
import code.christ.netty.console.ConsoleCommandManager;
import code.christ.netty.console.LoginConsoleCommand;
import code.christ.netty.handler.ExceptionHandler;
import code.christ.netty.handler.IMIdleStateHandler;
import code.christ.netty.handler.IMProtocolSplitter;
import code.christ.netty.handler.UltimatePacketCodecHandler;
import code.christ.netty.util.ConnectUtil;
import code.christ.netty.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * Created by christmad on 2019/11/5.
 */
public class NettyClientGroupChat {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new IMIdleStateHandler())
                                .addLast(new IMProtocolSplitter())
                                .addLast(UltimatePacketCodecHandler.INSTANCE)
                                .addLast(new LoginResponseHandler())
                                .addLast(IMResponseHandler.INSTANCE)
                                .addLast(new HeartBeatTimerHandler())
                                .addLast(ExceptionHandler.INSTANCE)
                        ;

                    }
                });

        ChannelFuture channelFuture = ConnectUtil.connect(bootstrap, 8000, 3);
        while (!channelFuture.isSuccess()) {
            Thread.yield();
        }
        startConsoleThread(channelFuture.channel());
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {

            ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
            LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
            Scanner in = new Scanner(System.in);

            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(in, channel);
                } else {
                    consoleCommandManager.exec(in, channel);
                }
            }

        }).start();
    }
}
