package code.christ.netty.protocol.packet.response;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;
import lombok.Data;

/**
 * Created by christmad on 2019/10/27.
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;
    private String userName;

    @Override
    public byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
