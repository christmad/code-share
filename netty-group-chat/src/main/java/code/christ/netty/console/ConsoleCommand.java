package code.christ.netty.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * Created by christmad on 2019/11/4.
 * 控制台指令执行器接口，第一级指令由控制台直接输入，第二级指令由对应的指令执行器（ConsoleCommand）识别
 */
public interface ConsoleCommand {
    void exec(Scanner in, Channel channel);
}
