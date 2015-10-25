package cn.fitrecipe.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.Http.PostRequest;
import cn.fitrecipe.android.entity.BasketRecord;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.PunchRecord;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.function.Common;
import cn.fitrecipe.android.function.JsonParseHelper;
import cn.fitrecipe.android.function.RequestErrorHelper;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class AddToPlanActivity extends Activity implements View.OnClickListener {

    private TextView nowDate_0_btn;
    private TextView nowDate_1_btn;
    private TextView nowDate_2_btn;
    private TextView nowDate_3_btn;
    private TextView nowDate_4_btn;
    private TextView nowDate_5_btn;
    private ArrayList<TextView> date_btn_array;

    private TextView nowMeal_0_btn;
    private TextView nowMeal_1_btn;
    private TextView nowMeal_2_btn;
    private TextView nowMeal_3_btn;
    private TextView nowMeal_4_btn;
    private TextView nowMeal_5_btn;
    private ArrayList<TextView> meal_btn_array;

    private ArrayList<String> date_text_array;
    private String[] meal_text_array = {"早餐", "上午加餐", "午餐", "下午加餐", "晚餐", "夜宵"};
    private Button add_to_plan_btn;
    private int choosen_date = 0;
    private int choosen_meal = 0;
    private int adjust_unit_weight;//每次调整的基本单位
    private int now_weight;//用户添加时的重量

    private TextView minus_btn;
    private TextView add_btn;
    private TextView weight_text;

    private ArrayList<DatePlanItem> items;
    private ProgressDialog pd;
    private int recipe_id;

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_addtoplan);

        now_weight = getIntent().getIntExtra("amount", 0);
        recipe_id = getIntent().getIntExtra("id", 0);
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        adjust_unit_weight = now_weight / 4;
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        setDate();
    }

    private void initEvent() {
        setClickEvent(date_btn_array);
        setClickEvent(meal_btn_array);
        add_to_plan_btn.setOnClickListener(this);
        minus_btn.setOnClickListener(this);
        add_btn.setOnClickListener(this);
    }

    private void initView() {
        nowDate_0_btn = (TextView) findViewById(R.id.nowDate_0);
        nowDate_1_btn = (TextView) findViewById(R.id.nowDate_1);
        nowDate_2_btn = (TextView) findViewById(R.id.nowDate_2);
        nowDate_3_btn = (TextView) findViewById(R.id.nowDate_3);
        nowDate_4_btn = (TextView) findViewById(R.id.nowDate_4);
        nowDate_5_btn = (TextView) findViewById(R.id.nowDate_5);
        date_btn_array = new ArrayList<>();
        date_btn_array.add(nowDate_0_btn);
        date_btn_array.add(nowDate_1_btn);
        date_btn_array.add(nowDate_2_btn);
        date_btn_array.add(nowDate_3_btn);
        date_btn_array.add(nowDate_4_btn);
        date_btn_array.add(nowDate_5_btn);

        nowMeal_0_btn = (TextView) findViewById(R.id.nowMeal_0);
        nowMeal_1_btn = (TextView) findViewById(R.id.nowMeal_1);
        nowMeal_2_btn = (TextView) findViewById(R.id.nowMeal_2);
        nowMeal_3_btn = (TextView) findViewById(R.id.nowMeal_3);
        nowMeal_4_btn = (TextView) findViewById(R.id.nowMeal_4);
        nowMeal_5_btn = (TextView) findViewById(R.id.nowMeal_5);
        meal_btn_array = new ArrayList<>();
        meal_btn_array.add(nowMeal_0_btn);
        meal_btn_array.add(nowMeal_1_btn);
        meal_btn_array.add(nowMeal_2_btn);
        meal_btn_array.add(nowMeal_3_btn);
        meal_btn_array.add(nowMeal_4_btn);
        meal_btn_array.add(nowMeal_5_btn);

        add_to_plan_btn = (Button) findViewById(R.id.add_to_plan_btn);

        minus_btn = (TextView) findViewById(R.id.minus_btn);
        add_btn = (TextView) findViewById(R.id.add_btn);
        weight_text = (TextView) findViewById(R.id.weight_text);
        weight_text.setText(now_weight+"g");
    }

    private void setDate(){
        date_text_array = new ArrayList<>();
        for (int i = 0; i< date_btn_array.size(); i++){
            String date_text = Common.getAddDate(i);
            date_text_array.add(date_text);
            date_btn_array.get(i).setText(date_text);
        }
        date_btn_array.get(0).setText("今天");
    }

    private void resetDateBtn(ArrayList<TextView> btns){
        for(int i = 0; i< btns.size(); i++){
            btns.get(i).setTextColor(getResources().getColor(R.color.base_color));
            btns.get(i).setBackground(getResources().getDrawable(R.drawable.add_to_plan));
        }
    }

    private void setBtnChosen(TextView btn, boolean isDate){
        btn.setTextColor(getResources().getColor(R.color.white));
        btn.setBackground(getResources().getDrawable(R.drawable.add_to_plan_chosen));
        if (isDate)
            choosen_date = date_btn_array.indexOf(btn);
        else
            choosen_meal = meal_btn_array.indexOf(btn);
    }

    private void setClickEvent(ArrayList<TextView> btns){
        for (int i = 0; i < btns.size(); i++){
            btns.get(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nowDate_0:
            case R.id.nowDate_1:
            case R.id.nowDate_2:
            case R.id.nowDate_3:
            case R.id.nowDate_4:
            case R.id.nowDate_5:
                resetDateBtn(date_btn_array);
                setBtnChosen((TextView) v, true);
                break;
            case R.id.nowMeal_0:
            case R.id.nowMeal_1:
            case R.id.nowMeal_2:
            case R.id.nowMeal_3:
            case R.id.nowMeal_4:
            case R.id.nowMeal_5:
                resetDateBtn(meal_btn_array);
                setBtnChosen((TextView) v, false);
                break;
            case R.id.add_to_plan_btn:
                addToPlan();
                break;
            case R.id.minus_btn:
                if (now_weight > adjust_unit_weight){
                    now_weight -= adjust_unit_weight;
                    weight_text.setText(now_weight+" g");
                }else {
                    Common.errorDialog(this, "调整错误", "重量不能小于等于0").show();
                }
                break;
            case R.id.add_btn:
                now_weight += adjust_unit_weight;
                weight_text.setText(now_weight + " g");
                break;
            default:
                break;
        }
    }

    private void error() {
        Toast.makeText(this, date_text_array.get(choosen_date) + "  " + meal_text_array[choosen_meal] + "已打卡或在菜篮子中，不能添加到菜篮子", Toast.LENGTH_SHORT).show();
    }

    private void addToPlan() {
        String date = Common.getSomeDay(Common.getDate(), choosen_date);
        boolean flag = true;
        if(FrApplication.getInstance().getBasketData() != null) {
            List<BasketRecord> brs = FrApplication.getInstance().getBasketData().get(date);
            if(brs != null) {
                String type = "";
                switch (choosen_meal) {
                    case 0:
                        type = "breakfast";
                        break;
                    case 1:
                        type = "add_meal_01";
                        break;
                    case 2:
                        type = "lunch";
                        break;
                    case 3:
                        type = "add_meal_02";
                        break;
                    case 4:
                        type = "supper";
                        break;
                    case 5:
                        type = "add_meal_03";
                        break;

                }
                for (int i = 0; i < brs.size(); i++)
                    if (brs.get(i).getType().equals(type))
                        flag = false;
            }
        }
        if(FrApplication.getInstance().getPunchData() != null) {
            List<PunchRecord> prs = FrApplication.getInstance().getPunchData().get(date);
            if (prs != null) {
                String type = "";
                switch (choosen_meal) {
                    case 0:
                        type = "breakfast";
                        break;
                    case 1:
                        type = "add_meal_01";
                        break;
                    case 2:
                        type = "lunch";
                        break;
                    case 3:
                        type = "add_meal_02";
                        break;
                    case 4:
                        type = "supper";
                        break;
                    case 5:
                        type = "add_meal_03";
                        break;

                }
                for (int i = 0; i < prs.size(); i++)
                    if (prs.get(i).getType().equals(type))
                        flag = false;
            }
        }
        if(!flag) {
            error();
            return;
        }

        String newDate = Common.dateFormat(date);
        pd = ProgressDialog.show(AddToPlanActivity.this, "", "获取现有计划...", true, false);
        pd.setCanceledOnTouchOutside(false);
        GetRequest request = new GetRequest(FrServerConfig.getDatePlanUrl(newDate, newDate), FrApplication.getInstance().getToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        if(res != null && res.has("data")) {
                            int plan_id = -1;
                            try {
                                JSONObject data = res.getJSONObject("data");
                                if(data.has("calendar") && data.getJSONArray("calendar").length() > 0) {
                                    plan_id = data.getJSONArray("calendar").getJSONObject(0).getJSONObject("plan").getInt("id");
                                }
                                postprpcess(plan_id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        RequestErrorHelper.toast(AddToPlanActivity.this, volleyError);
                        if(pd.isShowing())
                            pd.dismiss();
                    }
                });
        FrRequest.getInstance().request(request);
    }

    private void postprpcess(int plan_id) {
        if(plan_id == -1) {
            items = Common.generateDatePlan(Common.getDate());
            try {
                update(plan_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else {
            requestPlanDetails(plan_id);
        }
    }

    private void requestPlanDetails(final int plan_id) {
        pd.setMessage("获取计划详情...");
        GetRequest request = new GetRequest(FrServerConfig.getPlanDetails(plan_id), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        if (res != null && res.has("data")) {
                            try {
                                JSONObject data = res.getJSONObject("data");
                                parsePlanJson(data);
                                //
                                update(plan_id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        RequestErrorHelper.toast(AddToPlanActivity.this, volleyError);
                        if(pd.isShowing())
                            pd.dismiss();
                    }
                });
        FrRequest.getInstance().request(request);
    }

    //处理计划的JSON
    private void parsePlanJson(JSONObject data) throws JSONException {
        JSONArray routine_set = data.getJSONArray("routine_set");
        for(int i  = 0; i < routine_set.length(); i++) {
            JSONObject routine = routine_set.getJSONObject(i);
            JSONArray dish_set = routine.getJSONArray("dish_set");
            //某一天的一日五餐
            items = new ArrayList<>();
            //列表项
            for(int j = 0; j < dish_set.length(); j++) {
                //具体某一餐
                DatePlanItem item = new DatePlanItem();
                //确定餐的类型
                int type = dish_set.getJSONObject(j).getInt("type");
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
                //获取计划中食材
                JSONArray singleingredient_set = dish_set.getJSONObject(j).getJSONArray("singleingredient_set");
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
                JSONArray singlerecipe_set = dish_set.getJSONObject(j).getJSONArray("singlerecipe_set");
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
                items.add(item);//添加早餐
            }
        }
    }

    public void update(final int plan_id) throws JSONException {
        final PlanComponent component = PlanComponent.getPlanComponentFromRecipe(recipe, now_weight);
        component.setType(1);
        component.setId(recipe_id);
        component.setAmount(now_weight);
        ArrayList<PlanComponent> componentList = items.get(choosen_meal).getComponents() ;
        if(componentList == null)
            componentList = new ArrayList<>();
        componentList.add(component);
        items.get(choosen_meal).setComponents(componentList);

        JSONObject params = new JSONObject();
        JSONArray dish = new JSONArray();
        for(int i = 0; i < items.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("type", i);
            JSONArray ingredient = new JSONArray();
            JSONArray recipe = new JSONArray();
            ArrayList<PlanComponent> components = items.get(i).getComponents();
            if(components != null) {
                for(int j = 0; j <components.size(); j++) {
                    JSONObject obj1 = new JSONObject();
                    obj1.put("id", components.get(j).getId());
                    obj1.put("amount", components.get(j).getAmount());
                    if(components.get(j).getType() == 1)
                        recipe.put(obj1);
                    else
                        ingredient.put(obj1);
                }
            }
            obj.put("ingredient", ingredient);
            obj.put("recipe", recipe);
            dish.put(obj);
        }
        params.put("dish", dish);
        if(plan_id != -1)
            params.put("id", plan_id);
        else {
            params.put("joined_date", Common.getSomeDay(Common.getDate(), choosen_date));
        }
        pd.setMessage("添加到计划...");
        PostRequest request = new PostRequest(FrServerConfig.getUpdatePlanUrl(), FrApplication.getInstance().getToken(), params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                Toast.makeText(AddToPlanActivity.this, "更新自定义计划成功！", Toast.LENGTH_SHORT).show();
                FrApplication.getInstance().setComponent(component);
                FrApplication.getInstance().setDate(Common.getSomeDay(Common.getDate(), choosen_date));
                FrApplication.getInstance().setType(choosen_meal);
                FrApplication.getInstance().setPlan_id(plan_id);
                //Toast.makeText(AddToPlanActivity.this, "更新自定义计划成功！", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                RequestErrorHelper.toast(AddToPlanActivity.this, volleyError);
                if(pd.isShowing())
                    pd.dismiss();
            }
        });
        FrRequest.getInstance().request(request);
    }
}
