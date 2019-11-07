package code.christ.netty.client.handler;

import code.christ.netty.protocol.packet.response.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by christmad on 2019/11/5.
 * 客户端查询群成员的响应处理器
 */
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket msg) {
        if (msg.isSuccess()) {
            System.out.println("群[" + msg.getGroupId() + "]中的包括：" + msg.getUserSessionList());
        } else {
            System.out.println("获取群[" + msg.getGroupId() + "]成员失败，原因=[" + msg.getReason() + "]");
        }
    }
}
