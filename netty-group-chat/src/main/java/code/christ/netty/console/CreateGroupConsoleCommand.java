package code.christ.netty.console;

import code.christ.netty.protocol.packet.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by christmad on 2019/11/4.
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner in, Channel channel) {
        System.out.print("正在创建群聊, 请输入群聊成员 userId，以英文逗号隔开：");
        String ids = in.next();
        CreateGroupRequestPacket packet = new CreateGroupRequestPacket();
        packet.setUserIdList(Arrays.asList(ids.split(",")));
        channel.writeAndFlush(packet);
    }
}
