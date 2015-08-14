package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.Comment;

/**
 * Created by wk on 2015/8/12.
 */
public class CommentAdapter extends BaseAdapter implements View.OnClickListener{

    private List<Comment> commentCardsList;
    private Context context;
    private int authorId;
    private View rootView;

    public CommentAdapter(Context context, List<Comment> commentCardsList, View rootView, int authorId) {
        this.context = context;
        this.commentCardsList = commentCardsList;
        this.authorId = authorId;
        this.rootView = rootView;
    }

    @Override
    public int getCount() {
        if(commentCardsList == null)
            return 0;
        else
            return commentCardsList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentCardsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentCardViewHolder holder;
        if(convertView != null)
            holder = (CommentCardViewHolder) convertView.getTag();
        else {
            convertView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.activity_comment_item, parent, false);
            holder = new CommentCardViewHolder(convertView);
            convertView.setTag(holder);
        }
        Comment c = commentCardsList.get(position);
        holder.comment_id.setText(""+c.getId());
        holder.comment_text.setText(c.getContent());
        holder.comment_time.setText(c.getCreated_time());
        holder.comment_user_id.setText("" + c.getAuthor().getId());
        holder.comment_user_name.setText("" + c.getAuthor().getNick_name());
        FrApplication.getInstance().getMyImageLoader().displayImage(holder.comment_user_avatar, c.getAuthor().getAvatar());
        String type = "";
        if(c.getAuthor().is_official()) {
            type = "（官方）";
            holder.comment_user_type.setText(type);
        }
        else
            if(c.getAuthor().getId() == authorId) {
                type = "（作者）";
                holder.comment_user_type.setText(type);
            }
            else
                holder.comment_user_type.setVisibility(View.GONE);
        convertView.setOnClickListener(this);
        return convertView;
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

    public static class CommentCardViewHolder {
        protected TextView comment_id;
        protected TextView comment_user_id;
        protected ImageView comment_user_avatar;
        protected TextView comment_user_name;
        protected TextView comment_user_type;
        protected TextView comment_time;
        protected TextView comment_text;

        public CommentCardViewHolder(View itemView) {
            comment_id = (TextView) itemView.findViewById(R.id.comment_id);
            comment_user_id = (TextView) itemView.findViewById(R.id.comment_user_id);
            comment_user_avatar = (ImageView) itemView.findViewById(R.id.comment_user_avatar);
            comment_user_name = (TextView) itemView.findViewById(R.id.comment_user_name);
            comment_user_type = (TextView) itemView.findViewById(R.id.comment_user_type);
            comment_time = (TextView) itemView.findViewById(R.id.comment_time);
            comment_text = (TextView) itemView.findViewById(R.id.comment_text);
        }
    }
}
