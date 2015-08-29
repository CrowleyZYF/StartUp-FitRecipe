package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RecipeActivity;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.entity.Recipe;

/**
 * Created by wk on 2015/8/28.
 */
public class BasketAdapter extends BaseAdapter {

    private Context context;
    private List<Recipe> recipes;

    public BasketAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public int getCount() {
        if(recipes == null) return 0;
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        }else {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.ingredient_recipe_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.recipe_title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.recipe_details);
            holder.listView = (LinearLayoutForListView) convertView.findViewById(R.id.ingredient_list);
            convertView.setTag(holder);
        }
        holder.textView.setText(recipes.get(position).getTitle());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeID = recipes.get(position).getId() + "";
                Intent intent = new Intent(context, RecipeActivity.class);
                intent.putExtra("id", recipeID);
                context.startActivity(intent);
            }
        });
        holder.listView.setAdapter(new IngredientAdapter(context, recipes.get(position).getComponent_set(), null));
        return convertView;
    }
    class ViewHolder {
        TextView textView;
        ImageView imageView;
        LinearLayoutForListView listView;
    }
}
