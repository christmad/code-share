package code.christ.netty.handler;

import code.christ.netty.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by christmad on 2019/11/2.
 * 拒绝非本协议连接
 */
public class IMProtocolSplitter extends LengthFieldBasedFrameDecoder {

    private static final int FIELD_OFFSET = 7;
    private static final int FIELD_LENGTH = 4;

    public IMProtocolSplitter() {
        super(Integer.MAX_VALUE, FIELD_OFFSET, FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 屏蔽非本协议的客户端————断开连接
        // 由于 ByteBuf 需要往后传递，因此这里的读取 API 只能用 getInt()
        // 不能用 readInt, read/write 方法会改变读/写指针
        if (in.getInt(in.readerIndex()) != Packet.MAGIC_NUMBER) {
            // 在此断开连接
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }

}
