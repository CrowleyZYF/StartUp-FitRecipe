package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.Comment;
import cn.fitrecipe.android.model.CommentCard;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class CommentCardAdapter extends RecyclerView.Adapter<CommentCardAdapter.CommentCardViewHolder> implements View.OnClickListener {

    private List<Comment> commentCardsList;
    private Context context;
    private View rootView;


    public CommentCardAdapter(Context context, List<Comment> commentCardsList, View rootView) {
        this.context = context;
        this.commentCardsList = commentCardsList;
        this.rootView = rootView;
    }

    @Override
    public CommentCardAdapter.CommentCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.activity_comment_item, viewGroup, false);

        itemView.setOnClickListener(this);

        return new CommentCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentCardAdapter.CommentCardViewHolder contactViewHolder, int i) {
        Comment cc = commentCardsList.get(i);
        contactViewHolder.comment_id.setText(cc.getId()+"");
        contactViewHolder.comment_user_id.setText(cc.getAuthor().getId()+"");
        contactViewHolder.comment_user_name.setText(cc.getAuthor().getNick_name());
        contactViewHolder.comment_time.setText(cc.getCreated_time());
        if(!cc.getAuthor().is_official()){
            contactViewHolder.comment_user_type.setVisibility(View.VISIBLE);
            contactViewHolder.comment_user_type.setText("(官方) ");
        }else{
            contactViewHolder.comment_user_type.setVisibility(View.GONE);
        }
        //contactViewHolder.comment_user_avatar.setImageResource (this.context.getResources().getDrawable(cc.getComment_user_avatar()));
        FrApplication.getInstance().getMyImageLoader().displayImage(contactViewHolder.comment_user_avatar, cc.getAuthor().getAvatar());
        contactViewHolder.comment_text.setText(cc.getContent());
    }

    @Override
    public int getItemCount() {
        if(commentCardsList == null)
            return 0;
        else
            return commentCardsList.size();
    }

    @Override
    public void onClick(View v) {
        String user = ((TextView) v.findViewById(R.id.comment_user_name)).getText().toString();
        String prefix = "回复 " + user + " : ";
        EditText et = (EditText) this.rootView.findViewById(R.id.comment_editText);
        et.setHint(prefix);
        String replyUserID = ((TextView) v.findViewById(R.id.comment_user_id)).getText().toString();
        String replyCommentID = ((TextView) v.findViewById(R.id.comment_id)).getText().toString();
        ((TextView) this.rootView.findViewById(R.id.comment_reply_comment_id)).setText(replyCommentID);
        ((TextView) this.rootView.findViewById(R.id.comment_reply_user_id)).setText(replyUserID);
        this.rootView.findViewById(R.id.comment_cancel_reply).setVisibility(View.VISIBLE);
    }

    public static class CommentCardViewHolder extends RecyclerView.ViewHolder {
        protected TextView comment_id;
        protected TextView comment_user_id;
        protected ImageView comment_user_avatar;
        protected TextView comment_user_name;
        protected TextView comment_user_type;
        protected TextView comment_time;
        protected TextView comment_text;

        public CommentCardViewHolder(View itemView) {
            super(itemView);
            comment_id = (TextView) itemView.findViewById(R.id.comment_id);
            comment_user_id = (TextView) itemView.findViewById(R.id.comment_user_id);
            comment_user_avatar =  (ImageView) itemView.findViewById(R.id.comment_user_avatar);
            comment_user_name = (TextView) itemView.findViewById(R.id.comment_user_name);
            comment_user_type = (TextView) itemView.findViewById(R.id.comment_user_type);
            comment_time = (TextView) itemView.findViewById(R.id.comment_time);
            comment_text = (TextView) itemView.findViewById(R.id.comment_text);
        }
    }
}
