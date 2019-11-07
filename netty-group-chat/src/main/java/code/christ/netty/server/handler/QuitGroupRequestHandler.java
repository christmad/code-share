package code.christ.netty.server.handler;

import code.christ.netty.protocol.packet.request.QuitGroupRequestPacket;
import code.christ.netty.protocol.packet.response.QuitGroupResponsePacket;
import code.christ.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * Created by christmad on 2019/11/5.
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    private QuitGroupRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) {
        String groupId = msg.getGroupId();
        // 非法退群判断 1
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            ctx.writeAndFlush(new QuitGroupResponsePacket(false, "没有这个群聊 id[" + groupId + "]"));
            return;
        }

        // 非法退群判断 2
        boolean isMember = false;
        Channel current = ctx.channel();
        for (Channel channel : channelGroup) {
            if (channel == current) {
                isMember = true;
                break;
            }
        }
        if (!isMember) {
            ctx.writeAndFlush(new QuitGroupResponsePacket(false, "你不是这个群聊的成员 Id[" + groupId + "]"));
            return;
        }

        // 退群响应，清理服务器的群聊缓存，以及群的销毁逻辑
        channelGroup.remove(current);
        SessionUtil.helpClearChannelGroup(groupId, channelGroup);

        QuitGroupResponsePacket packet = new QuitGroupResponsePacket();
        packet.setSuccess(true);
        packet.setGroupId(groupId);

        ctx.writeAndFlush(packet);
    }
}
