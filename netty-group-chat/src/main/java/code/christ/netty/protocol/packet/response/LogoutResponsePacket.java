package code.christ.netty.protocol.packet.response;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;
import lombok.Data;

/**
 * Created by christmad on 2019/11/5.
 */
@Data
public class LogoutResponsePacket extends Packet {

    @Override
    public byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
