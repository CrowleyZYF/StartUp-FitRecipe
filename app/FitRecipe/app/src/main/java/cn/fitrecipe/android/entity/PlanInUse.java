package cn.fitrecipe.android.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wk on 2015/9/14.
 */
@DatabaseTable(tableName = "fr_plan")
public class PlanInUse {
    @DatabaseField(id = true)
    private int isUsed;
    @DatabaseField
    private String name;
    @DatabaseField
    private int days;
    @DatabaseField
    private String dateplans;
    @DatabaseField
    private String startDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public ArrayList<DatePlan> getDateplans() {
        if(dateplans == null) {
            return null;
        }else {
            Gson gson = new Gson();
            return gson.fromJson(dateplans, new TypeToken<ArrayList<DatePlan>>(){}.getType());
        }
    }

    public void setDateplans(List<DatePlan> dateplans) {
        if(dateplans != null) {
            Gson gson = new Gson();
            this.dateplans = gson.toJson(dateplans);;
        }
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }
}
