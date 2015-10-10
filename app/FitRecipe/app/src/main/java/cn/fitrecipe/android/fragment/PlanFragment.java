package cn.fitrecipe.android.fragment;

import android.app.Application;
import android.content.Intent;
import android.media.JetPlayer;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
import cn.fitrecipe.android.entity.PlanAuthor;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.entity.Series;
import cn.fitrecipe.android.entity.SeriesPlan;
import cn.fitrecipe.android.function.Common;
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

    // start, end 2015-09-23
    private void getData(String start, String end) {
        data = new HashMap<>();
        if(Common.isOpenNetwork(getActivity())) {
            start = Common.dateFormat(start);
            end = Common.dateFormat(end);
            getDataFromServer(start, end);
        }else
            getDataFromLocal(start, end);
    }

    private void getDataFromServer(final String start, final String end) {
        GetRequest request = new GetRequest(FrServerConfig.getDatePlanUrl(start, end), FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res != null && res.has("data")) {
                    try {
                        JSONObject data = res.getJSONObject("data");
                        processData(data, start, end);
                        hideLoading(false, "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideLoading(true, getResources().getString(R.string.network_error));
                getDataFromLocal(start, end);
            }
        });
        FrRequest.getInstance().request(request);
    }

    private void processData(JSONObject data, String start, String end) throws JSONException {
        if(data != null) {
            planMap = new ConcurrentHashMap<>();
            count = new AtomicInteger(0);
            if(data.getJSONObject("lastJoined") != null) {
                count.incrementAndGet();
                requestPlanDetails(data.getJSONObject("lastJoined").getString("joined_date"), data.getJSONObject("lastJoined").getJSONObject("plan").getInt("id"), start, end);
            }
            JSONArray calendar = data.getJSONArray("calendar");
            for(int i = 0; i < calendar.length(); i++) {
                count.incrementAndGet();
                requestPlanDetails(calendar.getJSONObject(i).getString("joined_date"), calendar.getJSONObject(i).getJSONObject("plan").getInt("id"), start, end);
            }
        }
    }

    private void requestPlanDetails(final String join_date, int plan_id, final String start, final String end) {
        String url = FrServerConfig.getPlanDetails(plan_id);
        GetRequest request = new GetRequest(url, null, new Response.Listener<JSONObject>() {
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
        }, new Response.ErrorListener() {
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

    private void postprocess(String start, String end) {
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
        if(nowDate != null && Common.CompareDate(start, nowDate) >= 0) {
            SeriesPlan now = plans.get(nowDate);
            String str = start;
            while(!str.equals(end)) {
                if(plans.containsKey(str)) {
                    nowDate = str;
                    now = plans.get(str);
                }
                if(now.getTitle().equals("personal plan")) {
                    if(nowDate.equals(str))
                        data.put(str, plans.get(str).getDatePlans().get(0));
                    else
                        data.put(str, gernerateEmptyPlan(str));
                }else {
                    int th = Common.getDiff(str, nowDate);
                    data.put(str, plans.get(str).getDatePlans().get(th));
                }
                str = Common.getSomeDay(str, 1);
            }
            switchPlan(pointer, 1);
        }else {
            Toast.makeText(getActivity(), "服务器数据错误!", Toast.LENGTH_SHORT).show();
        }
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
    private void parsePlanJson(String join_date, JSONObject data) throws JSONException {
        SeriesPlan plan = new SeriesPlan();
        plan.setId(data.getInt("id"));
        plan.setImg(data.getString("img"));
        plan.setInrtoduce(data.getString("inrtoduce"));
        plan.setDifficulty(data.getInt("difficulty"));
        plan.setDelicious(data.getInt("delicious"));
        plan.setBenifit(data.getInt("benifit"));
        plan.setTotal_days(data.getInt("total_days"));
        plan.setDish_headcount(data.getInt("dish_headcount"));
        plan.setTitle(data.getString("title"));

//        JSONObject author_json = data.getJSONObject("author");
//        plan.setAuthor(new Gson().fromJson(author_json.toString(), PlanAuthor.class));

        ArrayList<DatePlan> datePlans = new ArrayList<>();

        JSONArray routine_set = data.getJSONArray("routine_set");
        for(int i  = 0; i < routine_set.length(); i++) {
            JSONObject routine = routine_set.getJSONObject(i);
            DatePlan datePlan = new DatePlan();
            datePlan.setPlan_name(plan.getTitle());
            JSONArray dish_set = routine.getJSONArray("dish_set");
            List<DatePlanItem> items = new ArrayList<>();
            for(int j = 0; j < dish_set.length(); j++) {
                DatePlanItem item = new DatePlanItem();
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
                JSONArray singleingredient_set = dish_set.getJSONObject(j).getJSONArray("singleingredient_set");
                for(int k = 0; k < singleingredient_set.length(); k++) {
                    PlanComponent component = new PlanComponent();
                    component.setType(0);
                    component.setName(singleingredient_set.getJSONObject(k).getJSONObject("ingredient").getString("name"));
                    component.setNutritions(JsonParseHelper.getNutritionSetFromJson(singleingredient_set.getJSONObject(k).getJSONObject("ingredient").getJSONObject("nutrition_set")));
                    component.setAmount(singleingredient_set.getJSONObject(k).getInt("amount"));
                    component.setCalories(component.getAmount() * singleingredient_set.getJSONObject(k).getJSONObject("ingredient").getJSONObject("nutrition_set").getJSONObject("Energy").getDouble("amount") / 100);
//                    componentList.add(component);
                    item.addContent(component);
                }
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
//                    componentList.add(component);
                    item.addContent(component);
                }
//                item.setComponents(componentList);
                items.add(item);
            }
            datePlan.setItems(items);
            datePlans.add(datePlan);
        }
        plan.setDatePlans(datePlans);

        planMap.put(join_date, plan);
    }

    private void getDataFromLocal(String start, String end) {

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

        loadingInterface = (LinearLayout) v.findViewById(R.id.loading_interface);
        dotsTextView = (DotsTextView) v.findViewById(R.id.dots);
        info_container = (LinearLayout) v.findViewById(R.id.info_container);
    }

    private void initData() {
        adapter = new PlanElementAdapter(this, items, report);
        plans.setAdapter(adapter);

        report = FrDbHelper.getInstance(getActivity()).getReport();
        if(report == null) {
            hideLoading(true, "No report!");
            return;
        }
        if(report.isGoalType()) {
            plan_status.setText("增肌第");
        }else{
            plan_status.setText("减脂第");
        }


        String today = Common.getDate();
        getData(Common.getSomeDay(today, -2), Common.getSomeDay(today, 7));
//        try {
//            createEmptyPlan();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        getRecentPlan();

//        getData();
//        loadData();
    }



    private void createEmptyPlan() throws JSONException {
        JSONObject params = new JSONObject();
        JSONArray dish = new JSONArray();
        List<DatePlanItem> items = FrDbHelper.getInstance(getActivity()).generateDatePlan();
        for(int i = 0; i < items.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("type", i);
            JSONArray ingredient = new JSONArray();
            JSONArray recipe = new JSONArray();
            obj.put("ingredient", ingredient);
            obj.put("recipe", recipe);
            dish.put(obj);
        }
        params.put("dish", dish);
        PostRequest request = new PostRequest(FrServerConfig.getUpdatePlanUrl(), FrApplication.getInstance().getToken(), params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                Toast.makeText(getActivity(), "create empty plan!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        FrRequest.getInstance().request(request);
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
            plan_name.setText(datePlan.getPlan_name().equals("personal plan")?"自定义计划":datePlan.getPlan_name());
            items = datePlan.getItems();
            if(pointer > 0)
                adapter.setData(items, datePlan.getPlan_name().equals("personal plan")?true:false, false);
            else
                adapter.setData(items, datePlan.getPlan_name().equals("personal plan")?true:false, true);
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
