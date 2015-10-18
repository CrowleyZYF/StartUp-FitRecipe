package cn.fitrecipe.android.entity;

import java.io.Serializable;

import cn.fitrecipe.android.Http.FrServerConfig;

/**
 * Created by wk on 2015/8/9.
 */
public class Theme implements Serializable{
    private int id;
    private String created_time;
    private String updated_time;
    private String title;
    private String content;
    private String img;
    private String thumbnail;
    private boolean has_collected;
    private int recipe_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return FrServerConfig.getImageCompressed(img);
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getThumbnail() {
        return FrServerConfig.getImageCompressed(thumbnail);
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getRecipe_count() {
        return recipe_count;
    }

    public void setRecipe_count(int recipe_count) {
        this.recipe_count = recipe_count;
    }

    public boolean isHas_collected() {
        return has_collected;
    }

    public void setHas_collected(boolean has_collected) {
        this.has_collected = has_collected;
    }
}
