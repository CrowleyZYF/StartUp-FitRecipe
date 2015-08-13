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

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.KnowledgeArticleActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.Series;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class SeriesCardAdapter extends RecyclerView.Adapter<SeriesCardAdapter.SeriesCardViewHolder> {

    private List<Series> seriesCardsList;
    private Context context;

    public SeriesCardAdapter(Context context, List<Series> seriesCardsList) {
        this.context = context;
        this.seriesCardsList = seriesCardsList;
    }

    @Override
    public SeriesCardAdapter.SeriesCardViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.activity_knowledge_series_list_item, viewGroup, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KnowledgeArticleActivity.class);
                intent.putExtra("series", seriesCardsList.get(i));
                context.startActivity(intent);
            }
        });

        return new SeriesCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SeriesCardViewHolder contactViewHolder, int i) {
        Series sc = seriesCardsList.get(i);
        contactViewHolder.series_name.setText(sc.getTitle());
        contactViewHolder.series_author_name.setText(sc.getAuthor());
        contactViewHolder.series_type.setText(sc.getAuthor_type());
        contactViewHolder.series_read.setText(sc.getTotal_read_count()+"");
        contactViewHolder.series_follow.setText(sc.getCollection_count()+"");
        FrApplication.getInstance().getMyImageLoader().displayImage(contactViewHolder.series_background, sc.getImg_cover());
        FrApplication.getInstance().getMyImageLoader().displayImage(contactViewHolder.series_author_background, sc.getAuthor_avatar());
    }

    @Override
    public int getItemCount() {
        return seriesCardsList.size();
    }

    public static class SeriesCardViewHolder extends RecyclerView.ViewHolder {
        protected TextView series_name;
        protected TextView series_author_name;
        protected TextView series_read;
        protected TextView series_follow;
        protected TextView series_type;
        protected ImageView series_background;
        protected ImageView series_author_background;

        public SeriesCardViewHolder(View itemView) {
            super(itemView);
            series_name =  (TextView) itemView.findViewById(R.id.series_name);
            series_author_name = (TextView)  itemView.findViewById(R.id.series_author_name);
            series_read = (TextView)  itemView.findViewById(R.id.series_read_number);
            series_follow = (TextView) itemView.findViewById(R.id.follow_number);
            series_type = (TextView) itemView.findViewById(R.id.series_author_type);
            series_background = (ImageView) itemView.findViewById(R.id.series_background);
            series_author_background = (ImageView) itemView.findViewById(R.id.series_author_background);
        }
    }
}
