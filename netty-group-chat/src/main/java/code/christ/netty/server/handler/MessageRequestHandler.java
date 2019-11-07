package code.christ.netty.server.handler;

import code.christ.netty.protocol.packet.request.MessageRequestPacket;
import code.christ.netty.protocol.packet.response.MessageResponsePacket;
import code.christ.netty.session.Session;
import code.christ.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by christmad on 2019/11/3.
 * 服务端单聊逻辑处理器，负责接收客户端单聊请求，然后转发给指定接收者
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private MessageRequestHandler() {}

    /**
     * 单聊逻辑实现
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) {
        // 获取当前给服务器发消息的用户 session
        Session session = SessionUtil.getSession(ctx.channel());
        MessageResponsePacket response = new MessageResponsePacket();
        response.setFromUserId(session.getUserId());
        response.setFromUserName(session.getUserName());
        response.setMessage(msg.getMessage());

        // 获取要转发的用户所在的 channel
        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());
        // 服务器内部执行“转发”
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(response);
        } else {
            // 不登录不能聊天，粗暴解决
            System.out.println("用户ID[" + msg.getToUserId() + "] 不在线，发送失败！");
        }
    }
}
