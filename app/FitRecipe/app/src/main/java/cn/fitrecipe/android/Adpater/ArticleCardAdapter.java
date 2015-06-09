package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.ArticleActivity;
import cn.fitrecipe.android.KnowledgeArticleActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.model.ArticleCard;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class ArticleCardAdapter extends RecyclerView.Adapter<ArticleCardAdapter.ArticleCardViewHolder> implements View.OnClickListener {

    private List<ArticleCard> ArticleCardsList;
    private Context context;

    public ArticleCardAdapter(Context context, List<ArticleCard> ArticleCardsList) {
        this.context = context;
        this.ArticleCardsList = ArticleCardsList;
    }

    @Override
    public ArticleCardAdapter.ArticleCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.activity_knowledge_articles_list_item, viewGroup, false);

        itemView.setOnClickListener(this);

        return new ArticleCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleCardViewHolder contactViewHolder, int i) {
        ArticleCard ac = ArticleCardsList.get(i);
        contactViewHolder.article_name.setText(ac.getArticle_name());
        contactViewHolder.article_time.setText(ac.getArticle_time());
        contactViewHolder.article_read.setText(ac.getArticle_read());
        contactViewHolder.article_pic.setImageResource(ac.getArticle_pic());
    }

    @Override
    public int getItemCount() {
        return ArticleCardsList.size();
    }

    @Override
    public void onClick(View v) {
        this.context.startActivity(new Intent(this.context, ArticleActivity.class));
    }

    public static class ArticleCardViewHolder extends RecyclerView.ViewHolder {
        protected TextView article_name;
        protected TextView article_time;
        protected TextView article_read;
        protected ImageView article_pic;

        public ArticleCardViewHolder(View itemView) {
            super(itemView);
            article_name =  (TextView) itemView.findViewById(R.id.article_name);
            article_time = (TextView)  itemView.findViewById(R.id.article_time);
            article_read = (TextView)  itemView.findViewById(R.id.article_read);
            article_pic = (ImageView) itemView.findViewById(R.id.article_pic);
        }
    }
}
