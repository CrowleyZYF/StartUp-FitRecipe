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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.fitrecipe.android.Adpater.PlanElementAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.IngredientActivity;
import cn.fitrecipe.android.NutritionActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.SlidingPage;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.DayPlan;
import cn.fitrecipe.android.entity.PlanItem;
import cn.fitrecipe.android.entity.Report;

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
    private TextView plan_status_day;

    public static final int BREAKFAST_CODE = 00;
    public static final int ADDMEAL_01_CODE = 01;
    public static final int LUNCH_CODE = 02;
    public static final int ADDMEAL_02_CODE = 03;
    public static final int SUPPER_CODE = 04;

    private int pointer = 0;
    private long now;

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
        plan_status_day = (TextView) v.findViewById(R.id.plan_status_day);
        next_btn = (ImageView) v.findViewById(R.id.next_btn);
        prev_btn = (ImageView) v.findViewById(R.id.prev_btn);
    }

    private void initData() {
        now = System.currentTimeMillis();
        adapter = new PlanElementAdapter(this, items, report);
        plans.setAdapter(adapter);
//        switchPlan(pointer);
    }


    @Override
    public void onResume() {
        super.onResume();
        switchPlan(pointer);
    }


    private void switchPlan(int pointer) {
        Date date = new Date(now + pointer * 24 * 3600 * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);
        dayPlan = FrDbHelper.getInstance(getActivity()).getDayPlan(strDate);
        if(dayPlan == null) {
            dayPlan = new DayPlan();
            dayPlan.setDate(strDate);
            dayPlan = FrDbHelper.getInstance(getActivity()).addDayPlan(dayPlan);
        }
        items = dayPlan.getPlanItems();
        plan_status_day.setText(pointer+"");
        adapter.setData(items);
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
                pointer++;
                switchPlan(pointer);
                break;
            case R.id.prev_btn:
                pointer--;
                switchPlan(pointer);
                break;
        }
    }


}
