package cn.fitrecipe.android.entity;

import java.io.Serializable;

import cn.fitrecipe.android.Http.FrServerConfig;

/**
 * Created by wk on 2015/8/6.
 */
public class Procedure implements Serializable{
    private String content;
    private String num;
    private String img;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getImg() {
        return FrServerConfig.getImageCompressed(img);
    }

    public void setImg(String img) {
        this.img = img;
    }
}
