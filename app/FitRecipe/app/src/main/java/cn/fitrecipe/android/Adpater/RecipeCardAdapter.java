package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.ImageLoader.MyImageLoader;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RecipeActivity;
import cn.fitrecipe.android.model.RecipeCard;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.RecipeCardViewHolder> implements View.OnClickListener {

    private List<RecipeCard> recipeCardsList;
    private Context context;


    public RecipeCardAdapter(Context context, List<RecipeCard> recipeCardsList) {
        this.context = context;
        this.recipeCardsList = recipeCardsList;
    }

    @Override
    public RecipeCardAdapter.RecipeCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.framework_common_recipe_card, viewGroup, false);

        itemView.setOnClickListener(this);

        return new RecipeCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeCardAdapter.RecipeCardViewHolder contactViewHolder, int i) {
        RecipeCard rc = recipeCardsList.get(i);
        contactViewHolder.recipe_id.setText(rc.getRecipe_id());
        contactViewHolder.recipe_name.setText(rc.getRecipe_name());
        contactViewHolder.recipe_function.setText(rc.getRecipe_function());
        if(rc.getRecipe_function().equals("完美")){
            contactViewHolder.recipe_function.setBackground(context.getResources().getDrawable(R.drawable.perfect_background));
        }else if(rc.getRecipe_function().equals("高蛋白")){
            contactViewHolder.recipe_function.setBackground(context.getResources().getDrawable(R.drawable.hp_background));
        }else if(rc.getRecipe_function().equals("低脂")){
            contactViewHolder.recipe_function.setBackground(context.getResources().getDrawable(R.drawable.lf_background));
        }else if(rc.getRecipe_function().equals("低卡")){
            contactViewHolder.recipe_function.setBackground(context.getResources().getDrawable(R.drawable.lk_background));
        }
        contactViewHolder.recipe_calorie.setText(rc.getRecipe_calorie());
        contactViewHolder.recipe_time.setText(rc.getRecipe_time());
        contactViewHolder.recipe_like.setText(rc.getRecipe_like());
//        contactViewHolder.recipe_background.setBackground (this.context.getResources().getDrawable(rc.getRecipe_background()));
        FrApplication.getInstance().getMyImageLoader().displayImage(contactViewHolder.recipe_background, rc.getRecipe_background());
    }

    @Override
    public int getItemCount() {
        if(recipeCardsList == null)
            return 0;
        else
            return recipeCardsList.size();
    }

    @Override
    public void onClick(View v) {
        String id = ((TextView) v.findViewById(R.id.recipe_id)).getText().toString();
        Intent intent=new Intent(context,RecipeActivity.class);
        intent.putExtra("id", id);
        //TODO
        context.startActivity(intent);
    }

    public static class RecipeCardViewHolder extends RecyclerView.ViewHolder {
        protected TextView recipe_name;
        protected TextView recipe_id;
        protected TextView recipe_function;
        protected TextView recipe_time;
        protected TextView recipe_calorie;
        protected TextView recipe_like;
        protected RelativeLayout recipe_background;

        public RecipeCardViewHolder(View itemView) {
            super(itemView);
            recipe_name =  (TextView) itemView.findViewById(R.id.recipe_name);
            recipe_id = (TextView) itemView.findViewById(R.id.recipe_id);
            recipe_function = (TextView)  itemView.findViewById(R.id.recipe_function);
            recipe_time = (TextView)  itemView.findViewById(R.id.recipe_time);
            recipe_calorie = (TextView) itemView.findViewById(R.id.recipe_calorie);
            recipe_like = (TextView) itemView.findViewById(R.id.recipe_like);
            recipe_background = (RelativeLayout) itemView.findViewById(R.id.recipe_background);
        }
    }
}
