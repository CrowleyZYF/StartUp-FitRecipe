package cn.fitrecipe.android.model;

import cn.fitrecipe.android.R;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class ThemeCard {
    protected String recipe_background;

    public ThemeCard(){
//        this.recipe_background= R.drawable.update_init;
    }

    public ThemeCard(String background){
        this.recipe_background= background;
    }

    public String getRecipe_background() {return this.recipe_background; }
}


