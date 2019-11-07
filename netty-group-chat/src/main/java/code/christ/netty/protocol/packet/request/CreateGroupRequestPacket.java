package code.christ.netty.protocol.packet.request;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;
import lombok.Data;

import java.util.List;

/**
 * Created by christmad on 2019/11/4.
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
