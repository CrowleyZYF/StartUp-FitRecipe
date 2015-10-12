package cn.fitrecipe.android.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import cn.fitrecipe.android.Adpater.PlanElementAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.Http.PostRequest;
import cn.fitrecipe.android.IngredientActivity;
import cn.fitrecipe.android.NutritionActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.entity.Series;
import cn.fitrecipe.android.entity.SeriesPlan;
import cn.fitrecipe.android.function.Common;
import cn.fitrecipe.android.function.JoinPlanHelper;
import cn.fitrecipe.android.function.JsonParseHelper;
import pl.tajchert.sample.DotsTextView;

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
    private TextView other_plan_days;

    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;
    private LinearLayout info_container;

    public static final int BREAKFAST_CODE = 00;
    public static final int ADDMEAL_01_CODE = 01;
    public static final int LUNCH_CODE = 02;
    public static final int ADDMEAL_02_CODE = 03;
    public static final int SUPPER_CODE = 04;
    public static final int ADDMEAL_03_CODE = 05;

    private int pointer = 0;
    private Map<String, DatePlan> data;
    public static boolean isFresh = false;

    private Map<String, SeriesPlan> planMap;
    private AtomicInteger count;

    //使用第三方计划的时候，完成第（1/7）天
    private Map<String, String> indexDate;


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

    private void initView(View v) {
        //信息页面
        info_container = (LinearLayout) v.findViewById(R.id.info_container);
        //计划区域
        plans = (LinearLayoutForListView) v.findViewById(R.id.plans);
        //添加到菜篮子
        shopping_btn = (ImageView) v.findViewById(R.id.shopping_btn);
        //增肌还是减脂
        plan_status = (TextView) v.findViewById(R.id.plan_status);
        //第几天
        plan_status_day = (TextView) v.findViewById(R.id.plan_status_day);
        //查看下一天的记录
        next_btn = (ImageView) v.findViewById(R.id.next_btn);
        //查看上一天的记录
        prev_btn = (ImageView) v.findViewById(R.id.prev_btn);
        //日期
        diy_days = (TextView) v.findViewById(R.id.diy_days);
        //计划名称
        plan_name = (TextView) v.findViewById(R.id.plan_name);
        //第三方计划的周期
        other_plan_days = (TextView) v.findViewById(R.id.other_plan_days);
        //加载页面
        loadingInterface = (LinearLayout) v.findViewById(R.id.loading_interface);
        dotsTextView = (DotsTextView) v.findViewById(R.id.dots);
    }

    private void initEvent() {
        shopping_btn.setOnClickListener(this);
        next_btn.setOnClickListener(this);
        prev_btn.setOnClickListener(this);
    }

    private void initData() {
        //初始化一日多餐
        adapter = new PlanElementAdapter(this, items, report);
        plans.setAdapter(adapter);
        //获取报告
        report = FrDbHelper.getInstance(getActivity()).getReport();
        if(report == null) {
            hideLoading(true, "No report!");
            return;
        }
        //显示健身状态
        if(report.isGoalType()) {
            plan_status.setText("增肌第");
        }else{
            plan_status.setText("减脂第");
        }
        //获取前面两天，后面七天的记录
        String today = Common.getDate();
        getData(Common.getSomeDay(today, -2), Common.getSomeDay(today, 7));
    }

    // 获取服务器上的记录
    private void getData(String start, String end) {
        data = new HashMap<>();
        if(Common.isOpenNetwork(getActivity())) {
            start = Common.dateFormat(start);
            end = Common.dateFormat(end);
            getDataFromServer(start, end);
        }else
            getDataFromLocal(start, end);
    }

    // 请求数据
    private void getDataFromServer(final String start, final String end) {
        GetRequest request = new GetRequest(FrServerConfig.getDatePlanUrl(start, end), FrApplication.getInstance().getToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        if(res != null && res.has("data")) {
                            try {
                                JSONObject data = res.getJSONObject("data");
                                processData(data, start, end);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        hideLoading(true, getResources().getString(R.string.network_error));
                        getDataFromLocal(start, end);
                    }
                });
        FrRequest.getInstance().request(request);
    }

    //处理网络上的数据
    private void processData(JSONObject data, String start, String end) throws JSONException {
        if(data != null) {
            planMap = new ConcurrentHashMap<>();
            count = new AtomicInteger(0);
            if(!data.getString("lastJoined").equals("null")) {
                count.incrementAndGet();
                requestPlanDetails(data.getJSONObject("lastJoined").getString("joined_date"), data.getJSONObject("lastJoined").getJSONObject("plan").getInt("id"), start, end);
            }
            JSONArray calendar = data.getJSONArray("calendar");
            for(int i = 0; i < calendar.length(); i++) {
                count.incrementAndGet();
                requestPlanDetails(calendar.getJSONObject(i).getString("joined_date"), calendar.getJSONObject(i).getJSONObject("plan").getInt("id"), start, end);
            }
            //新用户完全没有记录 直接创建一个新的自定义计划
            if(data.getString("lastJoined").equals("null") && calendar.length()==0){
                postprocess(start, end);
            }
        }
    }

    //获取计划详情
    private void requestPlanDetails(final String join_date, int plan_id, final String start, final String end) {
        GetRequest request = new GetRequest(FrServerConfig.getPlanDetails(plan_id), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        if (res != null && res.has("data")) {
                            try {
                                JSONObject data = res.getJSONObject("data");
                                parsePlanJson(join_date, data);
                                count.decrementAndGet();
                                if(count.compareAndSet(0, 0))
                                    postprocess(start, end);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (volleyError != null && volleyError.networkResponse != null) {
                            hideLoading(true, getResources().getString(R.string.network_error));
                            int statusCode = volleyError.networkResponse.statusCode;
                            if (statusCode == 404) {
                                Toast.makeText(
                                        getActivity(), "404！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        FrRequest.getInstance().request(request);
    }

    //处理计划的JSON
    private void parsePlanJson(String join_date, JSONObject data) throws JSONException {
        SeriesPlan plan = new SeriesPlan();
        plan.setId(data.getInt("id"));//计划ID
        plan.setImg(data.getString("img"));//计划缩略图
        plan.setInrtoduce(data.getString("inrtoduce"));//计划背景图
        plan.setDifficulty(data.getInt("difficulty"));//计划操作难度
        plan.setDelicious(data.getInt("delicious"));//计划美味程度
        plan.setBenifit(data.getInt("benifit"));//计划类型：增肌-减脂
        plan.setTotal_days(data.getInt("total_days"));//计划总共的天数
        plan.setDish_headcount(data.getInt("dish_headcount"));//采用计划的人数
        plan.setTitle(data.getString("title"));//计划名称

        //好多天的计划
        ArrayList<DatePlan> datePlans = new ArrayList<>();

        JSONArray routine_set = data.getJSONArray("routine_set");

        for(int i  = 0; i < routine_set.length(); i++) {
            JSONObject routine = routine_set.getJSONObject(i);

            //某一天的计划
            DatePlan datePlan = new DatePlan();
            datePlan.setPlan_name(plan.getTitle());

            JSONArray dish_set = routine.getJSONArray("dish_set");
            //某一天的一日五餐
            List<DatePlanItem> items = new ArrayList<>();

            //列表项
            for(int j = 0; j < dish_set.length(); j++) {
                //具体某一餐
                DatePlanItem item = new DatePlanItem();
                //确定餐的类型
                int type = dish_set.getJSONObject(j).getInt("type");
                switch (type) {
                    case 0:
                        item.setType("breakfast");  break;
                    case 1:
                        item.setType("add_meal_01"); break;
                    case 2:
                        item.setType("lunch");  break;
                    case 3:
                        item.setType("add_meal_02"); break;
                    case 4:
                        item.setType("supper");  break;
                    case 5:
                        item.setType("add_meal_03"); break;
                }
                item.setDefaultImageCover("");
                //获取计划中食材
                JSONArray singleingredient_set = dish_set.getJSONObject(j).getJSONArray("singleingredient_set");
                for(int k = 0; k < singleingredient_set.length(); k++) {
                    PlanComponent component = new PlanComponent();
                    component.setType(0);//标记为食材
                    component.setName(singleingredient_set.getJSONObject(k).getJSONObject("ingredient").getString("name"));//设置名称
                    component.setNutritions(JsonParseHelper.getNutritionSetFromJson(singleingredient_set.getJSONObject(k).getJSONObject("ingredient").getJSONObject("nutrition_set")));//获取营养信息
                    component.setAmount(singleingredient_set.getJSONObject(k).getInt("amount"));//设置重量
                    component.setCalories(component.getAmount() * singleingredient_set.getJSONObject(k).getJSONObject("ingredient").getJSONObject("nutrition_set").getJSONObject("Energy").getDouble("amount") / 100);//设置卡路里
                    item.addContent(component);
                }
                //获取计划中食谱
                JSONArray singlerecipe_set = dish_set.getJSONObject(j).getJSONArray("singlerecipe_set");
                for(int k = 0; k < singlerecipe_set.length(); k++) {
                    JSONObject json_recipe = singlerecipe_set.getJSONObject(k);
                    PlanComponent component = new PlanComponent();
                    component.setAmount(json_recipe.getInt("amount"));
                    component.setCalories(component.getAmount() * json_recipe.getJSONObject("recipe").getDouble("calories") / 100);
                    component.setType(1);
                    component.setId(json_recipe.getJSONObject("recipe").getInt("id"));
                    component.setName(json_recipe.getJSONObject("recipe").getString("title"));
                    component.setNutritions(JsonParseHelper.getNutritionSetFromJson(json_recipe.getJSONObject("recipe").getJSONObject("nutrition_set")));
                    JSONArray json_component = json_recipe.getJSONObject("recipe").getJSONArray("component_set");
                    ArrayList<PlanComponent> components = new ArrayList<>();
                    for(int q = 0; q < json_component.length(); q++) {
                        PlanComponent component1 = new PlanComponent();
                        JSONObject jcomponent = json_component.getJSONObject(q);
                        component1.setName(jcomponent.getJSONObject("ingredient").getString("name"));
                        component1.setAmount(jcomponent.getInt("amount"));
                        component1.setType(0);
                        components.add(component1);
                    }
                    component.setComponents(components);
                    item.addContent(component);
                }
                items.add(item);//添加早餐
            }
            datePlan.setItems(items);//添加某一天的一日五餐
            datePlans.add(datePlan);//添加计划中的某一天
        }

        plan.setDatePlans(datePlans);//设置计划中的好几天

        planMap.put(join_date, plan);//和日期对应
    }

    private void postprocess(String start, String end) throws JSONException {
        boolean isNew = false;
        Map<String, SeriesPlan> plans = new TreeMap<>();
        Set<String> keyset = planMap.keySet();
        Iterator<String> iterator = keyset.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            plans.put(key, planMap.get(key));
        }

        start = Common.dateFormatReverse(start);
        end = Common.dateFormatReverse(end);
        Set<String> keyset1 = plans.keySet();
        Iterator<String> iterator1 = keyset1.iterator();
        String nowDate = iterator1.hasNext() ? iterator1.next() : null;
        if(nowDate != null) {
            if (Common.CompareDate(start, nowDate) < 0)
                start = nowDate;
        }else {
            //新用户 默认设定自定义计划
            isNew = true;
            start = Common.getDate();
            nowDate = start;
            plans.put(start, gerneratePersonalPlan());
            new JoinPlanHelper(getActivity()).joinPersonalPlan(new JoinPlanHelper.CallBack() {
                @Override
                public void handle() {
                    hideLoading(false, "");
                    Toast.makeText(getActivity(), "默认设置自定义计划", Toast.LENGTH_SHORT).show();
                }
            });
        }
        SeriesPlan now = plans.get(nowDate);//切换的不同计划
        String str = start;
        while(Common.CompareDate(str, end) < 0) {

            if(plans.containsKey(str)) {
                nowDate = str;
                now = plans.get(str);
            }
                /*
                * nowDate是切换不同计划的时间点 str是自然时间 自定义计划是每天都会创建不同的
                * 所以
                * 1. 对于自定义计划来说 如果nowDate和str相同，就说明那天创建了自定义计划，直接放到记录里，否则就说明那天没有创建，为空
                * 2. 对于第三方计划来说 则取某一天的计划 自动轮循
                * */
            if(now.getTitle().equals("personal plan")) {
                if(nowDate.equals(str))
                    data.put(str, plans.get(str).getDatePlans().get(0));
                else
                    data.put(str, gernerateEmptyPlan(str));
            }else {
                int th = Common.getDiff(str, nowDate) % plans.get(str).getTotal_days();
                data.put(str, plans.get(nowDate).getDatePlans().get(th));
                indexDate.put(str, "完成("+th+"/"+plans.get(str).getDatePlans().size()+")天");
            }
            str = Common.getSomeDay(str, 1);
        }
        switchPlan(pointer, 1);
        if(!isNew)
            hideLoading(false, "");
    }

    private SeriesPlan gerneratePersonalPlan() {
        SeriesPlan plan = new SeriesPlan();
        plan.setTitle("personal plan");
        plan.setTotal_days(1);
        ArrayList<DatePlan> datePlans = new ArrayList<>();
        datePlans.add(gernerateEmptyPlan(Common.getDate()));
        plan.setDatePlans(datePlans);
        return plan;
    }

    private DatePlan gernerateEmptyPlan(String date) {
        DatePlan datePlan = new DatePlan();
        datePlan.setIsPunch(false);
        datePlan.setPlan_name("personal plan");
        datePlan.setInBasket(false);
        datePlan.setDate(date);
        datePlan.setItems(FrDbHelper.getInstance(getActivity()).generateDatePlan());
        return datePlan;
    }

    //pointer是往前或者往后几天 dir是之前还是之后
    private boolean switchPlan(int pointer, int dir) {
        if(datePlan != null) {
            boolean flag = false;
            for(int i = 0; i < items.size(); i++)
                flag = flag || items.get(i).isInBasket();
            datePlan.setInBasket(flag);
            datePlan.setItems(items);
        }
        String str = Common.getSomeDay(Common.getDate(), pointer);//获取几月几号的记录
        int days = Common.getDiff(str, report.getUpdatetime()) + 1;//增肌减脂第几天
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
            plan_name.setText(datePlan.getPlan_name().equals("personal plan")?"自定义计划":datePlan.getPlan_name());
            other_plan_days.setVisibility(datePlan.getPlan_name().equals("personal plan")?View.GONE:View.VISIBLE);
            other_plan_days.setText(indexDate!=null&&indexDate.containsKey(str)?indexDate.get(str):"");
            items = datePlan.getItems();
            if(pointer > 0)
                adapter.setData(items, datePlan.getPlan_name().equals("personal plan")?true:false, false);//未来
            else
                adapter.setData(items, datePlan.getPlan_name().equals("personal plan")?true:false, true);//过去
            return true;
        }
        else {
            Toast.makeText(getActivity(), "已经无计划了!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void getDataFromLocal(String start, String end) {

    }

    private void loadData() {
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
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }else{
            info_container.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(isFresh) {
//            loadData();
//        }
//        isFresh = false;
    }


    @Override
    public void onPause() {
        super.onPause();
//        if(datePlan != null) {
//            boolean flag = false, flag1 = false;
//            for(int i = 0; i < items.size(); i++) {
//                flag = flag || items.get(i).isInBasket();
//                flag1 = flag1 || items.get(i).isPunch();
//            }
//            datePlan.setInBasket(flag);
//            datePlan.setIsPunch(flag1);
//            datePlan.setItems(items);
//        }
//        Set<String> keySet = data.keySet();
//        Iterator<String> iterator = keySet.iterator();
//        while (iterator.hasNext()) {
//            DatePlan update = data.get(iterator.next());
//            FrDbHelper.getInstance(getActivity()).addDatePlan(update);
//        }
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
