package code.christ.netty.protocol.packet.response;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by christmad on 2019/11/5.
 */
@Data
@NoArgsConstructor
public class JoinGroupResponsePacket extends Packet {

    private String groupId;

    public JoinGroupResponsePacket(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
