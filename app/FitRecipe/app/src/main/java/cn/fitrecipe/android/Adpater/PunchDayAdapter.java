package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.PunchContentSureActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.function.Common;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class PunchDayAdapter extends RecyclerView.Adapter<PunchDayAdapter.PunchDayViewHolder>{

    private List<DatePlan> PunchDaysList;
    private Context context;
    private Report report;

    public PunchDayAdapter(Context context, List<DatePlan> PunchDaysList, Report report) {
        this.context = context;
        this.PunchDaysList = PunchDaysList;
        this.report = report;
    }

    @Override
    public PunchDayAdapter.PunchDayViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.fragment_record_punch_day, viewGroup, false);

        return new PunchDayViewHolder(itemView, this.context);
    }

    @Override
    public void onBindViewHolder(PunchDayAdapter.PunchDayViewHolder contactViewHolder, int i) {
        DatePlan pd = PunchDaysList.get(i);
        contactViewHolder.punch_day.setText(pd.getDate());
        PunchItemAdapter punchItemAdapter = new PunchItemAdapter(this.context, pd);
        contactViewHolder.punch_items.setAdapter(punchItemAdapter);
    }

    @Override
    public int getItemCount() {
        if(PunchDaysList == null)
            return 0;
        else
            return PunchDaysList.size();
    }

    public static class PunchDayViewHolder extends RecyclerView.ViewHolder {
        protected TextView punch_day;
        protected GridView punch_items;

        public PunchDayViewHolder(View itemView, Context context) {
            super(itemView);
            punch_day =  (TextView) itemView.findViewById(R.id.punch_day);
            punch_items = (GridView) itemView.findViewById(R.id.punch_day_gridview);
        }
    }

    private class PunchItemAdapter extends BaseAdapter{

        private Context context;
        private List<DatePlanItem> punchItems;
        private DatePlan datePlan;

        public PunchItemAdapter(Context context, DatePlan datePlan){
            this.context = context;
            this.datePlan = datePlan;
            this.punchItems = datePlan.getItems();
        }

        @Override
        public int getCount() {
            if(punchItems == null)
                return 0;
            else
                return punchItems.size();
        }

        @Override
        public Object getItem(int position) {
            return punchItems.get(position);
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
                convertView = View.inflate(context, R.layout.fragment_record_punch_item, null);
                holder.punch_photo = (ImageView) convertView.findViewById(R.id.punch_photo);
                holder.punch_day = (TextView) convertView.findViewById(R.id.punch_day);
                holder.punch_type = (TextView) convertView.findViewById(R.id.punch_type);
                holder.punch_calories = (TextView) convertView.findViewById(R.id.punch_calories);
                holder.punch_share = (ImageView) convertView.findViewById(R.id.share_btn);
                //holder.punch_piechart = (PieChartView) convertView.findViewById(R.id.punch_piechart);
                convertView.setTag(holder);
            }
            holder.punch_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Common.toBeContinuedDialog(context).show();
                }
            });
            if(punchItems.get(position).getImageCover() == null || punchItems.get(position).getImageCover().length() == 0)
                FrApplication.getInstance().getMyImageLoader().displayImage(holder.punch_photo, punchItems.get(position).getDefaultImageCover());
            else
                FrApplication.getInstance().getMyImageLoader().displayImage(holder.punch_photo, punchItems.get(position).getImageCover());

            holder.punch_day.setText((Common.getDiff(datePlan.getDate(), punchItems.get(position).getDate())+1) + "");
            switch (punchItems.get(position).getType()) {
                case "breakfast" :
                    holder.punch_type.setText("早餐");    break;
                case "lunch":
                    holder.punch_type.setText("午餐");    break;
                case "supper":
                    holder.punch_type.setText("晚餐");    break;
                case "add_meal_01":
                case "add_meal_02":
                    holder.punch_type.setText("加餐");    break;
                case "add_meal_03":
                    holder.punch_type.setText("夜宵");    break;
            }
            holder.punch_calories.setText(Math.round(punchItems.get(position).getCalories_take()) + "kcal");


            double sum = punchItems.get(position).getCarbohydrate_take() + punchItems.get(position).getProtein_take() + punchItems.get(position).getFat_take();

            int a = (int) Math.round(punchItems.get(position).getCarbohydrate_take() * 100 / sum);
            int b = (int) Math.round(punchItems.get(position).getProtein_take() * 100 / sum);
            int c = 100 - a - b;
            //holder.punch_piechart.setValue(new float[]{a, b, c}, false, false, false);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PunchContentSureActivity.class);
                    intent.putExtra("item", punchItems.get(position));
                    intent.putExtra("action", "share");
                    context.startActivity(intent);
                }
            });

            return convertView;
        }

        class ViewHolder {
            ImageView punch_photo;
            TextView punch_day;
            TextView punch_type;
            TextView punch_calories;
            //PieChartView punch_piechart;
            ImageView punch_share;
        }
    }
}
