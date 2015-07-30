package cn.fitrecipe.android.model;

import cn.fitrecipe.android.R;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class ThemeCard {
    protected int theme_id;
    protected String theme_background;
    protected String theme_info;  //json

    public ThemeCard(){
//        this.recipe_background= R.drawable.update_init;
        this.theme_id = 0;
        this.theme_background = "";
        this.theme_info = "";
    }

    public ThemeCard(int id,String background, String theme_info){
        this.theme_id = id;
        this.theme_background = background;
        this.theme_info = theme_info;
    }

    public String getTheme_id(){return this.theme_id+"";}

    public String getTheme_background() {return this.theme_background; }

    public String getTheme_info() {
        return this.theme_info;
    }
}


