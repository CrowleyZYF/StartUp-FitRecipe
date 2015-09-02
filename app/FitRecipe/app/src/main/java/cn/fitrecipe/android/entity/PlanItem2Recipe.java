package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wk on 2015/8/29.
 */
@DatabaseTable(tableName = "fr_planitem2recipe")
public class PlanItem2Recipe {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(foreign = true, uniqueCombo = true)
    private Recipe recipe;
    @DatabaseField(foreign = true, uniqueCombo = true)
    private PlanItem planItem;
    @DatabaseField
    private int recipeWeight;
    @DatabaseField
    private int itemIndex;

    public PlanItem getPlanItem() {
        return planItem;
    }

    public void setPlanItem(PlanItem planItem) {
        this.planItem = planItem;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getRecipeWeight() {
        return recipeWeight;
    }

    public void setRecipeWeight(int recipeWeight) {
        this.recipeWeight = recipeWeight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }
}
