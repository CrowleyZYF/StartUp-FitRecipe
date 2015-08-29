package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wk on 2015/8/28.
 */
@DatabaseTable(tableName = "fr_basket")
public class BasketItem {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(uniqueCombo = true)
    private String type;
    @DatabaseField(uniqueCombo = true)
    private int itemId;


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
