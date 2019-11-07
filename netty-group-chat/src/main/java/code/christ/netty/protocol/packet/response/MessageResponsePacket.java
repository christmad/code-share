package code.christ.netty.protocol.packet.response;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;
import lombok.Data;

/**
 * Created by christmad on 2019/11/3.
 * 客户端单聊-发给接收者的数据包
 */
@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;
    private String fromUserName;
    private String message;

    @Override
    public byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
