package cn.fitrecipe.android.entity;

/**
 * Created by 奕峰 on 2015/8/28.
 */
public class PlanDetailItem {

    private int id;
    private int type;
    private String name;
    private int weight;
    private int calories;

    public PlanDetailItem(int id, int type, String name, int weight, int calories){
        this.id = id;
        this.type = type;
        this.name = name;
        this.weight = weight;
        this.calories = calories;
    }

    public int getId(){
        return this.id;
    }

    public int getType(){
        return this.type;
    }

    public String getName(){
        return this.name;
    }

    public String getWeight(){
        return this.weight+"g";
    }

    public String getCalories(){
        return this.calories+"kcal";
    }
}
