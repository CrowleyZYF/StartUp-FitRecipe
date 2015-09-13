package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wk on 2015/9/8.
 */
@DatabaseTable(tableName = "fr_myplan")
public class MyPlan implements Comparable<MyPlan>{

    @DatabaseField(id = true)
    private String startDate;
    @DatabaseField
    private String endDate;
    @DatabaseField(foreign = true)
    private SeriesPlan plan;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public SeriesPlan getPlan() {
        return plan;
    }

    public void setPlan(SeriesPlan plan) {
        this.plan = plan;
    }

    @Override
    public int compareTo(MyPlan another) {
        return this.startDate.compareTo(another.getStartDate());
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
