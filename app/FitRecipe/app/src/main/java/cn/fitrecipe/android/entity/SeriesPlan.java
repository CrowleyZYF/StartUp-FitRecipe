package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by wk on 2015/9/1.
 */
@DatabaseTable(tableName = "fr_seriesplan")
public class SeriesPlan {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private int hard_rank;
    @DatabaseField
    private int delicious_rank;
    @DatabaseField
    private int label;
    @DatabaseField
    private int days;
    @DatabaseField
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHard_rank() {
        return hard_rank;
    }

    public void setHard_rank(int hard_rank) {
        this.hard_rank = hard_rank;
    }

    public int getDelicious_rank() {
        return delicious_rank;
    }

    public void setDelicious_rank(int delicious_rank) {
        this.delicious_rank = delicious_rank;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getJoin() {
        return join;
    }

    public void setJoin(int join) {
        this.join = join;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Collection<DayPlan> getDayplans() {
        return dayplans;
    }

    public void setDayplans(Collection<DayPlan> dayplans) {
        this.dayplans = dayplans;
    }

    @DatabaseField

    private int join;
    private String background;
    @ForeignCollectionField
    private Collection<DayPlan> dayplans;
}
