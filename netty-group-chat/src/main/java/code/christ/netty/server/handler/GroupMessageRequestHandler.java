package code.christ.netty.server.handler;

import code.christ.netty.protocol.packet.request.GroupMessageRequestPacket;
import code.christ.netty.protocol.packet.response.GroupMessageResponsePacket;
import code.christ.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * Created by christmad on 2019/11/5.
 * 服务端群聊消息处理器，转发消息给群组中的所有人
 */
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    private GroupMessageRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            ctx.writeAndFlush(new GroupMessageResponsePacket(false, "没有这个群聊 id[" + groupId + "]"));
            return;
        }

        // 构建群聊消息响应
        GroupMessageResponsePacket packet = new GroupMessageResponsePacket();
        packet.setFromGroupId(groupId);
        packet.setFromUser(SessionUtil.getSession(ctx.channel()));
        packet.setMessage(msg.getMessage());

        channelGroup.writeAndFlush(packet);
    }
}
