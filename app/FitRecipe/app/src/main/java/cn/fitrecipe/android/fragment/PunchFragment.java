package cn.fitrecipe.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import cn.fitrecipe.android.UI.BorderScrollView;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.function.Common;
import cn.fitrecipe.android.function.JsonParseHelper;
import cn.fitrecipe.android.function.RequestErrorHelper;
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
    private BorderScrollView info_container;
    private Map<String, DatePlan> map;
    private List<DatePlan> datePlans;
    private int total;
    private int start = 0, num = 15;

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
        info_container = (BorderScrollView) view.findViewById(R.id.info_container);
        info_container.setOnBorderListener(new BorderScrollView.OnBorderListener() {
            @Override
            public void onBottom() {
                getData();
            }

            @Override
            public void onTop() {

            }
        });
    }

    private void getData() {
        final String today = Common.getDate();
        String url = FrServerConfig.getPunchListUrl(Common.dateFormat(Common.getSomeDay(today, - start - num)), Common.dateFormat(Common.getSomeDay(today, -start)));
        GetRequest request = new GetRequest(url, FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if (res != null && res.has("data")) {
                    try {
                        JSONObject data = res.getJSONObject("data");
                        if (datePlans==null)
                            total = data.getInt("count");
                        processData(data.getJSONArray("punchs"));
                        if(start == 0)
                            hideLoading(false, "");
                        else {
                            if (data.getJSONArray("punchs").length() == 0) {
                                info_container.setNoMore();
                                Toast.makeText(getActivity(), "没有更多记录了", Toast.LENGTH_SHORT).show();
                            }
                            info_container.setCompleteMore();
                        }
                        if(data.length() > 0)
                            start += num;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                RequestErrorHelper.toast(getActivity(), volleyError);
                getPunchDateFromLocal();
            }
        });
        FrRequest.getInstance().request(request);
    }

    private void processData(JSONArray data) throws JSONException {
        if(data != null) {
            map = new TreeMap<>();
            for(int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);
                DatePlanItem item = new DatePlanItem();
                String date = obj.getString("date");
                //确定餐的类型
                item.setPunchId(obj.getInt("id"));
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
                    component.setCalories(singleingredient_set.getJSONObject(k).getJSONObject("ingredient").getJSONObject("nutrition_set").getJSONObject("Energy").getDouble("amount"));//设置卡路里
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

        if(datePlans==null){
            datePlans = new ArrayList<>();
        }
        Set<String> keyset = map.keySet();
        Iterator<String> iterator = keyset.iterator();
        while(iterator.hasNext()) {
            datePlans.add(map.get(iterator.next()));
        }
        Collections.sort(datePlans, new Comparator<DatePlan>() {
            @Override
            public int compare(DatePlan lhs, DatePlan rhs) {
                return -Common.CompareDate(lhs.getDate(), rhs.getDate());
            }
        });
        int x = total;
        for(int i = 0; i < datePlans.size(); i++) {
            List<DatePlanItem> tmp = datePlans.get(i).getItems();
            for(int j = tmp.size()-1; j >= 0; j--)
                if(tmp.get(j).isPunch())
                    tmp.get(j).setTh(x--);
            datePlans.get(i).setItems(tmp);
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
            //Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }
        info_container.setVisibility(View.VISIBLE);
        info_container.smoothScrollTo(0, 0);
    }
}
