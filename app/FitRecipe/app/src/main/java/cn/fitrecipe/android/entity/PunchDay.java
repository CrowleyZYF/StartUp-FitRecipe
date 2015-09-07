package cn.fitrecipe.android.entity;

import java.util.List;

/**
 * Created by 奕峰 on 2015/9/4.
 */
public class PunchDay {
    private String date;
    private List<PunchItem> itemLists;

    public PunchDay(String date, List<PunchItem> itemLists){
        this.date = date;
        this.itemLists = itemLists;
    }

    public String getDate(){
        return this.date;
    }

    public List<PunchItem> getItemLists(){
        return this.itemLists;
    }

}
