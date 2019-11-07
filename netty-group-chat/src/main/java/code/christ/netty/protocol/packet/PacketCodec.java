package code.christ.netty.protocol.packet;

import code.christ.netty.protocol.Command;
import code.christ.netty.protocol.packet.request.*;
import code.christ.netty.protocol.packet.response.*;
import code.christ.netty.protocol.serializer.JSONSerializer;
import code.christ.netty.protocol.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by christmad on 2019/10/26.
 * 协议编解码器
 */
public class PacketCodec {
    public static final PacketCodec INSTANCE = new PacketCodec();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;
    // 如果两个 map 是 static 的，注意它们 和 INSTANCE 的实例化顺序, 否则在类初始化阶段会因 map 没有初始化报 NPE


    // 私有构造器并提供一个单例 INSTANCE
    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        // 登录登出 type map
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);

        //单聊 type map
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

        // 群聊相关 type map
        packetTypeMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(Command.GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);

        // 空闲检测心跳 type map
        packetTypeMap.put(Command.HEART_BEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(Command.HEART_BEAT_RESPONSE, HeartBeatResponsePacket.class);

        Serializer serializer = new JSONSerializer();
        serializerMap = new HashMap<>();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    /**
     * 将 java 对象编码后写入 ByteBuf 对象中, 然后发送到网络
     * 默认使用 packet 中定义的序列化算法，这里的默认是 fastjson
     */
    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
        return encode(byteBufAllocator.ioBuffer(), packet);
    }

    /**
     * 将 java 对象 packet 编码为 ByteBuf 对象
     */
    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // 1. 创建 ByteBuf 对象——改为可以外部传入
//        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        // 2. 序列化 java对象, 稍后填充到数据内容部分
        byte[] content = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(Packet.MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(packet.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(content.length);
        byteBuf.writeBytes(content);

        return byteBuf;
    }



    /**
     * 将从网络收到的 ByteBuf 对象解码为 java 对象, 解码过程为编码过程的逆过程, 根据协议规定的字段一步步处理
     */
    public Packet decode(ByteBuf buffer) {
        // 跳过 magic number
        buffer.skipBytes(4);
        // 跳过版本号
        buffer.skipBytes(1);
        // 序列化算法
        byte serializerAlgorithm = buffer.readByte();
        // 指令
        byte command = buffer.readByte();
        // 数据长度
        int length = buffer.readInt();

        // 读出数据
        byte[] content = new byte[length];
        buffer.readBytes(content);

        // 反序列化——有两点是需要关注的
        // a.根据 command 字段获取 content 的具体 Packet 类型——为 Serializer#deserialize() 准备 clz 参数
        // b.根据 serializerAlgorithm 字段获取 content 序列化所使用的 Serializer, 用它来执行反序列化
        Class<? extends Packet> packetType = getPacketType(command);
        Serializer serializer = getSerializer(serializerAlgorithm);
        if (packetType != null && serializer != null) {
            return serializer.deserialize(packetType, content);
        }

        return null;
    }

    private Serializer getSerializer(byte serializerAlgorithm) {
        return serializerMap.get(serializerAlgorithm);
    }

    private Class<? extends Packet> getPacketType(byte command) {
        return packetTypeMap.get(command);
    }
}
