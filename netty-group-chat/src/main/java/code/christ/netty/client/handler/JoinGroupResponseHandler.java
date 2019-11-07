package code.christ.netty.client.handler;

import code.christ.netty.protocol.packet.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by christmad on 2019/11/5.
 * 客户端加群响应处理器
 */
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) {
        if (msg.isSuccess()) {
            System.out.println("加入群聊[" + msg.getGroupId() + "] 成功！");
        } else {
            System.out.println("加入群聊[" + msg.getGroupId() + "] 失败！原因=[" + msg.getReason() + "]");
        }
    }
}
