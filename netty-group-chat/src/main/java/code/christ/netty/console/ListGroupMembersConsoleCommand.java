package code.christ.netty.console;

import code.christ.netty.protocol.packet.request.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by christmad on 2019/11/4.
 */
public class ListGroupMembersConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner in, Channel channel) {
        System.out.print("输入 groupId，获取成员列表：");
        String groupId = in.next();
        ListGroupMembersRequestPacket packet = new ListGroupMembersRequestPacket();
        packet.setGroupId(groupId);
        channel.writeAndFlush(packet);
    }
}
