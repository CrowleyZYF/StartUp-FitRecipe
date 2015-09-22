package cn.fitrecipe.android.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.function.Common;

/**
 * Created by wk on 2015/9/14.
 */
@DatabaseTable(tableName = "fr_dateplan")
public class DatePlan implements Serializable, Comparable<DatePlan>{

    @DatabaseField(id = true)
    private String date;
    @DatabaseField
    private String items;
    @DatabaseField
    private boolean inBasket;
    @DatabaseField
    private String plan_name;

    public String getPlan_name() {
        return plan_name;
    }
    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DatePlanItem> getItems() {
        if(items == null)   return null;
        else {
            Gson gson = new Gson();
            List<DatePlanItem> items1 = gson.fromJson(items, new TypeToken<ArrayList<DatePlanItem>>() {
            }.getType());
            for(DatePlanItem item : items1)
                item.setDate(date);
            return items1;
        }
    }

    public void setItems(List<DatePlanItem> items) {
        if(items != null) {
            Gson gson = new Gson();
            this.items = gson.toJson(items);
        }
    }

    public double getTotalCalories() {
        List<DatePlanItem> list = getItems();
        double total = 0.0;
        for(int i = 0; i < list.size(); i++) {
            total += list.get(i).getCalories_take();
        }
        return total;
    }

    @Override
    public int compareTo(DatePlan another) {
        return Common.CompareDate(this.date, another.getDate());
    }

    public boolean isInBasket() {
        return inBasket;
    }

    public void setInBasket(boolean inBasket) {
        this.inBasket = inBasket;
    }
}
