package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RecipeActivity;
import cn.fitrecipe.android.ThemeActivity;
import cn.fitrecipe.android.model.RecipeCard;
import cn.fitrecipe.android.model.ThemeCard;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class ThemeCardAdapter extends RecyclerView.Adapter<ThemeCardAdapter.ThemeCardViewHolder> implements View.OnClickListener {

    private List<ThemeCard> themeCardsList;
    private Context context;

    public ThemeCardAdapter(Context context, List<ThemeCard> recipeCardsList) {
        this.context = context;
        this.themeCardsList = recipeCardsList;
    }

    @Override
    public ThemeCardAdapter.ThemeCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.fragment_index_theme_item, viewGroup, false);

        itemView.setOnClickListener(this);

        return new ThemeCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ThemeCardAdapter.ThemeCardViewHolder contactViewHolder, int i) {
        ThemeCard tc = themeCardsList.get(i);
        contactViewHolder.theme_background.setImageResource(tc.getRecipe_background());
    }

    @Override
    public int getItemCount() {
        return themeCardsList.size();
    }

    @Override
    public void onClick(View v) {
        this.context.startActivity(new Intent(this.context, ThemeActivity.class));
    }

    public static class ThemeCardViewHolder extends RecyclerView.ViewHolder {
        protected ImageView theme_background;

        public ThemeCardViewHolder(View itemView) {
            super(itemView);
            theme_background = (ImageView) itemView.findViewById(R.id.theme_image);
        }
    }
}
