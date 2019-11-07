package code.christ.netty.protocol.packet.response;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;
import lombok.Data;

import java.util.List;

/**
 * Created by christmad on 2019/11/5.
 */
@Data
public class CreateGroupResponsePacket extends Packet {

    private String groupId;
    private List<String> userNameList;

    @Override
    public byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
