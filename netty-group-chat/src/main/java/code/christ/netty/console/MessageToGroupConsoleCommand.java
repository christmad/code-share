package code.christ.netty.console;

import code.christ.netty.protocol.packet.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by christmad on 2019/11/4.
 * 客户端群聊消息的控制台命令处理器
 */
public class MessageToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner in, Channel channel) {
        System.out.print("发送消息给某个群组：");
        String groupId = in.next();
        String message = in.next();

        channel.writeAndFlush(new GroupMessageRequestPacket(groupId, message));
    }
}
