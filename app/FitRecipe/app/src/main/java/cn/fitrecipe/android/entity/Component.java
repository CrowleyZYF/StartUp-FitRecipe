package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by wk on 2015/8/6.
 */
@DatabaseTable(tableName = "fr_component")
public class Component implements Serializable{

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
    private Ingredient ingredient;
    @DatabaseField
    private String amount;
    @DatabaseField
    private String remark;
    @DatabaseField(foreign = true, canBeNull = true)
    private Recipe recipe;

    public int getMAmount() {
        return Integer.parseInt(amount);
    }

    public void setMAmount(int amount) {
        this.amount = String.valueOf(amount);
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        if(remark == null || remark.length() == 0)
            remark = "--";
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
