package code.christ.netty.server.handler;

import code.christ.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by christmad on 2019/11/3.
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() {}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("服务端打印：客户端未登录，断开连接！");
            ctx.channel().close();
        } else {
            // ChannelHandler 热插拔效果
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }
}
