package code.christ.netty.protocol.packet.response;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;

/**
 * Created by christmad on 2019/11/5.
 */
public class HeartBeatResponsePacket extends Packet {
    @Override
    public byte getCommand() {
        return Command.HEART_BEAT_RESPONSE;
    }
}
