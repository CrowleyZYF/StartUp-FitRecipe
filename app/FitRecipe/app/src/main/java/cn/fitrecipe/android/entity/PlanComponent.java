package cn.fitrecipe.android.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wk on 2015/9/14.
 */
public class PlanComponent implements Serializable, Comparable<PlanComponent> {

    private int id;
    private int type; //ingredient or recipe
    private String name;
    private int amount;
    private double calories;        //100g
    private int status; //if drop from the basket
    private ArrayList<PlanComponent> components;
    private ArrayList<Nutrition> nutritions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public ArrayList<PlanComponent> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<PlanComponent> components) {
        this.components = components;
    }

    public ArrayList<Nutrition> getNutritions() {
        return nutritions;
    }

    public void setNutritions(ArrayList<Nutrition> nutritions) {
        this.nutritions = nutritions;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static PlanComponent getPlanComponentFromRecipe(Recipe recipe, int weight) {
        PlanComponent pcomponent = new PlanComponent();
        pcomponent.setAmount(weight);
        pcomponent.setName(recipe.getTitle());
        pcomponent.setType(1);
        pcomponent.setId(recipe.getId());
        for(int i = 0; i < recipe.getNutrition_set().size(); i++)
            recipe.getNutrition_set().get(i).setAmount(recipe.getNutrition_set().get(i).getAmount());
        ArrayList<PlanComponent> components = new ArrayList<>();
        for(int i = 0; i < recipe.getComponent_set().size(); i++) {
            PlanComponent component = new PlanComponent();
            component.setName(recipe.getComponent_set().get(i).getIngredient().getName());
            component.setType(0);
            component.setAmount(Math.round(recipe.getComponent_set().get(i).getAmount() * weight * 1.0f/ recipe.getTotal_amount()));
            component.setCalories(100);
            components.add(component);
        }
        pcomponent.setComponents(components);
        pcomponent.setNutritions(recipe.getNutrition_set());
        pcomponent.setCalories(recipe.getCalories());
        return  pcomponent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int compareTo(PlanComponent another) {
        return -this.getType() + another.getType();
    }
}
