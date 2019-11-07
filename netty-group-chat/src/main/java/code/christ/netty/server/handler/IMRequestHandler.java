package code.christ.netty.server.handler;

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
public class IMRequestHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMRequestHandler INSTANCE = new IMRequestHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> channelMap;

    private IMRequestHandler() {
        channelMap = new HashMap<>();
        // 将指令类型 和 request handler 做映射
        channelMap.put(Command.MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        channelMap.put(Command.LOGIN_REQUEST, LoginRequestHandler.INSTANCE);
        channelMap.put(Command.LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
        channelMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        channelMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        channelMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        channelMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        channelMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        SimpleChannelInboundHandler<? extends Packet> simpleChannelInboundHandler = channelMap.get(msg.getCommand());
        if (simpleChannelInboundHandler != null) {
            // 只关心能处理的类型
            simpleChannelInboundHandler.channelRead(ctx, msg);
        }
    }
}
