package code.christ.netty.console;

import code.christ.netty.protocol.packet.request.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by christmad on 2019/11/4.
 */
public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner in, Channel channel) {
        channel.writeAndFlush(new LogoutRequestPacket());
    }
}
