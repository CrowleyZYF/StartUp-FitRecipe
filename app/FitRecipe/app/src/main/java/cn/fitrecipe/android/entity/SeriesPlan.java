package cn.fitrecipe.android.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wk on 2015/9/1.
 */
public class SeriesPlan implements Serializable, Comparable<SeriesPlan> {

    private int id;
    private String title;
    private int difficulty;
    private int delicious;
    private int benifit;
    private int total_days;
    private int type;       //计划类型
    private int dish_headcount;
    private String desc;
    private String inrtoduce;
    private String img;
    private String created_time;
    private String updated_time;
    private boolean is_personal;
    private String user;
    private PlanAuthor author;
    private String authored_date;
    private boolean isUsed;
    private List<DatePlan> datePlans;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDelicious() {
        return delicious;
    }

    public void setDelicious(int delicious) {
        this.delicious = delicious;
    }

    public int getBenifit() {
        return benifit;
    }

    public void setBenifit(int benifit) {
        this.benifit = benifit;
    }

    public int getTotal_days() {
        return total_days;
    }

    public void setTotal_days(int total_days) {
        this.total_days = total_days;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDish_headcount() {
        return dish_headcount;
    }

    public void setDish_headcount(int dish_headcount) {
        this.dish_headcount = dish_headcount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInrtoduce() {
        return inrtoduce;
    }

    public void setInrtoduce(String inrtoduce) {
        this.inrtoduce = inrtoduce;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public boolean is_personal() {
        return is_personal;
    }

    public void setIs_personal(boolean is_personal) {
        this.is_personal = is_personal;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public PlanAuthor getAuthor() {
        return author;
    }

    public void setAuthor(PlanAuthor author) {
        this.author = author;
    }

    public String getAuthored_date() {
        return authored_date;
    }

    public void setAuthored_date(String authored_date) {
        this.authored_date = authored_date;
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

    public boolean isUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
