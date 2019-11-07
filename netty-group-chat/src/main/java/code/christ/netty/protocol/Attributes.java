package code.christ.netty.protocol;

import code.christ.netty.session.Session;
import io.netty.util.AttributeKey;

/**
 * Created by christmad on 2019/10/31.
 * Channel#attr的键
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
