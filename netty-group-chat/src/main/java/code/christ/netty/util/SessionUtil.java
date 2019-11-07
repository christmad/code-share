package code.christ.netty.util;

import code.christ.netty.protocol.Attributes;
import code.christ.netty.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by christmad on 2019/11/3.
 */
public class SessionUtil {

    // 保存用户登录后的 channel 映射
    private static final ConcurrentHashMap<String, Channel> userIdToChannelMap = new ConcurrentHashMap<>();
    // 群聊和群聊成员的 channel group 映射
    private static final ConcurrentHashMap<String, ChannelGroup> groupIdToChannelGroupMap = new ConcurrentHashMap<>();

    /**
     * 登录后保存 session 和 用户连接的映射
     */
    public static void bindSession(Session session, Channel channel) {
        userIdToChannelMap.put(session.getUserId(), channel);
        // 用 session 表示登录状态并保存到 channel 中
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 连接关闭后清除 session 和 用户连接映射
     */
    public static void unbindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdToChannelMap.remove(getSession(channel).getUserId());
        }
    }

    /**
     * 获取当前 channel 的用户session
     */
    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static boolean hasLogin(Channel channel) {
        return getSession(channel) != null;
    }

    /**
     * 获取指定用户ID的 channel
     */
    public static Channel getChannel(String userId) {
        return userIdToChannelMap.get(userId);
    }

    public static void bindChannelGroup(String groupId, DefaultChannelGroup channelGroup) {
        groupIdToChannelGroupMap.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdToChannelGroupMap.get(groupId);
    }

    public static void helpClearChannelGroup(String groupId, ChannelGroup channelGroup) {
        if (channelGroup.isEmpty()) {
            channelGroup.close();
            groupIdToChannelGroupMap.remove(groupId);
        }
    }
}
