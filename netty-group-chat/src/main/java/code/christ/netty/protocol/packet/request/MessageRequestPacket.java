package code.christ.netty.protocol.packet.request;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by christmad on 2019/11/3.
 * 客户端单聊-发给服务端的数据包
 */
@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
