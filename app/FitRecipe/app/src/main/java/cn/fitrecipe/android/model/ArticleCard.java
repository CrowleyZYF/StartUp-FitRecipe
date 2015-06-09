package cn.fitrecipe.android.model;

import cn.fitrecipe.android.R;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class ArticleCard {
    protected String article_name;
    protected String article_time;
    protected int article_read;
    protected int article_pic;

    public ArticleCard(){
        this.article_name="";
        this.article_time="";
        this.article_read=0;
        this.article_pic=0;
    }

    public ArticleCard(String name, String time, int article_read, int article_pic){
        this.article_name=name;
        this.article_time=time;
        this.article_read=article_read;
        this.article_pic=article_pic;
    }

    public String getArticle_name(){
        return this.article_name;
    }

    public String getArticle_time() { return this.article_time; }

    public String getArticle_read(){ return this.article_read+""; }

    public int getArticle_pic() { return this.article_pic; }
    
}


