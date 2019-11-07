package code.christ.netty.protocol.packet.request;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;
import lombok.Data;

/**
 * Created by christmad on 2019/11/4.
 */
@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }
}
