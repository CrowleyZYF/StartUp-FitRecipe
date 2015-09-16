package cn.fitrecipe.android.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wk on 2015/9/1.
 */
public class SeriesPlan implements Serializable, Comparable<SeriesPlan> {

    private int id;
    private String name;
    private int hard_rank;
    private int delicious_rank;
    private int label;
    private int days;
    private int type;
    private int join;
    private String desc;
    private String intro;
    private String background;
    private String author_name;
    private int author_type;
    private String author_avatar;
    private String author_title;
    private int author_years;
    private int author_fatratio;
    private String author_intro;
    private boolean isUsed;
    private List<DatePlan> datePlans;

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

    @Override
    public int compareTo(SeriesPlan another) {
        return id - another.getId();
    }

    public List<DatePlan> getDatePlans() {
        return datePlans;
    }

    public void setDatePlans(List<DatePlan> datePlans) {
        this.datePlans = datePlans;
    }
}
