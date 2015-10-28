package cn.fitrecipe.android.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.R;

/**
 * Created by wk on 2015/9/14.
 */
public class DatePlanItem implements Serializable{

    private String type;
    private String time;
    private String date;
    private double calories_take;
    private double carbohydrate_take;
    private double protein_take;
    private double fat_take;
    private boolean isPunch;
    private int punchId;
    private boolean isInBasket;
    private String imageCover;
    private String defaultImageCover;
    private int punchNums;
    private ArrayList<PlanComponent> components;
    private int th;

    public ArrayList<Nutrition> getNutritions() {
        return nutritions;
    }

    public void setNutritions(ArrayList<Nutrition> nutritions) {
        this.nutritions = nutritions;
    }

    private ArrayList<Nutrition> nutritions;

    public boolean isPunch() {
        return isPunch;
    }

    public void setIsPunch(boolean isPunch) {
        this.isPunch = isPunch;
    }

    public boolean isInBasket() {
        return isInBasket;
    }

    public void setIsInBasket(boolean isInBasket) {
        this.isInBasket = isInBasket;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getCalories_take() {
        return calories_take;
    }

    public void setCalories_take(double calories_take) {
        this.calories_take = calories_take;
    }

    public double getCarbohydrate_take() {
        return carbohydrate_take;
    }

    public void setCarbohydrate_take(double carbohydrate_take) {
        this.carbohydrate_take = carbohydrate_take;
    }

    public double getProtein_take() {
        return protein_take;
    }

    public void setProtein_take(double protein_take) {
        this.protein_take = protein_take;
    }

    public double getFat_take() {

        return fat_take;
    }

    public void setFat_take(double fat_take) {
        this.fat_take = fat_take;
    }

    public ArrayList<PlanComponent> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<PlanComponent> components) {
        this.components = components;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public void addContent(PlanComponent obj) {
        if(nutritions == null) {
            nutritions = new ArrayList<>();
        }
        if(components == null)
            components = new ArrayList<>();

        List<Nutrition> nutrition_set = obj.getNutritions();
        if(nutrition_set != null) {
            for(int j = 0; j < nutrition_set.size(); j++) {
                Nutrition nutrition = null;
                if(nutritions.size() < nutrition_set.size()) {
                    nutrition = new Nutrition();
                    nutrition.setName(nutrition_set.get(j).getName());
                    nutrition.setUnit(nutrition_set.get(j).getUnit());
                    nutrition.setAmount(nutrition_set.get(j).getAmount()* obj.getAmount() / 100);
                    nutritions.add(nutrition);
                }
                else {
                    nutrition = nutritions.get(j);
                    nutrition.setAmount(nutrition.getAmount() + nutrition_set.get(j).getAmount() * obj.getAmount() / 100);
                    nutritions.set(j, nutrition);
                }

            }
        }
        calories_take += obj.getCalories()  * obj.getAmount() / 100;
        protein_take = nutritions.get(1).getAmount();
        fat_take = nutritions.get(2).getAmount();
        carbohydrate_take = nutritions.get(3).getAmount();
        components.add(obj);
    }

    public void deleteContent(int i) {
        PlanComponent component = components.get(i);
        List<Nutrition> nutrition_set = component.getNutritions();
        if (nutrition_set != null) {
            for (int j = 0; j < nutrition_set.size(); j++) {
                Nutrition nutrition = nutritions.get(j);
                nutrition.setAmount(nutrition.getAmount() - nutrition_set.get(j).getAmount() * component.getAmount() / 100);
                nutritions.set(j, nutrition);
            }
        }
        calories_take -= component.getCalories() * component.getAmount() / 100;
        protein_take = nutritions.get(1).getAmount();
        fat_take = nutritions.get(2).getAmount();
        carbohydrate_take = nutritions.get(3).getAmount();
        components.remove(i);
    }

    public int size() {
        if(components == null)  return 0;
        else                    return components.size();
    }

    public int getPunchNums() {
        return punchNums;
    }

    public void setPunchNums(int punchNums) {
        this.punchNums = punchNums;
    }

    public static DatePlanItem getAllItem(List<DatePlanItem> items) {
        DatePlanItem res = new DatePlanItem();
        if(items != null) {
            for (int i = 0; i < items.size(); i++) {
                List<PlanComponent> componentList = items.get(i).getComponents();
                if(componentList != null) {
                    for(int j = 0; j < componentList.size(); j++) {
                        res.addContent(componentList.get(j));
                    }
                }
            }
        }
        return res;
    }

    public String getDefaultImageCover() {
        return defaultImageCover;
    }

    public void setDefaultImageCover(String defaultImageCover) {
        this.defaultImageCover = defaultImageCover;
        if (type.equals("breakfast")){
            this.defaultImageCover = "drawable://" + R.drawable.breakfast;
        }
        if (type.equals("add_meal_01")){
            this.defaultImageCover = "drawable://" + R.drawable.add_meal_01;
        }
        if (type.equals("lunch")){
            this.defaultImageCover = "drawable://" + R.drawable.lunch;
        }
        if (type.equals("add_meal_02")){
            this.defaultImageCover = "drawable://" + R.drawable.add_meal_02;
        }
        if (type.equals("supper")){
            this.defaultImageCover = "drawable://" + R.drawable.dinner;
        }
        if (type.equals("add_meal_03")){
            this.defaultImageCover = "drawable://" + R.drawable.add_meal_03;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPunchId() {
        return punchId;
    }

    public void setPunchId(int punchId) {
        this.punchId = punchId;
    }

    public int getTh() {
        return th;
    }

    public void setTh(int th) {
        this.th = th;
    }
}
