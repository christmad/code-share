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
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    public QuitGroupResponsePacket(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
