package code.christ.netty.protocol.packet;

import code.christ.netty.protocol.serializer.Serializer;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by christmad on 2019/10/26.
 * 协议数据包基础对象
 */
@Data
@NoArgsConstructor
public abstract class Packet {

    /**
     * 协议的魔数字段, 4字节
     */
    public static final int MAGIC_NUMBER = 0x95277259;

    /**
     * 协议的版本字段, 保留位, 默认 1
     */
    private byte version = 1;

    /**
     * 协议的序列化算法字段
     */
    private byte serializerAlgorithm = Serializer.DEFAULT.getSerializerAlgorithm();

    // Q：flash 在这里设计时不用字段而是用抽象方法, 这是经过实践才能想得到的吗？
    public abstract byte getCommand();

    private boolean success;
    private String reason;

    protected Packet(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }
}
