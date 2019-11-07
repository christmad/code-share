package code.christ.netty.client.handler;

import code.christ.netty.protocol.packet.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by christmad on 2019/11/5.
 * 客户端退群响应处理器
 */
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) {
        if (msg.isSuccess()) {
            System.out.println("退出群聊[" + msg.getGroupId() + "]成功");
        } else {
            System.out.println("退出群聊[" + msg.getGroupId() + "]失败，原因=[" + msg.getReason() + "]");
        }
    }
}
