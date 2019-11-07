package code.christ.netty.protocol.packet.request;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by christmad on 2019/11/4.
 */
@Data
@NoArgsConstructor
public class GroupMessageRequestPacket extends Packet {

    private String groupId;
    private String message;

    public GroupMessageRequestPacket(String groupId, String message) {
        this.groupId = groupId;
        this.message = message;
    }

    @Override
    public byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
