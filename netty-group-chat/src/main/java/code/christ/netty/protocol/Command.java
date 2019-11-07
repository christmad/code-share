package code.christ.netty.protocol;

/**
 * Created by christmad on 2019/10/26.
 * 协议中的命令字段
 * 在这里出现的命令类型，都要添加到 PacketCodec 的 packet type map 里面来实现服务端和客户端对二进制包编解码
 */
public interface Command {
    byte LOGIN_REQUEST = 1;
    byte LOGIN_RESPONSE = 2;

    byte MESSAGE_REQUEST = 3;
    byte MESSAGE_RESPONSE = 4;

    byte LOGOUT_REQUEST = 5;

    byte CREATE_GROUP_REQUEST = 6;
    byte JOIN_GROUP_REQUEST = 7;
    byte QUIT_GROUP_REQUEST = 8;
    byte LIST_GROUP_MEMBERS_REQUEST = 9;
    byte GROUP_MESSAGE_REQUEST = 10;

    byte HEART_BEAT_REQUEST = 11;
    byte HEART_BEAT_RESPONSE = 12;

    byte CREATE_GROUP_RESPONSE = 13;
    byte JOIN_GROUP_RESPONSE = 14;
    byte LOGOUT_RESPONSE = 15;
    byte LIST_GROUP_MEMBERS_RESPONSE = 16;
    byte QUIT_GROUP_RESPONSE = 17;
    byte GROUP_MESSAGE_RESPONSE = 18;
}
