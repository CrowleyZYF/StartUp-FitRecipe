package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wk on 2015/9/1.
 */
@DatabaseTable(tableName = "fr_seriesplan")
public class SeriesPlan implements Serializable{

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
    @DatabaseField
    private int join;
    @DatabaseField
    private String desc;
    @DatabaseField
    private String intro;
    @DatabaseField
    private String background;
    @DatabaseField
    private String author_name;
    @DatabaseField
    private int author_type;
    @DatabaseField
    private String author_avatar;
    @DatabaseField
    private int author_years;
    @DatabaseField
    private int author_fatratio;
    @DatabaseField
    private String author_title;
    @DatabaseField
    private String author_intro;
    @DatabaseField
    private boolean isCustom;
    @DatabaseField(foreign = true)
    private Author author;
    @DatabaseField
    private boolean isUsed;
    @ForeignCollectionField
    private Collection<DayPlan> dayplans;

    public boolean isUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public int getAuthor_type() {
        return author_type;
    }

    public void setAuthor_type(int author_type) {
        this.author_type = author_type;
    }

    public String getAuthor_avatar() {
        return author_avatar;
    }

    public void setAuthor_avatar(String author_avatar) {
        this.author_avatar = author_avatar;
    }

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

    public ArrayList<DayPlan> getDayplans() {
        return (ArrayList<DayPlan>)dayplans;
    }

    public void setDayplans(ArrayList<DayPlan> dayplans) {
        this.dayplans = dayplans;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setIsCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    public int getAuthor_fatratio() {
        return author_fatratio;
    }

    public void setAuthor_fatratio(int author_fatratio) {
        this.author_fatratio = author_fatratio;
    }

    public int getAuthor_years() {
        return author_years;
    }

    public void setAuthor_years(int author_years) {
        this.author_years = author_years;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAuthor_title() {
        return author_title;
    }

    public void setAuthor_title(String author_title) {
        this.author_title = author_title;
    }
}
