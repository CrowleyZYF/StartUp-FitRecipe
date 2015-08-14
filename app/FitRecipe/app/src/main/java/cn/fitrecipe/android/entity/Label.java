package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wk on 2015/8/6.
 */
@DatabaseTable(tableName = "fr_label")
public class Label {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String type;
    @DatabaseField(canBeNull = true, foreign = true, columnName = "labels_id", foreignAutoRefresh = true)
    private Labels labels;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Labels getLabels() {
        return labels;
    }

    public void setLabels(Labels labels) {
        this.labels = labels;
    }
}
