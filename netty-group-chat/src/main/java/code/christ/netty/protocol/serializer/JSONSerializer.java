package code.christ.netty.protocol.serializer;

import com.alibaba.fastjson.JSON;

/**
 * Created by christmad on 2019/10/26.
 * 使用 fastjson 作为对象的二进制序列化算法
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clz, byte[] bytes) {
        return JSON.parseObject(bytes, clz);
    }
}
