package code.christ.netty.client.handler;

import code.christ.netty.protocol.packet.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by christmad on 2019/11/3.
 * 客户端单聊逻辑处理器，打印收到的消息
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        String fromUserId = msg.getFromUserId();
        String fromUserName = msg.getFromUserName();
        String message = msg.getMessage();
        System.out.println("客户端打印：fromUser[" + fromUserId + ":" + fromUserName + "] message=[" + message + "]");
    }
}
