package code.christ.netty.protocol.packet.response;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;
import code.christ.netty.session.Session;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by christmad on 2019/11/5.
 */
@Data
@NoArgsConstructor
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;
    private List<Session> userSessionList;

    public ListGroupMembersResponsePacket(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
