package code.christ.netty.server.handler;

import code.christ.netty.protocol.packet.request.JoinGroupRequestPacket;
import code.christ.netty.protocol.packet.response.JoinGroupResponsePacket;
import code.christ.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * Created by christmad on 2019/11/5.
 * 服务端加群请求处理器
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    private JoinGroupRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            // 使用 ctx.writeAndFlush() 缩短传播路径。需要 pipeline 中添加 MessageToMessageCodec
            ctx.writeAndFlush(new JoinGroupResponsePacket(false, "没有找到 groupId=[" + groupId + "]的群聊"));
            return;
        }
        // 当前用户所在 channel 加入 channelGroup
        channelGroup.add(ctx.channel());
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        joinGroupResponsePacket.setSuccess(true);
        joinGroupResponsePacket.setGroupId(groupId);
        // 仍然使用 ctx.writeAndFlush() 缩短传播路径
        ctx.writeAndFlush(joinGroupResponsePacket);
    }
}
