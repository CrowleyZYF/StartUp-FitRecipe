package cn.fitrecipe.android.entity;

/**
 * Created by 奕峰 on 2015/9/4.
 */
public class PunchItem {
    private int punch_item_photo;
    private int punch_item_days_index;
    private int punch_item_type;
    private int punch_item_calorie;
    private int punch_item_protein_ratio;
    private int punch_item_carbohydrate_ratio;
    private int punch_item_lipids_ratio;

    public PunchItem(int punch_item_photo, int punch_item_days_index, int punch_item_type, int punch_item_calorie, int punch_item_protein_ratio, int punch_item_carbohydrate_ratio, int punch_item_lipids_ratio){
        this.punch_item_photo = punch_item_photo;
        this.punch_item_days_index = punch_item_days_index;
        this.punch_item_type = punch_item_type;
        this.punch_item_calorie = punch_item_calorie;
        this.punch_item_protein_ratio = punch_item_protein_ratio;
        this.punch_item_carbohydrate_ratio = punch_item_carbohydrate_ratio;
        this.punch_item_lipids_ratio = punch_item_lipids_ratio;
    }

    public int getPunch_item_photo() { return this.punch_item_photo; }

    public int getPunch_item_days_index() { return this.punch_item_days_index; }

    public String getPunch_item_type() {
        switch (this.punch_item_type){
            case 0:
                return "早餐";
            case 1:
                return "上午加餐";
            case 2:
                return "午餐";
            case 3:
                return "下午加餐";
            case 4:
                return "晚餐";
            default:
                return "";
        }
    }

    public int getPunch_item_calorie() { return this.punch_item_calorie; }

    public String getPunch_item_protein_ratio() { return this.punch_item_protein_ratio+""; }

    public String getPunch_item_carbohydrate_ratio() { return this.punch_item_carbohydrate_ratio+""; }

    public String getPunch_item_lipids_ratio() { return this.punch_item_lipids_ratio+""; }
}
