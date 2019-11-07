package code.christ.netty.client.handler;

import code.christ.netty.protocol.packet.response.LogoutResponsePacket;
import code.christ.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by christmad on 2019/11/5.
 * 客户端登出响应处理器
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) {
        if (msg.isSuccess()) {
            System.out.println("服务器已登出，清理session");
            SessionUtil.unbindSession(ctx.channel());
        } else {
            System.out.println("服务器登出失败，原因：" + msg.getReason());
        }
    }
}
