package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.Recipe;

/**
 * Created by wk on 2015/8/24.
 */
public class SearchRecipeAdapter extends BaseAdapter{

    Context context;
    List<Recipe> data;

    public SearchRecipeAdapter(Context context, List<Recipe> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        }else {
            convertView = View.inflate(context, R.layout.search_recipe_list_item, null);
            holder = new ViewHolder();
            holder.textview1 = (TextView) convertView.findViewById(R.id.textview1);
            holder.textview2 = (TextView) convertView.findViewById(R.id.textview2);
            convertView.setTag(holder);
        }
        holder.textview1.setText(data.get(position).getTitle());
        holder.textview2.setText(data.get(position).getCalories()+"kcal");
        return convertView;
    }

    class ViewHolder {
        TextView textview1;
        TextView textview2;
    }
}
