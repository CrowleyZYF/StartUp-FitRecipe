package cn.fitrecipe.android.model;

import cn.fitrecipe.android.R;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class SeriesCard {
    protected String series_name;
    protected String series_author_name;
    protected int series_author_type;
    protected int series_background;
    protected int series_author_background;
    protected int series_follow;
    protected int series_read;

    public SeriesCard(){
        this.series_name="";
        this.series_author_name="";
        this.series_author_type=0;
        this.series_follow=0;
        this.series_read=0;
        this.series_background= R.drawable.temp;
        this.series_author_background= R.drawable.pic_header;
    }

    public SeriesCard(String name, String author_name, int author_type, int series_follow, int series_read, int series_background, int series_author_background){
        this.series_name=name;
        this.series_author_name=author_name;
        this.series_author_type=author_type;
        this.series_follow=series_follow;
        this.series_read=series_read;
        this.series_background= series_background;
        this.series_author_background= series_author_background;
    }

    public String getSeries_name(){
        return this.series_name;
    }

    public String getSeries_author_name() { return this.series_author_name; }

    public String getSeries_author_type() {
        if(this.series_author_type==0){
            return "官方原创";
        }else if(this.series_author_type==1){
            return "签约作者";
        }else{
            return "国外翻译";
        }
    }

    public String getSeries_follow(){ return this.series_follow+""; }

    public String getSeries_read(){ return this.series_read+""; }

    public int getSeries_background() { return this.series_background; }

    public int getSeries_author_background() { return this.series_author_background; }
    
}


