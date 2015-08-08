package cn.fitrecipe.android.entity;

import cn.fitrecipe.android.Http.FrServerConfig;

/**
 * Created by wk on 2015/8/6.
 */
public class Author {

    private String nick_name;
    private int id;
    private String avatar;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return FrServerConfig.getAvatarCompressed(avatar);
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
