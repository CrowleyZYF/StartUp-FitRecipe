package cn.fitrecipe.android.function;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.PlanAuthor;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.Series;
import cn.fitrecipe.android.entity.SeriesPlan;

/**
 * Created by wk on 2015/10/9.
 */
public class JsonParseHelper {

    public static ArrayList<Nutrition>  getNutritionSetFromJson(JSONObject json) throws JSONException {
        ArrayList<Nutrition> nutrition_set = new ArrayList<>();
        String[] names = new String[]{"Water", "Protein", "Total lipid (fat)", "Carbohydrate, by difference", "Fiber, total dietary", "Sodium, Na", "Vitamin C, total ascorbic acid", "Vitamin D", "Fatty acids", "Cholesterol"};
        for (int i = 0; i < names.length; i++) {
            JSONObject jnutrition = json.getJSONObject(names[i]);
            Nutrition nutrition = new Nutrition();
            if(jnutrition.has("name"))
                nutrition.setName(jnutrition.getString("name"));
            if(jnutrition.has("amount"))
                nutrition.setAmount(jnutrition.getDouble("amount"));
            if(jnutrition.has("unit"))
                nutrition.setUnit(jnutrition.getString("unit"));
            nutrition_set.add(nutrition);
        }
        return nutrition_set;
    }

    public static SeriesPlan getSeriesPlanFromJson(JSONObject data) throws JSONException {
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
        plan.setBrief(data.getString("brief"));
        plan.setCover(data.getString("cover"));

        //计划作者
        if(!data.get("author").toString().equals("null")) {
            JSONObject author_json = data.getJSONObject("author");
            plan.setAuthor(new Gson().fromJson(author_json.toString(), PlanAuthor.class));
        }
        //好多天的计划
        ArrayList<DatePlan> datePlans = new ArrayList<>();

        JSONArray routine_set = data.getJSONArray("routine_set");

        for(int i  = 0; i < routine_set.length(); i++) {
            JSONObject routine = routine_set.getJSONObject(i);

            //某一天的计划
            DatePlan datePlan = new DatePlan();
            datePlan.setVideo(routine.getString("video"));
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
                    component.setCalories(singleingredient_set.getJSONObject(k).getJSONObject("ingredient").getJSONObject("nutrition_set").getJSONObject("Energy").getDouble("amount"));//设置卡路里
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
            datePlan.setItems(items);//添加某一天的一日五餐
            datePlans.add(datePlan);//添加计划中的某一天
        }

        plan.setDatePlans(datePlans);//设置计划中的好几天
        return plan;
    }
}
