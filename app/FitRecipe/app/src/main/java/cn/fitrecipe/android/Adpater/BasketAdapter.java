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
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.Recipe;

/**
 * Created by wk on 2015/8/28.
 */
public class BasketAdapter extends BaseAdapter {

    private Context context;
    private List<PlanComponent> components;

    public BasketAdapter(Context context, List<PlanComponent> components) {
        this.context = context;
        this.components = components;
    }

    @Override
    public int getCount() {
        if(components == null) return 0;
        return components.size();
    }

    @Override
    public Object getItem(int position) {
        return components.get(position);
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
        holder.textView.setText(components.get(position).getName());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!components.get(position).getName().equals("其它")) {
                    String recipeID = components.get(position).getId() + "";
                    Intent intent = new Intent(context, RecipeActivity.class);
                    intent.putExtra("id", recipeID);
                    context.startActivity(intent);
                }
            }
        });
        holder.listView.setAdapter(new IngredientAdapter(context, components.get(position).getComponents(), null));
        return convertView;
    }
    class ViewHolder {
        TextView textView;
        ImageView imageView;
        LinearLayoutForListView listView;
    }
}
