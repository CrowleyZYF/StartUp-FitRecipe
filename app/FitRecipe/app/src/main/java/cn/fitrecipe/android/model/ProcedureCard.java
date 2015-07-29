package cn.fitrecipe.android.model;

import cn.fitrecipe.android.R;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class ProcedureCard {
    protected int procedure_num;
    protected String procedure_content;
    protected int procedure_img;

    public ProcedureCard(){
        this.procedure_num=0;
        this.procedure_content="健食记";
        this.procedure_img= R.drawable.loading_pic2;
    }

    public ProcedureCard(int num, String content, int img){
        this.procedure_num=num;
        this.procedure_content=content;
        this.procedure_img= img;
    }

    public String getProcedure_content(){
        return this.procedure_content;
    }

    public String getProcedure_num(){
        return this.procedure_num+"";
    }

    public int getProcedure_img() {return this.procedure_img; }
}


