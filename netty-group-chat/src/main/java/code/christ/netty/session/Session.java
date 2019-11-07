package code.christ.netty.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by christmad on 2019/11/3.
 * 保存用户登录后的信息。这里只做简单记录。
 */
@Data
@NoArgsConstructor
public class Session {

    private String userId;
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userId + ":" + userName;
    }
}
