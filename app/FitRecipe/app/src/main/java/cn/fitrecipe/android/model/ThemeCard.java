package cn.fitrecipe.android.model;

import cn.fitrecipe.android.R;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class ThemeCard {
    protected int theme_id;
    protected String theme_background;

    public ThemeCard(){
//        this.recipe_background= R.drawable.update_init;
        this.theme_id = 0;
        this.theme_background = "";
    }

    public ThemeCard(int id,String background){
        this.theme_id = id;
        this.theme_background = background;
    }

    public String getTheme_id(){return this.theme_id+"";}

    public String getTheme_background() {return this.theme_background; }
}


