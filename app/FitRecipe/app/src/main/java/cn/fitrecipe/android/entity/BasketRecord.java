package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wk on 2015/10/15.
 */
@DatabaseTable(tableName = "fr_basket_record")
public class BasketRecord {

    @DatabaseField
    private String date;
    @DatabaseField
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
