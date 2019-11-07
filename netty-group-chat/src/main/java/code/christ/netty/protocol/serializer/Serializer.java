package code.christ.netty.protocol.serializer;


/**
 * Created by christmad on 2019/10/26.
 * 定义序列化接口
 */
public interface Serializer {
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法——我们默认只用一个 JSON序列化 实现
     */
    byte getSerializerAlgorithm();

    /**
     * java转换成二进制对象
     */
    byte[] serialize(Object object);

    /**
     * 二进制对象转换成java对象
     */
    <T> T deserialize(Class<T> clz, byte[] bytes);
}
