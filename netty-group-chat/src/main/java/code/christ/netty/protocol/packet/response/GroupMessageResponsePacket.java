package code.christ.netty.protocol.packet.response;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.Packet;
import code.christ.netty.session.Session;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by christmad on 2019/11/5.
 */
@Data
@NoArgsConstructor
public class GroupMessageResponsePacket extends Packet {

    // 群聊可以有多个，用这个字段区别不同群聊的消息
    private String fromGroupId;
    // 消息的生产者信息，实际环境中可能有 头像、性别、签名 等等其他字段
    private Session fromUser;
    private String message;

    public GroupMessageResponsePacket(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
