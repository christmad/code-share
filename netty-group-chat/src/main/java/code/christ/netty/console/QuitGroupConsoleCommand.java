package code.christ.netty.console;

import code.christ.netty.protocol.packet.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by christmad on 2019/11/4.
 */
public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner in, Channel channel) {
        System.out.print("请输入 groupId，退出群聊：");
        String groupId = in.next();
        QuitGroupRequestPacket packet = new QuitGroupRequestPacket();
        packet.setGroupId(groupId);
        channel.writeAndFlush(packet);
    }
}
