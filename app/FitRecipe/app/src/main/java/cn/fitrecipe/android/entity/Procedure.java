package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import cn.fitrecipe.android.Http.FrServerConfig;

/**
 * Created by wk on 2015/8/6.
 */
@DatabaseTable(tableName = "fr_procedure")
public class Procedure implements Serializable{
    @DatabaseField
    private String content;
    @DatabaseField
    private String num;
    @DatabaseField
    private String img;
    @DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
    private transient Recipe recipe;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getImg() {
        return FrServerConfig.getImageCompressed(img);
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
