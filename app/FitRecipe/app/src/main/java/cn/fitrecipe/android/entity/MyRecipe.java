package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;
import java.util.List;

/**
 * Created by wk on 2015/8/14.
 */
@DatabaseTable(tableName = "fr_components")
public class MyRecipe {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @ForeignCollectionField
    private Collection<Component> components;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<Component> getComponents() {
        return components;
    }

    public void setComponents(Collection<Component> components) {
        this.components = components;
    }
}

