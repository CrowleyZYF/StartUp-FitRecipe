package cn.fitrecipe.android.entity;

import java.io.Serializable;

/**
 * Created by wk on 2015/8/8.
 */
public class Comment implements Serializable{

    private int id;
    private Author author;
    private Author reply;
    private String created_time;
    private String updated_time;
    private String content;
    private int recipe;

    public int getRecipe() {
        return recipe;
    }

    public void setRecipe(int recipe) {
        this.recipe = recipe;
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

    public Author getReply() {
        return reply;
    }

    public void setReply(Author reply) {
        this.reply = reply;
    }

    public String getContent() {
        String str = content;
        if(reply != null)
            str =  "回复 " + reply.getNick_name() + " : " + content;
        return str;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
