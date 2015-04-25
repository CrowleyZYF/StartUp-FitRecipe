package cn.fitrecipe.android.Adpater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.model.RecipeCard;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.RecipeCardViewHolder> {

    private List<RecipeCard> recipeCardsList;

    public RecipeCardAdapter(List<RecipeCard> recipeCardsList) {
        this.recipeCardsList = recipeCardsList;
    }

    @Override
    public RecipeCardAdapter.RecipeCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.recipe_card, viewGroup, false);

        return new RecipeCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeCardAdapter.RecipeCardViewHolder contactViewHolder, int i) {
        RecipeCard rc = recipeCardsList.get(i);
        contactViewHolder.recipe_name.setText(rc.getRecipe_name());
        contactViewHolder.recipe_function.setText(rc.getRecipe_function());
        contactViewHolder.recipe_calorie.setText(rc.getRecipe_calorie());
        contactViewHolder.recipe_time.setText(rc.getRecipe_time());
        contactViewHolder.recipe_like.setText(rc.getRecipe_like());
    }

    @Override
    public int getItemCount() {
        return recipeCardsList.size();
    }

    public static class RecipeCardViewHolder extends RecyclerView.ViewHolder {
        protected TextView recipe_name;
        protected TextView recipe_function;
        protected TextView recipe_time;
        protected TextView recipe_calorie;
        protected TextView recipe_like;

        public RecipeCardViewHolder(View itemView) {
            super(itemView);
            recipe_name =  (TextView) itemView.findViewById(R.id.recipe_name);
            recipe_function = (TextView)  itemView.findViewById(R.id.recipe_function);
            recipe_time = (TextView)  itemView.findViewById(R.id.recipe_time);
            recipe_calorie = (TextView) itemView.findViewById(R.id.recipe_calorie);
            recipe_like = (TextView) itemView.findViewById(R.id.recipe_like);
        }
    }
}
