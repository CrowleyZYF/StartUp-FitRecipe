package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wk on 2015/8/26.
 */
@DatabaseTable(tableName = "fr_planitem")
public class PlanItem implements Serializable{

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(useGetSet = true)
    private int type;
    @DatabaseField
    private String imageCover;
    @DatabaseField(foreign = true)
    private transient DayPlan dayPlan;
    @ForeignCollectionField
    private transient Collection<Component> components;
    @DatabaseField
    private boolean isPunched;
    @DatabaseField
    private boolean inBasket;

    private ItemType itemType;
    private ArrayList<Object> data;
    private ArrayList<Nutrition> tNutrition;
    private double tCalories;

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
                        nutrition.setAmount(nutrition_set.get(j).getAmount() * recipe.getIncreWeight() / 100);
                        tNutrition.add(nutrition);
                    }
                    else {
                        nutrition = tNutrition.get(j);
                        nutrition.setAmount(nutrition.getAmount() + nutrition_set.get(j).getAmount() * recipe.getIncreWeight()/ 100);
                        tNutrition.set(j, nutrition);
                    }

                }
            }
            tCalories += recipe.getCalories() * recipe.getIncreWeight() / 100;
        }else {
            //TODO @wk add nutrtion from component
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
                    nutrition.setAmount(nutrition.getAmount() - nutrition_set.get(j).getAmount() * recipe.getIncreWeight() / 100);
                    tNutrition.set(j, nutrition);
                }
            }
            tCalories -= recipe.getCalories() * recipe.getIncreWeight() / 100;
        }
        if (object instanceof Component) {

        }
        data.remove(i);
    }


    public ArrayList<Nutrition> gettNutrition() {
        return tNutrition;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
        type = this.itemType.index();
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(ArrayList<Object> data) {
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

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public DayPlan getDayPlan() {
        return dayPlan;
    }

    public void setDayPlan(DayPlan dayPlan) {
        this.dayPlan = dayPlan;
    }

    public Collection<Component> getComponents() {
        return components;
    }

    public void setComponents(Collection<Component> components) {
        this.components = components;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        switch (type) {
            case 0:
                itemType = ItemType.BREAKFAST; break;
            case 1:
                itemType = ItemType.ADDMEAL_01; break;
            case 2:
                itemType = ItemType.LUNCH;  break;
            case 3:
                itemType = ItemType.ADDMEAL_02; break;
            case 4:
                itemType = ItemType.SUPPER; break;
        }
    }

    public boolean isPunched() {
        return isPunched;
    }

    public void setIsPunched(boolean isPunched) {
        this.isPunched = isPunched;
    }

    public boolean isInBasket() {
        return inBasket;
    }

    public void setInBasket(boolean inBasket) {
        this.inBasket = inBasket;
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
