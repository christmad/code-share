package code.christ.netty.server.handler;

import code.christ.netty.protocol.packet.request.CreateGroupRequestPacket;
import code.christ.netty.protocol.packet.response.CreateGroupResponsePacket;
import code.christ.netty.util.IDUtil;
import code.christ.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christmad on 2019/11/5.
 * 服务端创建群聊请求处理器
 */
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    private CreateGroupRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) {
        // 1 创建群 id，粗暴解决，random 一下
        String groupId = IDUtil.randomId();

        List<String> userIdList = msg.getUserIdList();
        List<String> userNameList = new ArrayList<>();
        // 2 创建 ChannelGroup，用它来群发消息和通知
        DefaultChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        // 3 创建建群的结果响应
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        if (channelGroup.isEmpty()) {
            createGroupResponsePacket.setSuccess(false);
            createGroupResponsePacket.setReason("用户id没有对应任何用户，创建群聊失败");
        } else {
            createGroupResponsePacket.setSuccess(true);
            createGroupResponsePacket.setGroupId(groupId);
            createGroupResponsePacket.setUserNameList(userNameList);
            System.out.println("群聊创建成功，id 为 " + groupId + "，群里面有：" + userNameList);
            // 4 保存群组的相关信息
            SessionUtil.bindChannelGroup(groupId, channelGroup);
        }

        // 5 给群成员发送拉群通知
        channelGroup.writeAndFlush(createGroupResponsePacket);

    }
}
