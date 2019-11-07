package code.christ.netty.client.handler;

import code.christ.netty.protocol.packet.response.LoginResponsePacket;
import code.christ.netty.session.Session;
import code.christ.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by christmad on 2019/11/3.
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) {
        if (msg.isSuccess()) {
            String userId = msg.getUserId();
            String userName = msg.getUserName();
            System.out.println("客户端打印：[" + userName + "]登陆成功 userId 为 " + userId);

            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            System.out.println("客户端打印：登录失败 reason[" + msg.getReason() + "]");
        }
    }
}
