package code.christ.netty.console;

import code.christ.netty.protocol.packet.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by christmad on 2019/11/4.
 */
public class MessageToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner in, Channel channel) {
        System.out.print("发送消息给某个用户：");
        String toUserId = in.next();
        String message = in.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
