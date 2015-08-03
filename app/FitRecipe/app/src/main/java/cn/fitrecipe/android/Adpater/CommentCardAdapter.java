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

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RecipeActivity;
import cn.fitrecipe.android.model.RecipeCard;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class CommentCardAdapter extends RecyclerView.Adapter<CommentCardAdapter.CommentCardViewHolder> implements View.OnClickListener {

    private List<RecipeCard> recipeCardsList;
    private Context context;


    public CommentCardAdapter(Context context, List<RecipeCard> recipeCardsList) {
        this.context = context;
        this.recipeCardsList = recipeCardsList;
    }

    @Override
    public CommentCardAdapter.CommentCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.framework_common_recipe_card, viewGroup, false);

        itemView.setOnClickListener(this);

        return new CommentCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentCardAdapter.CommentCardViewHolder contactViewHolder, int i) {
        /*RecipeCard rc = recipeCardsList.get(i);
        contactViewHolder.recipe_id.setText(rc.getRecipe_id());
        contactViewHolder.recipe_name.setText(rc.getRecipe_name());
        contactViewHolder.recipe_function.setText(rc.getRecipe_function());
        contactViewHolder.recipe_function_backup.setText(rc.getRecipe_function_backup());
        if(rc.getRecipe_function().equals("完美")){
            contactViewHolder.recipe_function.setBackground(context.getResources().getDrawable(R.drawable.perfect_background));
        }else if(rc.getRecipe_function().equals("高蛋白")){
            contactViewHolder.recipe_function.setBackground(context.getResources().getDrawable(R.drawable.hp_background));
        }else if(rc.getRecipe_function().equals("低脂")){
            contactViewHolder.recipe_function.setBackground(context.getResources().getDrawable(R.drawable.lf_background));
        }else if(rc.getRecipe_function().equals("低卡")){
            contactViewHolder.recipe_function.setBackground(context.getResources().getDrawable(R.drawable.lk_background));
        }
        if(!rc.getRecipe_function_backup().equals("")){
            contactViewHolder.recipe_function_backup.setVisibility(View.VISIBLE);
            if(rc.getRecipe_function_backup().equals("完美")){
                contactViewHolder.recipe_function_backup.setBackground(context.getResources().getDrawable(R.drawable.perfect_background));
            }else if(rc.getRecipe_function_backup().equals("高蛋白")){
                contactViewHolder.recipe_function_backup.setBackground(context.getResources().getDrawable(R.drawable.hp_background));
            }else if(rc.getRecipe_function_backup().equals("低脂")){
                contactViewHolder.recipe_function_backup.setBackground(context.getResources().getDrawable(R.drawable.lf_background));
            }else if(rc.getRecipe_function_backup().equals("低卡")){
                contactViewHolder.recipe_function_backup.setBackground(context.getResources().getDrawable(R.drawable.lk_background));
            }
        }else{
            contactViewHolder.recipe_function_backup.setVisibility(View.GONE);
        }
        contactViewHolder.recipe_calorie.setText(rc.getRecipe_calorie());
        contactViewHolder.recipe_time.setText(rc.getRecipe_time());
        contactViewHolder.recipe_like.setText(rc.getRecipe_like());
        contactViewHolder.recipe_background.setBackground (this.context.getResources().getDrawable(rc.getRecipe_background()));*/
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

    public static class CommentCardViewHolder extends RecyclerView.ViewHolder {
        protected ImageView comment_user_avatar;
        protected TextView comment_user_name;
        protected TextView comment_user_type;
        protected TextView comment_time;
        protected TextView comment_text;

        public CommentCardViewHolder(View itemView) {
            super(itemView);
            comment_user_avatar =  (ImageView) itemView.findViewById(R.id.comment_user_avatar);
            comment_user_name = (TextView) itemView.findViewById(R.id.comment_user_name);
            comment_user_type = (TextView) itemView.findViewById(R.id.comment_user_type);
            comment_time = (TextView) itemView.findViewById(R.id.comment_time);
            comment_text = (TextView) itemView.findViewById(R.id.comment_text);
        }
    }
}
