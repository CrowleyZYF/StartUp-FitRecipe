package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by wk on 2015/8/12.
 */
@DatabaseTable(tableName = "fr_labels")
public class Labels {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String category;
    @ForeignCollectionField
    private  Collection<Label> many;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Collection<Label> getMany() {
        return many;
    }

    public void setMany(Collection<Label> many) {
        this.many = many;
    }
}
