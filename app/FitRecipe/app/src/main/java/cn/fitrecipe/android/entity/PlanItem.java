package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wk on 2015/8/26.
 */
@DatabaseTable(tableName = "fr_plan")
public class PlanItem implements Serializable{

    @DatabaseField(generatedId = true)
    private int id;
    private ItemType itemType;
    private List<Object> data;
//    private List<Recipe> recipes;
//    private List<Ingredient> ingredients;
    private List<Nutrition> tNutrition;
    private double tCalories;
    private int imageCover;

    public int getImageCover() {
        return imageCover;
    }

    public void setImageCover(int imageCover) {
        this.imageCover = imageCover;
    }

    public int size() {
        if(data != null)
            return data.size();
        return 0;
    }

    public double getProtein() {
        if(tNutrition != null) {
            return tNutrition.get(1).getAmount();
        }
        return 0;
    }

    public double getFat() {
        if(tNutrition != null) {
            return tNutrition.get(2).getAmount();
        }
        return 0;
    }

    public double getCarbohydrate() {
        if(tNutrition != null) {
            return tNutrition.get(3).getAmount();
        }
        return 0;
    }


    public void addContent(Object object) {

        if(tNutrition == null) {
            tNutrition = new ArrayList<>();
        }
        if(data == null)
            data = new ArrayList<>();

        if (object instanceof Recipe) {
            Recipe recipe = (Recipe)object;
            List<Nutrition> nutrition_set = recipe.getNutrition_set();
            if(nutrition_set != null) {
                for(int j = 0; j < nutrition_set.size(); j++) {
                    Nutrition nutrition = null;
                    if(tNutrition.size() < nutrition_set.size()) {
                        nutrition = new Nutrition();
                        nutrition.setName(nutrition_set.get(j).getName());
                        nutrition.setUnit(nutrition_set.get(j).getUnit());
                        nutrition.setAmount(nutrition_set.get(j).getAmount());
                        tNutrition.add(nutrition);
                    }
                    else {
                        nutrition = tNutrition.get(j);
                        nutrition.setAmount(nutrition.getAmount() + nutrition_set.get(j).getAmount() * recipe.getTotal_amount() / 100);
                        tNutrition.set(j, nutrition);
                    }

                }
            }
            tCalories += recipe.getCalories() * recipe.getTotal_amount() / 100;
        }else {
            //TODO @wk add nutrtion from component
            for(int i = 0; i < 10; i++) {
                Nutrition nutrition = new Nutrition();
                nutrition.setAmount(100);
                nutrition.setName("水");
                tNutrition.add(nutrition);
            }
        }
        data.add(object);
    }

    public void deleteContent(int i) {
        Object object = data.get(i);
        if (object instanceof Recipe &&  tNutrition != null) {
            Recipe recipe = (Recipe) object;
            List<Nutrition> nutrition_set = recipe.getNutrition_set();
            if (nutrition_set != null) {
                for (int j = 0; j < nutrition_set.size(); j++) {
                    Nutrition nutrition = tNutrition.get(j);
                    nutrition.setAmount(nutrition.getAmount() - nutrition_set.get(j).getAmount() * recipe.getTotal_amount() / 100);
                    tNutrition.set(j, nutrition);
                }
            }
            tCalories -= recipe.getCalories() * recipe.getTotal_amount() / 100;
        }
        if (object instanceof Component) {

        }
        data.remove(i);
    }


    public List<Nutrition> gettNutrition() {
        return tNutrition;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public double gettCalories() {
        return tCalories;
    }

    public void settCalories(double tCalories) {
        this.tCalories = tCalories;
    }

    public static PlanItem getAllItem(List<PlanItem> items) {
        PlanItem item = new PlanItem();
        for(int i = 0; i < items.size(); i++) {
            PlanItem tmp = items.get(i);
            for(int j = 0; j < tmp.size(); j++) {
                Object obj = tmp.getData().get(j);
                item.addContent(obj);
            }
        }
        return item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public enum ItemType {
        BREAKFAST(0, "早餐"), ADDMEAL_01(1, "加餐"), LUNCH(2, "午餐"), ADDMEAL_02(3, "加餐"), SUPPER(4, "晚餐"), ALL(5, "");
        private String value;
        private int index;
        private ItemType(int index, String value) {
            this.index = index;
            this.value = value;
        }

        public int index() {
            return index;
        }

        public String value() {
            return value;
        }
    }
}
