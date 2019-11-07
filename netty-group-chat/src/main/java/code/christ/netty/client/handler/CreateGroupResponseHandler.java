package code.christ.netty.client.handler;

import code.christ.netty.protocol.packet.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by christmad on 2019/11/5.
 * 客户端建群响应处理器
 */
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) {
        if (msg.isSuccess()) {
            System.out.println("创建群聊成功，群聊 id 为[" + msg.getGroupId() + "]，群成员有：" + msg.getUserNameList());
        } else {
            System.out.println("群聊创建失败，原因=[" + msg.getReason() + "]");
        }
    }
}
