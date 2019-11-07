package code.christ.netty.server.handler;

import code.christ.netty.protocol.packet.request.LogoutRequestPacket;
import code.christ.netty.protocol.packet.response.LogoutResponsePacket;
import code.christ.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by christmad on 2019/11/5.
 * 服务端登出请求处理器
 */
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) {
        Channel channel = ctx.channel();
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        if (SessionUtil.getSession(channel) != null) {
            SessionUtil.unbindSession(ctx.channel());
            logoutResponsePacket.setSuccess(true);
        } else {
            logoutResponsePacket.setSuccess(false);
            logoutResponsePacket.setReason("登出失败：用户未登录");
        }
        ctx.writeAndFlush(logoutResponsePacket);
    }
}
