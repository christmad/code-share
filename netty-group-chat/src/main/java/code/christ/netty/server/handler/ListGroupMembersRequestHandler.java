package code.christ.netty.server.handler;

import code.christ.netty.protocol.packet.request.ListGroupMembersRequestPacket;
import code.christ.netty.protocol.packet.response.ListGroupMembersResponsePacket;
import code.christ.netty.session.Session;
import code.christ.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christmad on 2019/11/5.
 * 服务端查询群成员列表的请求处理器
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            ctx.writeAndFlush(new ListGroupMembersResponsePacket(false, "没有这个群聊 id[" + groupId + "]"));
            return;
        }
        List<Session> userSessionList = new ArrayList<>();
        for (Channel channel : channelGroup) {
            Session session = SessionUtil.getSession(channel);
            if (session != null) {
                userSessionList.add(session);
            }
        }
        ListGroupMembersResponsePacket packet = new ListGroupMembersResponsePacket();
        packet.setSuccess(true);
        packet.setGroupId(groupId);
        packet.setUserSessionList(userSessionList);

        ctx.writeAndFlush(packet);
    }
}
