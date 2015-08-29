package cn.fitrecipe.android.entity;

import java.util.List;

/**
 * Created by wk on 2015/8/7.
 */
public class PlanDetail {
    private int id;
    private int index_day;
    private int total_calories;
    private int breakfast_calories;
    private int breakfast_punch;
    private boolean breakfast_exist;
    private List<PlanDetailItem> breakfast_item;
    private int addmeal_01_calories;
    private int addmeal_01_punch;
    private boolean addmeal_01_exist;
    private List<PlanDetailItem> addmeal_01_item;
    private int lunch_calories;
    private int lunch_punch;
    private boolean lunch_exist;
    private List<PlanDetailItem> lunch_item;
    private int addmeal_02_calories;
    private int addmeal_02_punch;
    private boolean addmeal_02_exist;
    private List<PlanDetailItem> addmeal_02_item;
    private int supper_calories;
    private int supper_punch;
    private boolean supper_exist;
    private List<PlanDetailItem> supper_item;

    public PlanDetail(int id, int index_day, int total_calories,
                      int breakfast_calories, int breakfast_punch, List<PlanDetailItem> breakfast_item, boolean breakfast_exist,
                      int addmeal_01_calories, int addmeal_01_punch, List<PlanDetailItem> addmeal_01_item, boolean addmeal_01_exist,
                      int lunch_calories, int lunch_punch, List<PlanDetailItem> lunch_item, boolean lunch_exist,
                      int addmeal_02_calories, int addmeal_02_punch, List<PlanDetailItem> addmeal_02_item, boolean addmeal_02_exist,
                      int supper_calories, int supper_punch, List<PlanDetailItem> supper_item, boolean supper_exist
                      ){
        this.id = id;
        this.index_day = index_day;
        this.total_calories = total_calories;
        this.breakfast_calories = breakfast_calories;
        this.breakfast_punch = breakfast_punch;
        this.breakfast_exist = breakfast_exist;
        this.breakfast_item = breakfast_item;
        this.addmeal_01_calories = addmeal_01_calories;
        this.addmeal_01_punch = addmeal_01_punch;
        this.addmeal_01_exist = addmeal_01_exist;
        this.addmeal_01_item = addmeal_01_item;
        this.lunch_calories = lunch_calories;
        this.lunch_punch = lunch_punch;
        this.lunch_exist = lunch_exist;
        this.lunch_item = lunch_item;
        this.addmeal_02_calories = addmeal_02_calories;
        this.addmeal_02_punch = addmeal_02_punch;
        this.addmeal_02_exist = addmeal_02_exist;
        this.addmeal_02_item = addmeal_02_item;
        this.supper_calories = supper_calories;
        this.supper_punch = supper_punch;
        this.supper_exist = supper_exist;
        this.supper_item = supper_item;
    }

    public int getId(){
        return this.id;
    }

    public int getIndex_day(){ return this.index_day; }

    public int getTotal_calories() { return this.total_calories; }

    public int getBreakfast_calories() {return this.breakfast_calories; }

    public int getBreakfast_punch() {return this.breakfast_punch; }

    public boolean getBreakfast_exist() {return this.breakfast_exist;}

    public List<PlanDetailItem> getBreakfast_item(){return this.breakfast_item;}

    public int getAddmeal_01_calories() {return this.addmeal_01_calories; }

    public int getAddmeal_01_punch() {return this.addmeal_01_punch; }

    public boolean getAddmeal_01_exist() {return this.addmeal_01_exist;}

    public List<PlanDetailItem> getAddmeal_01_item(){return this.addmeal_01_item;}

    public int getLunch_calories() {return this.lunch_calories; }

    public int getLunch_punch() {return this.lunch_punch; }

    public boolean getLunch_exist() {return this.lunch_exist;}

    public List<PlanDetailItem> getLunch_item(){return this.lunch_item;}

    public int getAddmeal_02_calories() {return this.addmeal_02_calories; }

    public int getAddmeal_02_punch() {return this.addmeal_02_punch; }

    public boolean getAddmeal_02_exist() {return this.addmeal_02_exist;}

    public List<PlanDetailItem> getAddmeal_02_item(){return this.addmeal_02_item;}

    public int getSupper_calories() {return this.supper_calories; }

    public int getSupper_punch() {return this.supper_punch; }

    public boolean getSupper_exist() {return this.supper_exist;}

    public List<PlanDetailItem> getSupper_item(){return this.supper_item;}






}
