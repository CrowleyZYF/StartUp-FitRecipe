package cn.fitrecipe.android.entity;

import java.io.Serializable;

import cn.fitrecipe.android.Http.FrServerConfig;

/**
 * Created by wk on 2015/8/7.
 */
public class Article implements Serializable{
    private int id;
    private String img_cover;
    private String created_time;
    private String title;
    private String updated_time;
    private String recommend_img;
    private String content;
    private String wx_url;
    private int series;

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getRecommend_img() {
        return recommend_img;
    }

    public void setRecommend_img(String recommend_img) {
        this.recommend_img = recommend_img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWx_url() {
        return wx_url;
    }

    public void setWx_url(String wx_url) {
        this.wx_url = wx_url;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_cover() {
        return FrServerConfig.getImageCompressed(img_cover);
    }

    public void setImg_cover(String img_cover) {
        this.img_cover = img_cover;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
