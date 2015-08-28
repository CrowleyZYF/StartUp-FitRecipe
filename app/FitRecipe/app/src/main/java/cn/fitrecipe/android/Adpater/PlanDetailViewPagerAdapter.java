package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.entity.PlanDetail;
import cn.fitrecipe.android.entity.PlanDetailItem;

/**
 * Created by 奕峰 on 2015/4/25.
 */
public class PlanDetailViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<PlanDetail> dataList;
    private List<View> planDetailLinearLayout = new ArrayList<View>();

    public PlanDetailViewPagerAdapter(Context context, List<PlanDetail> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PlanDetail planDetail = dataList.get(position);

        View planDetailContainer = LayoutInflater.from(context).inflate(R.layout.activity_plan_choice_info_detail, null);

        if(planDetail.getBreakfast_exist()) {
            TextView breakfast_plan_calories = (TextView) planDetailContainer.findViewById(R.id.breakfast_plan_calories);
            breakfast_plan_calories.setText(planDetail.getBreakfast_calories());
            TextView breakfast_plan_punch = (TextView) planDetailContainer.findViewById(R.id.breakfast_plan_punch);
            breakfast_plan_punch.setText(planDetail.getBreakfast_punch());
            LinearLayoutForListView breakfast_plan_detail = (LinearLayoutForListView) planDetailContainer.findViewById(R.id.breakfast_plan_detail);
            List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
            List<PlanDetailItem> breakfast_item = planDetail.getBreakfast_item();
            for(int i=0;i<breakfast_item.size();i++){
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("plan_item_id",breakfast_item.get(i).getId());
                item.put("plan_item_type",breakfast_item.get(i).getType());
                item.put("plan_item_name",breakfast_item.get(i).getName());
                item.put("plan_item_weight",breakfast_item.get(i).getWeight());
                item.put("plan_item_calories",breakfast_item.get(i).getCalories());
                dataList.add(item);
            }
            SimpleAdapter breakfast_plan_detail_adapter = new SimpleAdapter(this.context, dataList, R.layout.activity_plan_choice_info_detail_item,
                    new String[]{"plan_item_id","plan_item_type","plan_item_name","plan_item_weight","plan_item_calories"},
                    new int[]{R.id.plan_item_id, R.id.plan_item_type, R.id.plan_item_name, R.id.plan_item_weight, R.id.plan_item_calories});
            breakfast_plan_detail.setAdapter(breakfast_plan_detail_adapter);
        }else{
            LinearLayout breakfast_plan = (LinearLayout) planDetailContainer.findViewById(R.id.breakfast_plan);
            breakfast_plan.setVisibility(View.GONE);
        }
        if (planDetail.getAddmeal_01_exist()){
            TextView addmeal_01_plan_calories = (TextView) planDetailContainer.findViewById(R.id.addmeal_01_plan_calories);
            addmeal_01_plan_calories.setText(planDetail.getAddmeal_01_calories());
            TextView addmeal_01_plan_punch = (TextView) planDetailContainer.findViewById(R.id.addmeal_01_plan_punch);
            addmeal_01_plan_punch.setText(planDetail.getAddmeal_01_punch());
            LinearLayoutForListView addmeal_01_plan_detail = (LinearLayoutForListView) planDetailContainer.findViewById(R.id.addmeal_01_plan_detail);
            List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
            List<PlanDetailItem> addmeal_01_item = planDetail.getAddmeal_01_item();
            for(int i=0;i<addmeal_01_item.size();i++){
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("plan_item_id",addmeal_01_item.get(i).getId());
                item.put("plan_item_type",addmeal_01_item.get(i).getType());
                item.put("plan_item_name",addmeal_01_item.get(i).getName());
                item.put("plan_item_weight",addmeal_01_item.get(i).getWeight());
                item.put("plan_item_calories",addmeal_01_item.get(i).getCalories());
                dataList.add(item);
            }
            SimpleAdapter addmeal_01_plan_detail_adapter = new SimpleAdapter(this.context, dataList, R.layout.activity_plan_choice_info_detail_item,
                    new String[]{"plan_item_id","plan_item_type","plan_item_name","plan_item_weight","plan_item_calories"},
                    new int[]{R.id.plan_item_id, R.id.plan_item_type, R.id.plan_item_name, R.id.plan_item_weight, R.id.plan_item_calories});
            addmeal_01_plan_detail.setAdapter(addmeal_01_plan_detail_adapter);
        }else{
            LinearLayout addmeal_01_plan = (LinearLayout) planDetailContainer.findViewById(R.id.addmeal_01_plan);
            addmeal_01_plan.setVisibility(View.VISIBLE);
        }
        if (planDetail.getLunch_exist()){
            TextView lunch_plan_calories = (TextView) planDetailContainer.findViewById(R.id.lunch_plan_calories);
            lunch_plan_calories.setText(planDetail.getLunch_calories());
            TextView lunch_plan_punch = (TextView) planDetailContainer.findViewById(R.id.lunch_plan_punch);
            lunch_plan_punch.setText(planDetail.getLunch_punch());
            LinearLayoutForListView lunch_plan_detail = (LinearLayoutForListView) planDetailContainer.findViewById(R.id.lunch_plan_detail);
            List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
            List<PlanDetailItem> lunch_item = planDetail.getLunch_item();
            for(int i=0;i<lunch_item.size();i++){
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("plan_item_id",lunch_item.get(i).getId());
                item.put("plan_item_type",lunch_item.get(i).getType());
                item.put("plan_item_name",lunch_item.get(i).getName());
                item.put("plan_item_weight",lunch_item.get(i).getWeight());
                item.put("plan_item_calories",lunch_item.get(i).getCalories());
                dataList.add(item);
            }
            SimpleAdapter lunch_plan_detail_adapter = new SimpleAdapter(this.context, dataList, R.layout.activity_plan_choice_info_detail_item,
                    new String[]{"plan_item_id","plan_item_type","plan_item_name","plan_item_weight","plan_item_calories"},
                    new int[]{R.id.plan_item_id, R.id.plan_item_type, R.id.plan_item_name, R.id.plan_item_weight, R.id.plan_item_calories});
            lunch_plan_detail.setAdapter(lunch_plan_detail_adapter);
        }else{
            LinearLayout lunch_plan = (LinearLayout) planDetailContainer.findViewById(R.id.lunch_plan);
            lunch_plan.setVisibility(View.VISIBLE);
        }
        TextView addmeal_02_plan_calories = (TextView) planDetailContainer.findViewById(R.id.addmeal_02_plan_calories);
        addmeal_02_plan_calories.setText(planDetail.getAddmeal_02_calories());
        TextView addmeal_02_plan_punch = (TextView) planDetailContainer.findViewById(R.id.addmeal_02_plan_punch);
        addmeal_02_plan_punch.setText(planDetail.getAddmeal_02_punch());
        TextView supper_plan_calories = (TextView) planDetailContainer.findViewById(R.id.supper_plan_calories);
        supper_plan_calories.setText(planDetail.getSupper_calories());
        TextView supper_plan_punch = (TextView) planDetailContainer.findViewById(R.id.supper_plan_punch);
        supper_plan_punch.setText(planDetail.getSupper_punch());
        container.addView(planDetailContainer);
        planDetailLinearLayout.add(planDetailContainer);
        return planDetailContainer;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(planDetailLinearLayout.get(position));
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
