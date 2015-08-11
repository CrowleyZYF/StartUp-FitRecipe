package cn.fitrecipe.android.entity;

/**
 * Created by wk on 2015/8/9.
 */
public class Recommend {

    private Recipe recipe;
    private Series series;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public int getRecommendtype() {
        return recommendtype;
    }

    public void setRecommendtype(int recommendtype) {
        this.recommendtype = recommendtype;
    }

    private int recommendtype;
}
