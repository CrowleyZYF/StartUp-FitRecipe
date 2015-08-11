package cn.fitrecipe.android.entity;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

import cn.fitrecipe.android.Http.FrServerConfig;

/**
 * Created by wk on 2015/8/7.
 */
public class Series implements Serializable{

    private int id;
    private List<Article> article_set;
    private String created_time;
    private String updated_time;
    private String title;
    private String introduce;
    private String img_cover;
    private String recommend_img;
    private String author;
    private String author_avatar;
    private String author_type;
    private int article_type;
    private int total_read_count;

    public int getTotal_read_count() {
        return total_read_count;
    }

    public void setTotal_read_count(int total_read_count) {
        this.total_read_count = total_read_count;
    }

    public static Series fromJson(String json) {
        return new Gson().fromJson(json, Series.class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Article> getArticle_set() {
        return article_set;
    }

    public void setArticle_set(List<Article> article_set) {
        this.article_set = article_set;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getImg_cover() {
        return FrServerConfig.getImageCompressed(img_cover);
    }

    public void setImg_cover(String img_cover) {
        this.img_cover = img_cover;
    }

    public String getRecommend_img() {
        return FrServerConfig.getImageCompressed(recommend_img);
    }

    public void setRecommend_img(String recommend_img) {
        this.recommend_img = recommend_img;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_avatar() {
        return FrServerConfig.getAvatarCompressed(author_avatar);
    }

    public void setAuthor_avatar(String author_avatar) {
        this.author_avatar = author_avatar;
    }

    public String getAuthor_type() {
        return author_type;
    }

    public void setAuthor_type(String author_type) {
        this.author_type = author_type;
    }

    public int getArticle_type() {
        return article_type;
    }

    public void setArticle_type(int article_type) {
        this.article_type = article_type;
    }
}
