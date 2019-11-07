package code.christ.netty.client.handler;

import code.christ.netty.protocol.packet.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * Created by christmad on 2019/11/4.
 * 客户端定时发心跳
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleHeartBeat(ctx);

        super.channelActive(ctx);
    }

    private void scheduleHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor().scheduleWithFixedDelay(
                () -> {
                    if (ctx.channel().isActive()) {
                        ctx.writeAndFlush(new HeartBeatRequestPacket());
                    }
                }, 0, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);

    }
}
