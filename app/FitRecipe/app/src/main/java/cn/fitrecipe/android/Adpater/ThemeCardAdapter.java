package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.ThemeActivity;
import cn.fitrecipe.android.entity.Theme;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class ThemeCardAdapter extends RecyclerView.Adapter<ThemeCardAdapter.ThemeCardViewHolder>{

    private List<Theme> themeCardsList;
    private Context context;

    public ThemeCardAdapter(Context context, List<Theme> recipeCardsList) {
        this.context = context;
        this.themeCardsList = recipeCardsList;
    }

    @Override
    public ThemeCardAdapter.ThemeCardViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.fragment_index_theme_item, viewGroup, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ThemeActivity.class);
                intent.putExtra("theme", themeCardsList.get(i));
                context.startActivity(intent);
            }
        });

        return new ThemeCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ThemeCardAdapter.ThemeCardViewHolder contactViewHolder, int i) {
        Theme tc = themeCardsList.get(i);
//        contactViewHolder.theme_background.setImageResource(tc.getRecipe_background());
//        System.out.println(contactViewHolder.theme_background.toString() + "   :   " + tc.getThumbnail());
        FrApplication.getInstance().getMyImageLoader().displayImage(contactViewHolder.theme_background, tc.getThumbnail());
    }

    @Override
    public int getItemCount() {
        if(themeCardsList == null)
            return 0;
        else
            return themeCardsList.size();
    }

    public static class ThemeCardViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout theme_background;

        public ThemeCardViewHolder(View itemView) {
            super(itemView);
            theme_background = (LinearLayout) itemView.findViewById(R.id.theme_image);
        }
    }
}
