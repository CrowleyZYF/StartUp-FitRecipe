package cn.fitrecipe.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cn.fitrecipe.android.Adpater.PunchDayAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.function.Common;
import cn.fitrecipe.android.function.JsonParseHelper;
import pl.tajchert.sample.DotsTextView;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class PunchFragment extends Fragment
{
    private RecyclerView punchRecordRecyclerView;
    private PunchDayAdapter punchDayAdapter;
    private RecyclerViewLayoutManager punchRecordLayoutManager;

    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;
    private ScrollView info_container;
    private Map<String, DatePlan> map;
    private List<DatePlan> datePlans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_record_punch, container, false);

        initView(view);
        getData();

        return view;
    }

    private void initView(View view) {
        punchRecordRecyclerView = (RecyclerView) view.findViewById(R.id.punch_record);
        punchRecordRecyclerView.setHasFixedSize(true);
        punchRecordLayoutManager = new RecyclerViewLayoutManager(this.getActivity());
        punchRecordLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        punchRecordRecyclerView.setLayoutManager(punchRecordLayoutManager);

        loadingInterface = (LinearLayout)view.findViewById(R.id.loading_interface);
        dotsTextView = (DotsTextView) view.findViewById(R.id.dots);
        info_container = (ScrollView) view.findViewById(R.id.info_container);
    }

    private void getData() {

        if(!Common.isOpenNetwork(getActivity())) {
            getPunchDateFromLocal();
        }else {
            String today = Common.getDate();
            String url = FrServerConfig.getPunchListUrl(Common.dateFormat(Common.getSomeDay(today, -15)), Common.dateFormat(today));
            GetRequest request = new GetRequest(url, FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    if (res != null && res.has("data")) {
                        try {
                            JSONArray data = res.getJSONArray("data");
                            processData(data);
                            hideLoading(false, "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    getPunchDateFromLocal();
                }
            });
            FrRequest.getInstance().request(request);
        }
    }

    private void processData(JSONArray data) throws JSONException {
        if(data != null) {
            map = new TreeMap<>();
            for(int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);
                DatePlanItem item = new DatePlanItem();
                String date = obj.getString("date");
                //确定餐的类型
                int type = obj.getInt("type");
                switch (type) {
                    case 0:
                        item.setType("breakfast");
                        item.setDefaultImageCover("drawable://" + R.drawable.breakfast);
                        break;
                    case 1:
                        item.setType("add_meal_01");
                        item.setDefaultImageCover("drawable://" + R.drawable.add_meal_01);
                        break;
                    case 2:
                        item.setType("lunch");
                        item.setDefaultImageCover("drawable://" + R.drawable.lunch);
                        break;
                    case 3:
                        item.setType("add_meal_02");
                        item.setDefaultImageCover("drawable://" + R.drawable.add_meal_02);
                        break;
                    case 4:
                        item.setType("supper");
                        item.setDefaultImageCover("drawable://" + R.drawable.dinner);
                        break;
                    case 5:
                        item.setType("add_meal_03");
                        item.setDefaultImageCover("drawable://" + R.drawable.add_meal_03);
                        break;
                }
                item.setImageCover(obj.getString("img"));
                item.setDate(date);
                //获取计划中食材
                JSONArray singleingredient_set = obj.getJSONObject("dish").getJSONArray("singleingredient_set");
                for(int k = 0; k < singleingredient_set.length(); k++) {
                    PlanComponent component = new PlanComponent();
                    component.setType(0);//标记为食材
                    component.setId(singleingredient_set.getJSONObject(k).getJSONObject("ingredient").getInt("id"));
                    component.setName(singleingredient_set.getJSONObject(k).getJSONObject("ingredient").getString("name"));//设置名称
                    component.setNutritions(JsonParseHelper.getNutritionSetFromJson(singleingredient_set.getJSONObject(k).getJSONObject("ingredient").getJSONObject("nutrition_set")));//获取营养信息
                    component.setAmount(singleingredient_set.getJSONObject(k).getInt("amount"));//设置重量
                    component.setCalories(component.getAmount() * singleingredient_set.getJSONObject(k).getJSONObject("ingredient").getJSONObject("nutrition_set").getJSONObject("Energy").getDouble("amount") / 100);//设置卡路里
                    item.addContent(component);
                }
                //获取计划中食谱
                JSONArray singlerecipe_set = obj.getJSONObject("dish").getJSONArray("singlerecipe_set");
                for(int k = 0; k < singlerecipe_set.length(); k++) {
                    JSONObject json_recipe = singlerecipe_set.getJSONObject(k);
                    PlanComponent component = new PlanComponent();
                    component.setAmount(json_recipe.getInt("amount"));
                    component.setCalories(json_recipe.getJSONObject("recipe").getDouble("calories"));
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
                item.setIsPunch(true);
                DatePlan datePlan;
                if(map.containsKey(date)) {
                    datePlan = map.get(date);
                }else {
                    datePlan = new DatePlan();
                    datePlan.setDate(date);
                }
                List<DatePlanItem> items = datePlan.getItems();
                if(items == null) {
                    items = new ArrayList<>();
                }
                items.add(item);
                datePlan.setItems(items);
                map.put(date, datePlan);
            }
        }

        datePlans = new ArrayList<>();
        Set<String> keyset = map.keySet();
        Iterator<String> iterator = keyset.iterator();
        while(iterator.hasNext()) {
            datePlans.add(map.get(iterator.next()));
        }
        punchDayAdapter = new PunchDayAdapter(this.getActivity(), datePlans, FrApplication.getInstance().getReport());
        punchRecordRecyclerView.setAdapter(punchDayAdapter);
    }

    private void getPunchDateFromLocal() {
//        datePlans = FrDbHelper.getInstance(getActivity()).getPunchDatePlans();
        hideLoading(true, getResources().getString(R.string.network_error));
        if(punchDayAdapter == null) {
            punchDayAdapter = new PunchDayAdapter(this.getActivity(), datePlans, FrApplication.getInstance().getReport());
            punchRecordRecyclerView.setAdapter(punchDayAdapter);
        }else
            punchDayAdapter.notifyDataSetChanged();
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }
        info_container.setVisibility(View.VISIBLE);
        info_container.smoothScrollTo(0, 0);
    }
}
