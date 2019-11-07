package code.christ.netty.handler;

import code.christ.netty.protocol.packet.Packet;
import code.christ.netty.protocol.packet.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * Created by christmad on 2019/11/5.
 * 终极编解码器，既能编码，又能解码。有了它就可以用 ctx.writeAndFlush() 来缩短事件传播路径
 */
@ChannelHandler.Sharable
public class UltimatePacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {
    public static final UltimatePacketCodecHandler INSTANCE = new UltimatePacketCodecHandler();

    private UltimatePacketCodecHandler() {}

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) {
        // 使用 channel 上的 ByteBuf alloc，方便 netty 管理内存
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        out.add(PacketCodec.INSTANCE.encode(byteBuf, packet));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) {
        out.add(PacketCodec.INSTANCE.decode(buf));
    }
}
