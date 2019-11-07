package code.christ.netty.console;

import code.christ.netty.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by christmad on 2019/11/4.
 */
public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> commandManager;

    public ConsoleCommandManager() {
        commandManager = new HashMap<>();
        commandManager.put("messageToUser", new MessageToUserConsoleCommand());
        commandManager.put("logout", new LogoutConsoleCommand());
        commandManager.put("createGroup", new CreateGroupConsoleCommand());
        commandManager.put("joinGroup", new JoinGroupConsoleCommand());
        commandManager.put("quitGroup", new QuitGroupConsoleCommand());
        commandManager.put("listGroupMembers", new ListGroupMembersConsoleCommand());
        commandManager.put("messageToGroup", new MessageToGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner in, Channel channel) {
        if (!SessionUtil.hasLogin(channel)) {
            return;
        }
        // 获取第一个指令
        String command = in.next();
        ConsoleCommand commandRunner = commandManager.get(command);
        if (commandRunner == null) {
            System.out.println("无法识别[" + command + "]指令，请重新输入");
        } else {
            commandRunner.exec(in, channel);
        }
    }
}
