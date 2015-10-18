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
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.SeriesPlan;

/**
 * Created by 奕峰 on 2015/4/25.
 */
public class PlanDetailViewPagerAdapter extends PagerAdapter {
    private Context context;
    private SeriesPlan plan;
    private List<DatePlan> dataList;

    public PlanDetailViewPagerAdapter(Context context, SeriesPlan plan){
        this.context = context;
        this.plan = plan;
        this.dataList = plan.getDatePlans();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        DatePlan datePlan = dataList.get(position);
        View planDetailContainer = LayoutInflater.from(context).inflate(R.layout.plan_detail_list_item, null);

        LinearLayoutForListView listView = (LinearLayoutForListView) planDetailContainer.findViewById(R.id.dayplan_detail);
        PlanItemAdapter adapter = new PlanItemAdapter(datePlan.getItems());
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
        return plan.getTotal_days();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    class PlanItemAdapter extends BaseAdapter {

        private List<DatePlanItem> items;

        public PlanItemAdapter(List<DatePlanItem> items) {
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
            switch (items.get(position).getType()) {
                case "breakfast" :
                    holder.plan_item_title.setText("早餐");    break;
                case "lunch":
                    holder.plan_item_title.setText("午餐");    break;
                case "supper":
                    holder.plan_item_title.setText("晚餐");    break;
                case "add_meal_01":
                case "add_meal_02":
                    holder.plan_item_title.setText("加餐");    break;
                case "add_meal_03":
                    holder.plan_item_title.setText("夜宵");    break;
            }
            holder.plan_item_calories.setText(Math.round(items.get(position).getCalories_take()) + "");
            holder.plan_item_punch.setText(items.get(position).getPunchNums()+"");
            ComponentAdapter componentAdapter = new ComponentAdapter(items.get(position).getComponents());
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

        class ComponentAdapter extends BaseAdapter implements View.OnClickListener {
            private List<PlanComponent> data;

            public ComponentAdapter(List<PlanComponent> data) {
                this.data = data;
            }

            @Override
            public int getCount() {
                if(data == null)    return 0;
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
            public View getView(final int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if(convertView != null) {
                    holder = (ViewHolder) convertView.getTag();
                }else {
                    convertView = View.inflate(context, R.layout.activity_plan_choice_info_detail_item, null);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                }
                holder.plan_item_name.setText(data.get(position).getName());
                holder.plan_item_weight.setText(data.get(position).getAmount()+"g");
                holder.plan_item_calories.setText(Math.round(data.get(position).getCalories() * data.get(position).getAmount() / 100) + "kcal");
                /*if(data.get(position).getType() == 1) {
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String recipe_id = data.get(position).getId() + "";
                            Intent intent = new Intent(context, RecipeActivity.class);
                            intent.putExtra("id", recipe_id);
                            context.startActivity(intent);
                        }
                    });
                }*/
                holder.plan_item_type.setText(data.get(position).getType()+"");
                holder.plan_item_id.setText(data.get(position).getId()+"");
                convertView.setOnClickListener(this);
                return convertView;
            }

            @Override
            public void onClick(View v) {
                String type = ((TextView) v.findViewById(R.id.plan_item_type)).getText().toString();
                String id = ((TextView) v.findViewById(R.id.plan_item_id)).getText().toString();
                if (type.equals("1")){
                    Intent intent = new Intent(context, RecipeActivity.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            }

            class ViewHolder {
                TextView plan_item_name;
                TextView plan_item_weight;
                TextView plan_item_calories;
                TextView plan_item_type;
                TextView plan_item_id;

                public ViewHolder(View v) {
                    plan_item_name = (TextView) v.findViewById(R.id.plan_item_name);
                    plan_item_weight = (TextView) v.findViewById(R.id.plan_item_weight);
                    plan_item_calories = (TextView) v.findViewById(R.id.plan_item_calories);
                    plan_item_type = (TextView) v.findViewById(R.id.plan_item_type);
                    plan_item_id = (TextView) v.findViewById(R.id.plan_item_id);
                }
            }
        }
    }

}
