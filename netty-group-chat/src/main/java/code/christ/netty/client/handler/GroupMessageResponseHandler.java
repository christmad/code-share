package code.christ.netty.client.handler;

import code.christ.netty.protocol.packet.response.GroupMessageResponsePacket;
import code.christ.netty.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by christmad on 2019/11/5.
 * 客户端群聊消息响应处理器
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg) {
        String fromGroupId = msg.getFromGroupId();
        Session fromUser = msg.getFromUser();
        String message = msg.getMessage();
        System.out.println("收到群[" + fromGroupId + "]中[" + fromUser + "]发来的消息：[" + message + "]");
    }
}
