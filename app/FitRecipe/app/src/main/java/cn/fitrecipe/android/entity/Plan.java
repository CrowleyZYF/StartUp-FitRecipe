package cn.fitrecipe.android.entity;

/**
 * Created by wk on 2015/8/7.
 */
public class Plan {
    private int id;
    private String name;
    private int hard_rank;
    private int delicious_rank;
    private int label;
    private int days;
    private int type;
    private int join;
    private int background;


    public Plan(int id, String name, int hard_rank, int delicious_rank, int label, int days, int type, int join, int background){
        this.id = id;
        this.name = name;
        this.hard_rank = hard_rank;
        this.delicious_rank = delicious_rank;
        this.label = label;
        this.days = days;
        this.type = type;
        this.join = join;
        this.background = background;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getHard_rank(){
        return this.hard_rank;
    }

    public int getDelicious_rank(){
        return this.delicious_rank;
    }

    public int getLabel(){
        return this.label;
    }

    public int getDays(){
        return this.days;
    }

    public int getType(){
        return this.type;
    }

    public int getJoin(){
        return this.join;
    }

    public int getBackground(){
        return this.background;
    }



}
