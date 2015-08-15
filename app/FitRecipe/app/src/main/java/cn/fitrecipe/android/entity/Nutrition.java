package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wk on 2015/8/6.
 */
@DatabaseTable(tableName = "fr_nutrition")
public class Nutrition {

    @DatabaseField
    private String name;
    @DatabaseField
    private double amount;
    @DatabaseField
    private String unit;
    @DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
    private Recipe recipe;

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
