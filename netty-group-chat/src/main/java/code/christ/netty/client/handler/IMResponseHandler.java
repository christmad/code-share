package code.christ.netty.client.handler;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by christmad on 2019/11/5.
 */
@ChannelHandler.Sharable
public class IMResponseHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMResponseHandler INSTANCE = new IMResponseHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> channelMap;

    private IMResponseHandler() {
        channelMap = new HashMap<>();
        // 用指令类型将 request handler 一一对应起来
        channelMap.put(Command.MESSAGE_RESPONSE, new MessageResponseHandler());
        channelMap.put(Command.LOGOUT_RESPONSE, new LogoutResponseHandler());
        channelMap.put(Command.CREATE_GROUP_RESPONSE, new CreateGroupResponseHandler());
        channelMap.put(Command.JOIN_GROUP_RESPONSE, new JoinGroupResponseHandler());
        channelMap.put(Command.LIST_GROUP_MEMBERS_RESPONSE, new ListGroupMembersResponseHandler());
        channelMap.put(Command.QUIT_GROUP_RESPONSE, new QuitGroupResponseHandler());
        channelMap.put(Command.GROUP_MESSAGE_RESPONSE, new GroupMessageResponseHandler());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        SimpleChannelInboundHandler<? extends Packet> simpleChannelInboundHandler = channelMap.get(msg.getCommand());
        if (simpleChannelInboundHandler != null) {
            // 只关心能处理的类型——比如服务端的心跳回复包不需要处理，就不会进入这里
            simpleChannelInboundHandler.channelRead(ctx, msg);
        }
    }

}
