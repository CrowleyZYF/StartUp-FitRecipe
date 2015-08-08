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
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.KnowledgeArticleActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.Article;
import cn.fitrecipe.android.model.ArticleCard;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class ArticleCardAdapter extends RecyclerView.Adapter<ArticleCardAdapter.ArticleCardViewHolder>{

    private List<Article> ArticleCardsList;
    private Context context;

    public ArticleCardAdapter(Context context, List<Article> ArticleCardsList) {
        this.context = context;
        this.ArticleCardsList = ArticleCardsList;
    }

    @Override
    public ArticleCardAdapter.ArticleCardViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.activity_knowledge_articles_list_item, viewGroup, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("article", ArticleCardsList.get(i));
                context.startActivity(intent);
            }
        });

        return new ArticleCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleCardViewHolder contactViewHolder, int i) {
        Article ac = ArticleCardsList.get(i);
        contactViewHolder.article_name.setText(ac.getTitle());
        contactViewHolder.article_time.setText(ac.getCreated_time());
        contactViewHolder.article_read.setText("0");
        FrApplication.getInstance().getMyImageLoader().displayImage(contactViewHolder.article_pic, ac.getImg_cover());
    }

    @Override
    public int getItemCount() {
        return ArticleCardsList.size();
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
