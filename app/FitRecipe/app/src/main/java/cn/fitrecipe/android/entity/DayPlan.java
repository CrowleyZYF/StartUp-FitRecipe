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
public class DayPlan implements Serializable{

    @DatabaseField(generatedId = true)
    private int id;
    @ForeignCollectionField
    private Collection<PlanItem> planItems;
    @DatabaseField
    private int index;
    @DatabaseField
    private boolean isPunched;
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
        this.planItems = planItems;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
}
