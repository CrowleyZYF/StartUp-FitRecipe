package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RecipeActivity;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.DayPlan;
import cn.fitrecipe.android.entity.PlanItem;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.entity.SeriesPlan;

/**
 * Created by 奕峰 on 2015/4/25.
 */
public class PlanDetailViewPagerAdapter extends PagerAdapter {
    private Context context;
    private SeriesPlan plan;
    private List<DayPlan> dataList;

    public PlanDetailViewPagerAdapter(Context context, SeriesPlan plan){
        this.context = context;
        this.plan = plan;
        this.dataList = plan.getDayplans();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        DayPlan dayPlan = dataList.get(position);
        View planDetailContainer = LayoutInflater.from(context).inflate(R.layout.plan_detail_list_item, null);

        LinearLayoutForListView listView = (LinearLayoutForListView) planDetailContainer.findViewById(R.id.dayplan_detail);
        PlanItemAdapter adapter = new PlanItemAdapter(dayPlan.getPlanItems());
        listView.setAdapter(adapter);
        container.addView(planDetailContainer);
        return planDetailContainer;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeViewAt(position);
    }


    @Override
    public int getCount() {
        return plan.getDays();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    class PlanItemAdapter extends BaseAdapter {

        private List<PlanItem> items;

        public PlanItemAdapter(List<PlanItem> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
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
                convertView = View.inflate(context, R.layout.activity_plan_choice_info_detail, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            holder.plan_item_title.setText(items.get(position).getItemType().value());
            holder.plan_item_calories.setText(Math.round(items.get(position).gettCalories()) + "");
            holder.plan_item_punch.setText("1000");
            ComponentAdapter componentAdapter = new ComponentAdapter(items.get(position).getData());
            holder.listView.setAdapter(componentAdapter);
            return convertView;
        }

        class ViewHolder {
            TextView plan_item_title;
            TextView plan_item_calories;
            TextView plan_item_punch;
            LinearLayoutForListView listView;
            public ViewHolder(View v) {
                 plan_item_title = (TextView) v.findViewById(R.id.plan_item_title);
                 plan_item_calories = (TextView) v.findViewById(R.id.plan_item_calories);
                 plan_item_punch = (TextView) v.findViewById(R.id.plan_item_punch);
                 listView = (LinearLayoutForListView) v.findViewById(R.id.plan_item_detail);
            }
        }

        class ComponentAdapter extends BaseAdapter {
            private List<Object> data;

            public ComponentAdapter(List<Object> data) {
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
                    convertView = View.inflate(context, R.layout.activity_plan_choice_info_detail_item, null);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                }
                if(data.get(position) instanceof Recipe) {
                    final Recipe recipe = (Recipe) data.get(position);
                    holder.plan_item_name.setText(recipe.getTitle());
                    holder.plan_item_weight.setText(recipe.getIncreWeight()+"g");
                    holder.plan_item_calories.setText(Math.round(recipe.getCalories() * recipe.getIncreWeight() / 100) +"kcal");
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String recipe_id = recipe.getId() +"";
                            Intent intent=new Intent(context, RecipeActivity.class);
                            intent.putExtra("id", recipe_id);
                            context.startActivity(intent);
                        }
                    });
                }else {
                    Component component = (Component) data.get(position);
                    holder.plan_item_name.setText(component.getIngredient().getName());
                    holder.plan_item_weight.setText(component.getAmount()+"g");
                    holder.plan_item_calories.setText(100 +"kcal");
                }
                return convertView;
            }

            class ViewHolder {
                TextView plan_item_name;
                TextView plan_item_weight;
                TextView plan_item_calories;

                public ViewHolder(View v) {
                    plan_item_name = (TextView) v.findViewById(R.id.plan_item_name);
                    plan_item_weight = (TextView) v.findViewById(R.id.plan_item_weight);
                    plan_item_calories = (TextView) v.findViewById(R.id.plan_item_calories);
                }
            }
        }
    }

}
