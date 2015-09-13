package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by wk on 2015/8/29.
 */
@DatabaseTable(tableName = "fr_dayplan")
public class DayPlan implements Serializable, Comparable<DayPlan>{

    @DatabaseField(generatedId = true)
    private int id;
    @ForeignCollectionField
    private Collection<PlanItem> planItems;
    @DatabaseField
    private boolean isInMyPlan;
    @DatabaseField
    private boolean isPunched;
    @DatabaseField
    private double calories;
    @DatabaseField
    private String date;
    @DatabaseField(foreign = true)
    private transient SeriesPlan plan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PlanItem> getPlanItems() {
        return (List<PlanItem>)planItems;
    }

    public void setPlanItems(List<PlanItem> planItems) {
        calories = 0;
        for(int i = 0; i < planItems.size(); i++)
            calories += planItems.get(i).gettCalories();
        this.planItems = planItems;
    }

    public boolean isPunched() {
        return isPunched;
    }

    public void setIsPunched(boolean isPunched) {
        this.isPunched = isPunched;
    }

    public SeriesPlan getPlan() {
        return plan;
    }

    public void setPlan(SeriesPlan plan) {
        this.plan = plan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    @Override
    public int compareTo(DayPlan another) {
        return this.date.compareTo(another.getDate());
    }

    public boolean isInMyPlan() {
        return isInMyPlan;
    }

    public void setIsInMyPlan(boolean isInMyPlan) {
        this.isInMyPlan = isInMyPlan;
    }
}
