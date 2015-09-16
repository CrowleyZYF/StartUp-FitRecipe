package cn.fitrecipe.android.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wk on 2015/9/16.
 */
@DatabaseTable(tableName = "fr_basket")
public class Basket {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String content;


    public List<PlanComponent> getContent() {
        if(content == null)
            return null;
        else {
            Gson gson = new Gson();
            return gson.fromJson(content, new TypeToken<ArrayList<PlanComponent>>() {
            }.getType());
        }
    }

    public void setContent(List<PlanComponent> content) {
        if(content != null) {
            Gson gson = new Gson();
            this.content = gson.toJson(content);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
