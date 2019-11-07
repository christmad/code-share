package code.christ.netty.console;

import code.christ.netty.protocol.packet.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by christmad on 2019/11/4.
 */
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner in, Channel channel) {
        System.out.println("请输入用户名登录：");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        String userName = in.nextLine();
        // 粗暴处理，万能密码
        String pwd = "gebilaowang";
        loginRequestPacket.setUsername(userName);
        loginRequestPacket.setPassword(pwd);

        channel.writeAndFlush(loginRequestPacket);
        waitForLogin();
    }

    private void waitForLogin() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ignored) {
        }
    }
}
