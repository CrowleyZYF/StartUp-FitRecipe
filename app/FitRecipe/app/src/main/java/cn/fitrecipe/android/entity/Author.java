package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

import cn.fitrecipe.android.Http.FrServerConfig;

/**
 * Created by wk on 2015/8/6.
 */
@DatabaseTable(tableName = "fr_author")
public class Author implements Serializable{

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String nick_name;
    @DatabaseField
    private String avatar;
    @DatabaseField
    private boolean is_official;
    @DatabaseField
    private String phone;
    @DatabaseField
    private String token;
    @DatabaseField
    private boolean is_changed_nick;
    private List<External> externals;
    @DatabaseField
    private boolean isTested;
    @DatabaseField
    private boolean isLogin;

    public boolean getIsTested() {
        return isTested;
    }

    public void setIsTested(boolean isTested) {
        this.isTested = isTested;
    }

    public boolean getIs_changed_nick() {
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

    public boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
