package cn.fitrecipe.android.entity;

import java.util.List;

import cn.fitrecipe.android.Http.FrServerConfig;

/**
 * Created by wk on 2015/8/6.
 */
public class Author {

    private String nick_name;
    private int id;
    private String avatar;
    private boolean is_official;
    private String phone;
    private String token;
    private boolean is_changed_nick;
    private List<External> externals;


    public boolean is_changed_nick() {
        return is_changed_nick;
    }

    public void setIs_changed_nick(boolean is_changed_nick) {
        this.is_changed_nick = is_changed_nick;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    public boolean is_official() {
        return is_official;
    }

    public void setIs_official(boolean is_official) {
        this.is_official = is_official;
    }

    public List<External> getExternals() {
        return externals;
    }

    public void setExternals(List<External> externals) {
        this.externals = externals;
    }
}
