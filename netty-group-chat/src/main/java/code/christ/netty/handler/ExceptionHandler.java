package code.christ.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by christmad on 2019/11/3.
 */
@ChannelHandler.Sharable
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    public static final ExceptionHandler INSTANCE = new ExceptionHandler();

    private ExceptionHandler() {}

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("在连接 " + ctx.channel() + " 上捕获异常：" + cause.getMessage());
    }
}
