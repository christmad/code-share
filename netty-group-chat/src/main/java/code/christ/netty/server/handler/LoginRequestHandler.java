package code.christ.netty.server.handler;

import code.christ.netty.protocol.packet.request.LoginRequestPacket;
import code.christ.netty.protocol.packet.response.LoginResponsePacket;
import code.christ.netty.session.Session;
import code.christ.netty.util.IDUtil;
import code.christ.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.Date;

/**
 * Created by christmad on 2019/11/3.
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private LoginRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        ctx.channel().writeAndFlush(login(msg, ctx.channel()));
    }

    private NioEventLoopGroup loginBizThreadPool = new NioEventLoopGroup();

    private Object login(LoginRequestPacket msg, Channel channel) throws Exception {
        // 通常校验用户登录信息是一个数据库或网络耗时操作，需要把它扔进业务线程池去处理，以避免阻塞 netty 的 NIO 线程导致与该线程绑定的其他 channel 也被阻塞
        return loginBizThreadPool.submit(() -> {
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setUserName(msg.getUsername());
            if (valid(msg)) {
                loginResponsePacket.setSuccess(true);
                // 粗暴处理，服务器随机派 ID. 实际上 checkUser() 是一个数据库或网络操作，需要返回真实的用户信息
                String userId = checkUser();
                loginResponsePacket.setUserId(userId);
                SessionUtil.bindSession(new Session(userId, msg.getUsername()), channel);
                System.out.println("服务端打印：" + new Date() + "：[" + msg.getUsername() + "]登录【成功】");
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("登录校验失败");
                System.out.println("服务端打印：" + new Date() + "：[" + msg.getUsername() + "]登录【失败】");
            }
            return loginResponsePacket;
        }).get();
    }

    private String checkUser() {
        return IDUtil.randomId();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unbindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket msg) {
        return true;
    }
}
