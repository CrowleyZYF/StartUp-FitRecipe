package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.entity.PunchDay;
import cn.fitrecipe.android.entity.PunchItem;

/**
 * Created by 奕峰 on 2015/4/24.
 */
public class PunchDayAdapter extends RecyclerView.Adapter<PunchDayAdapter.PunchDayViewHolder>{

    private List<PunchDay> PunchDaysList;
    private Context context;


    public PunchDayAdapter(Context context, List<PunchDay> PunchDaysList) {
        this.context = context;
        this.PunchDaysList = PunchDaysList;
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
        PunchDay pd = PunchDaysList.get(i);
        contactViewHolder.punch_day.setText(pd.getDate());
        PunchItemAdapter punchItemAdapter = new PunchItemAdapter(this.context, pd.getItemLists());
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
        private List<PunchItem> punchItems;

        public PunchItemAdapter(Context context, List<PunchItem> punchItems){
            this.context = context;
            this.punchItems = punchItems;
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
        public View getView(int position, View convertView, ViewGroup parent) {
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
                holder.punch_piechart = (PieChartView) convertView.findViewById(R.id.punch_piechart);
                convertView.setTag(holder);
            }
            holder.punch_photo.setImageResource(punchItems.get(position).getPunch_item_photo());
            holder.punch_day.setText(punchItems.get(position).getPunch_item_days_index() + "");
            holder.punch_type.setText(punchItems.get(position).getPunch_item_type());
            holder.punch_calories.setText(punchItems.get(position).getPunch_item_calorie() + "kcal");
            float[] pieData = {Float.parseFloat(punchItems.get(position).getPunch_item_carbohydrate_ratio()),
                    Float.parseFloat(punchItems.get(position).getPunch_item_protein_ratio()),
                    Float.parseFloat(punchItems.get(position).getPunch_item_lipids_ratio())
            };
            holder.punch_piechart.setValue(pieData, false, false, false);
            return convertView;
        }

        class ViewHolder {
            ImageView punch_photo;
            TextView punch_day;
            TextView punch_type;
            TextView punch_calories;
            PieChartView punch_piechart;
        }
    }
}
