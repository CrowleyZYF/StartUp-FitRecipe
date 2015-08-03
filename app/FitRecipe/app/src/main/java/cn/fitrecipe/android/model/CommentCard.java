package cn.fitrecipe.android.model;

import cn.fitrecipe.android.R;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class CommentCard {
    protected int comment_id;
    protected int comment_user_avatar;
    protected String comment_user_name;
    protected int comment_user_type;
    protected String comment_time;
    protected String comment_context;
    protected String comment_reply_to_user;

    public CommentCard(){
        this.comment_id = 0;
        this.comment_user_avatar = R.drawable.pic_header;
        this.comment_user_name = "健食记";
        this.comment_user_type = 0;
        this.comment_time = "1991-11-26 21:11";
        this.comment_context = "这个最好吃了，可惜她吃不到唉";
        this.comment_reply_to_user = "";
    }

    public CommentCard(int id, int avatar, String name, int type, String time, String context, String reply){
        this.comment_id = id;
        this.comment_user_avatar = avatar;
        this.comment_user_name = name;
        this.comment_user_type = type;
        this.comment_time = time;
        this.comment_context = context;
        this.comment_reply_to_user = reply;
    }

    public String getComment_id(){return this.comment_id+"";}

    public String getComment_user_name(){return this.comment_user_name;}

    public int getComment_user_type(){return this.comment_user_type;}

    public String getComment_time(){return this.comment_time;}

    public String getComment_context(){
        if(this.comment_reply_to_user.equals("")){
            return this.comment_context;
        }else{
            return "回复 " + this.comment_reply_to_user + " :" + this.comment_context;
        }
    }
}


