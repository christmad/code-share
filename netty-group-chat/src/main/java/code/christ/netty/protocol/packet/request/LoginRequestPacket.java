package code.christ.netty.protocol.packet.request;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;
import lombok.Data;

/**
 * Created by christmad on 2019/10/26.
 */
@Data
public class LoginRequestPacket extends Packet {

    private String username;
    private String password;

    @Override
    public byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
