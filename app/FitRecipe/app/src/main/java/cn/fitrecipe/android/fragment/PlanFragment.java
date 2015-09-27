package cn.fitrecipe.android.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.fitrecipe.android.Adpater.PlanElementAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.IngredientActivity;
import cn.fitrecipe.android.NutritionActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.DayPlan;
import cn.fitrecipe.android.entity.PlanComponent;
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
    private List<DatePlanItem> items;
    private DatePlan datePlan;
    private PlanElementAdapter adapter;
    private ImageView shopping_btn, next_btn, prev_btn;
    private TextView plan_status_day, plan_status, diy_days, plan_name;

    public static final int BREAKFAST_CODE = 00;
    public static final int ADDMEAL_01_CODE = 01;
    public static final int LUNCH_CODE = 02;
    public static final int ADDMEAL_02_CODE = 03;
    public static final int SUPPER_CODE = 04;
    public static final int ADDMEAL_03_CODE = 05;

    private int pointer = 0;
    private long now;
    private Map<String, DatePlan> data;
    public static boolean isFresh = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);
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

//        switchPlan(pointer);
        loadData();
    }

    private void loadData() {
        long t = System.currentTimeMillis();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                if(report.isGoalType()) {
                    plan_status.setText("增肌第");
                }else{
                    plan_status.setText("减脂第");
                }
                switchPlan(pointer, 1);
            }

            @Override
            protected Void doInBackground(Void... params) {
                if (data != null)
                    data.clear();
                datePlan = null;
                report = FrDbHelper.getInstance(getActivity()).getReport();
                String start = Common.getSomeDay(Common.getDate(), -2);
                if(Common.CompareDate(start, report.getUpdatetime()) < 0)
                    start = report.getUpdatetime();
                data = FrDbHelper.getInstance(getActivity()).getDatePlan(start, Common.getSomeDay(Common.getDate(), 6));
                return null;
            }
        }.execute();
        long tt = System.currentTimeMillis();
        Toast.makeText(getActivity(), (tt - t) + "ms", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isFresh) {
            loadData();
        }
        isFresh = false;
    }


    @Override
    public void onPause() {
        super.onPause();
        if(datePlan != null) {
            boolean flag = false;
            for(int i = 0; i < items.size(); i++)
                flag = flag || items.get(i).isInBasket();
            datePlan.setInBasket(flag);
            datePlan.setItems(items);
        }
        Set<String> keySet = data.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            DatePlan update = data.get(iterator.next());
            FrDbHelper.getInstance(getActivity()).addDatePlan(update);
        }
    }

    private boolean switchPlan(int pointer, int dir) {
        if(datePlan != null) {
            boolean flag = false;
            for(int i = 0; i < items.size(); i++)
                flag = flag || items.get(i).isInBasket();
            datePlan.setInBasket(flag);
            datePlan.setItems(items);
        }
        String str = Common.getSomeDay(Common.getDate(), pointer);
        int days = Common.getDiff(str, report.getUpdatetime()) + 1;
        if(days + dir * 2 > 0 && pointer + dir * 2 < 7) {
            final String preGet = Common.getSomeDay(str, dir * 2);
            if (data == null || !data.containsKey(preGet)) {
                new Thread() {
                    @Override
                    public void run() {
                        Map<String, DatePlan> res = FrDbHelper.getInstance(getActivity()).getDatePlan(preGet, preGet);
                        if (res != null && res.size() > 0) {
                            data.putAll(res);
                        }
                    }
                }.start();
            }
        }
        if (data.containsKey(str)) {
            diy_days.setText(str);
            plan_status_day.setText(days + "");
            datePlan = data.get(str);
            plan_name.setText(datePlan.getPlan_name());
            items = datePlan.getItems();
            if(pointer > 0)
                adapter.setData(items, datePlan.getPlan_name().equals("自定义计划")?true:false, false);
            else
                adapter.setData(items, datePlan.getPlan_name().equals("自定义计划")?true:false, true);
            return true;
        }
        else {
            Toast.makeText(getActivity(), "已经无计划了!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BREAKFAST_CODE:
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("component_selected")) {
                    PlanComponent obj = (PlanComponent) data.getSerializableExtra("component_selected");
                    items.get(0).addContent(obj);
                    adapter.notifyDataSetChanged();
                }
                break;
            case ADDMEAL_01_CODE:
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("component_selected")) {
                    PlanComponent obj = (PlanComponent) data.getSerializableExtra("component_selected");
                    items.get(1).addContent(obj);
                    adapter.notifyDataSetChanged();
                }
                break;
            case LUNCH_CODE:
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("component_selected")) {
                    PlanComponent obj = (PlanComponent) data.getSerializableExtra("component_selected");
                    items.get(0).addContent(obj);
                    adapter.notifyDataSetChanged();
                }
                break;
            case ADDMEAL_02_CODE:
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("component_selected")) {
                    PlanComponent obj = (PlanComponent) data.getSerializableExtra("component_selected");
                    items.get(0).addContent(obj);
                    adapter.notifyDataSetChanged();
                }
                break;
            case SUPPER_CODE:
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("component_selected")) {
                    PlanComponent obj = (PlanComponent) data.getSerializableExtra("component_selected");
                    items.get(0).addContent(obj);
                    adapter.notifyDataSetChanged();
                }
                break;
            case ADDMEAL_03_CODE:
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("component_selected")) {
                    PlanComponent obj = (PlanComponent) data.getSerializableExtra("component_selected");
                    items.get(0).addContent(obj);
                    adapter.notifyDataSetChanged();
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void toggle(String type) {
//        Toast.makeText(getActivity(), type.value()+"", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), NutritionActivity.class);
        boolean f = true;
       if(type.equals("all")) {
           f = false;
            for(int i = 0; i < items.size(); i++){
                if(items.get(i).getComponents() != null && items.get(i).getComponents().size()> 0) {
                    f = true;
                    break;
                }
            }
       }
        if(f) {
            if(datePlan != null)
                datePlan.setItems(items);
            intent.putExtra("itemtype", type);
            intent.putExtra("dateplan", datePlan);
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
                if(switchPlan(pointer + 1, 1)) {
                    pointer++;
                }
                break;
            case R.id.prev_btn:
                if(switchPlan(pointer - 1, -1)) {
                    pointer--;
                }
                break;
        }
    }
}
