package cn.fitrecipe.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.PlanElementAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.IngredientActivity;
import cn.fitrecipe.android.NutritionActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.DayPlan;
import cn.fitrecipe.android.entity.PlanItem;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.entity.SeriesPlan;
import cn.fitrecipe.android.function.Common;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class PlanFragment extends Fragment implements View.OnClickListener{

    //Report
    private Report report;

    private LinearLayoutForListView plans;
    private List<PlanItem> items;
    private DayPlan dayPlan;
    private PlanElementAdapter adapter;
    private ImageView shopping_btn, next_btn, prev_btn;
    private TextView plan_status_day, plan_status, diy_days, plan_name;

    public static final int BREAKFAST_CODE = 00;
    public static final int ADDMEAL_01_CODE = 01;
    public static final int LUNCH_CODE = 02;
    public static final int ADDMEAL_02_CODE = 03;
    public static final int SUPPER_CODE = 04;

    private int pointer = 0;
    private long now;
    private Map<String, DayPlan> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        report = FrDbHelper.getInstance(getActivity()).getReport(FrApplication.getInstance().getAuthor());
        initView(v);
        initEvent();
        initData();
        return v;
    }

    private void initEvent() {
        shopping_btn.setOnClickListener(this);
        next_btn.setOnClickListener(this);
        prev_btn.setOnClickListener(this);
    }

    private void initView(View v) {
        plans = (LinearLayoutForListView) v.findViewById(R.id.plans);

        shopping_btn = (ImageView) v.findViewById(R.id.shopping_btn);
        plan_status = (TextView) v.findViewById(R.id.plan_status);
        plan_status_day = (TextView) v.findViewById(R.id.plan_status_day);
        next_btn = (ImageView) v.findViewById(R.id.next_btn);
        prev_btn = (ImageView) v.findViewById(R.id.prev_btn);
        diy_days = (TextView) v.findViewById(R.id.diy_days);
        plan_name = (TextView) v.findViewById(R.id.plan_name);
    }

    private void initData() {
        now = System.currentTimeMillis();
        adapter = new PlanElementAdapter(this, items, report);
        plans.setAdapter(adapter);
        if(report.isGoalType()) {
            plan_status.setText("增肌第");
        }else{
            plan_status.setText("减脂第");
        }
//        switchPlan(pointer);
    }


    @Override
    public void onResume() {
        super.onResume();
        long t = System.currentTimeMillis();
        data = FrDbHelper.getInstance(getActivity()).getMyPlan();
        switchPlan(pointer);
        long tt = System.currentTimeMillis();
        Toast.makeText(getActivity(), (tt - t)+"ms", Toast.LENGTH_SHORT).show();
    }

    private boolean switchPlan(int pointer) {
        String str = Common.getSomeDay(Common.getDate(), pointer);
        if (data.containsKey(str)) {
            diy_days.setText(str);
            int days = (int) (getDiff(str, report.getUpdatetime()) + 1);
            plan_status_day.setText(days + "");
            dayPlan = data.get(str);
            plan_name.setText(dayPlan.getPlan().getName());
            items = dayPlan.getPlanItems();
            adapter.setData(items);
            return true;
        }
        else {
            Toast.makeText(getActivity(), "已经无计划了!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


//    private void switchPlan(int pointer) {
//        Date date = new Date(now + pointer * 24 * 3600 * 1000);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String strDate = sdf.format(date);
//        int days = (int) (getDiff(strDate, report.getUpdatetime()) + 1);
//        if(days <= 0) {
//            Toast.makeText(getActivity(), "已经是最前一天了！", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(pointer >= 7) {
//            Toast.makeText(getActivity(), "只能制定7天内的计划！", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        diy_days.setText(strDate);
//        plan_status_day.setText(days +"");
////        dayPlan = FrDbHelper.getInstance(getActivity()).getDayPlan(strDate);
//        if(dayPlan == null) {
//            dayPlan = new DayPlan();
//            dayPlan.setDate(strDate);
//            dayPlan = FrDbHelper.getInstance(getActivity()).addDayPlan(dayPlan);
//        }
//        items = dayPlan.getPlanItems();
//        adapter.setData(items);
//    }


    private long getDiff(String str1, String str2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1, date2;
        try {
            date1 = sdf.parse(str1);
            date2 = sdf.parse(str2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return (date1.getTime() - date2.getTime()) / (24 * 3600 *1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Object obj = null;
        if(resultCode == getActivity().RESULT_OK && data.hasExtra("obj_selected")) {
            obj = data.getSerializableExtra("obj_selected");
            switch (requestCode) {
                case BREAKFAST_CODE:
                    items.get(0).addContent(obj);
                    FrDbHelper.getInstance(getActivity()).addPlanItem(dayPlan.getPlanItems().get(0));
                    break;
                case ADDMEAL_01_CODE:
                    items.get(1).addContent(obj);
                    FrDbHelper.getInstance(getActivity()).addPlanItem(dayPlan.getPlanItems().get(1));
                    break;
                case LUNCH_CODE:
                    items.get(2).addContent(obj);
                    FrDbHelper.getInstance(getActivity()).addPlanItem(dayPlan.getPlanItems().get(2));
                    break;
                case ADDMEAL_02_CODE:
                    items.get(3).addContent(obj);
                    FrDbHelper.getInstance(getActivity()).addPlanItem(dayPlan.getPlanItems().get(3));
                    break;
                case SUPPER_CODE:
                    items.get(4).addContent(obj);
                    FrDbHelper.getInstance(getActivity()).addPlanItem(dayPlan.getPlanItems().get(4));
                    break;
            }
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void toggle(PlanItem.ItemType type) {
//        Toast.makeText(getActivity(), type.value()+"", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), NutritionActivity.class);
        boolean f = true;
       if(type == PlanItem.ItemType.ALL) {
           f = false;
            for(int i = 0; i < items.size(); i++){
                if(items.get(i).size() > 0) {
                    f = true;
                    break;
                }
            }
       }
        if(f) {
            intent.putExtra("itemtype", type);
            intent.putExtra("dayplan", dayPlan);
            startActivity(intent);
        }else {
            Toast.makeText(getActivity(), "请添加食谱和食材！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_btn:
                Intent intent = new Intent(getActivity(), IngredientActivity.class);
                startActivity(intent);
                break;
            case R.id.next_btn:
                if(switchPlan(pointer + 1))
                    pointer++;
                break;
            case R.id.prev_btn:
                if(switchPlan(pointer - 1))
                    pointer--;
                break;
        }
    }


}
