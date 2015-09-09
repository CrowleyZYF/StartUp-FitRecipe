package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.fitrecipe.android.Adpater.PlanCardAdapter;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.UI.SlidingMenu;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.DayPlan;
import cn.fitrecipe.android.entity.Ingredient;
import cn.fitrecipe.android.entity.PlanItem;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.entity.Series;
import cn.fitrecipe.android.entity.SeriesPlan;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class PlanChoiceActivity extends Activity implements View.OnClickListener {

    private SlidingMenu mRightMenu;

    private TextView header_name;
    private ImageView back_btn;
    private ImageView filter_btn;
    private TextView sure_btn;

    private RecyclerView planChoiceRecyclerView;
    private PlanCardAdapter planCardAdapter;
    private RecyclerViewLayoutManager planChoiceLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_choice_container);

        initView();
//        initData();
        getData();
        initEvent();
    }

    private void initView() {
        header_name = (TextView) findViewById(R.id.header_name_text);
        header_name.setText(getResources().getString(R.string.recipe_plan));
        back_btn = (ImageView) findViewById(R.id.left_btn);
        back_btn.setImageResource(R.drawable.icon_back_white);
        filter_btn = (ImageView) findViewById(R.id.right_btn);
        filter_btn.setImageResource(R.drawable.icon_filter);
        sure_btn = (TextView) findViewById(R.id.filter_sure_btn);

        planChoiceRecyclerView = (RecyclerView) findViewById(R.id.plan_choice);
        planChoiceRecyclerView.setHasFixedSize(true);
        planChoiceLayoutManager = new RecyclerViewLayoutManager(this);
        planChoiceLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        planChoiceRecyclerView.setLayoutManager(planChoiceLayoutManager);

        mRightMenu = (SlidingMenu) findViewById(R.id.container_layout);
    }


    private void processData(JSONObject data) throws JSONException {
        Recipe recipe = Recipe.fromJson(data.toString());
        initData(recipe);
    }

    private void getData() {
        List<SeriesPlan> plans = FrDbHelper.getInstance(this).getSeriesPlans();
        if(plans != null && plans.size() > 0) {
            planCardAdapter = new PlanCardAdapter(this, plans);
            planChoiceRecyclerView.setAdapter(planCardAdapter);
        }else {
            GetRequest request = new GetRequest(FrServerConfig.getRecipeDetails("8"), FrApplication.getInstance().getToken(), new JSONObject(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    if (res != null && res.has("data")) {
                        try {
                            JSONObject data = res.getJSONObject("data");
                            processData(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (volleyError != null && volleyError.networkResponse != null) {
                        int statusCode = volleyError.networkResponse.statusCode;
                        if (statusCode == 404) {
                            Toast.makeText(PlanChoiceActivity.this, "食谱不存在！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            FrRequest.getInstance().request(request);
        }
    }
    private void initData(Recipe recipe) {
        List<SeriesPlan> plans = new ArrayList<>();
        SeriesPlan plan = new SeriesPlan();
        plan.setId(1);
        plan.setName("低碳减肥法");
        plan.setDays(2);
        plan.setAuthor_avatar("http://tp4.sinaimg.cn/1714487171/180/40036635794/1");
        plan.setBackground("http://ww1.sinaimg.cn/bmiddle/81f8b0e2gw1evv4r7gen8j20hq08rt9s.jpg");
        plan.setAuthor_type(0);
        plan.setAuthor_name("Lucy");
        plan.setIsUsed(false);
        plan.setJoin(100);
        plan.setDelicious_rank(2);
        plan.setHard_rank(1);
        plan.setLabel(0);
        plan.setType(1);
        plan.setIntro("很有意思的饮食方案，让你能够在最短的时间里减肥减好多，减脂无敌了");
        plan.setDesc("1. 以低GI食物为主\n2. 以低GI食物为主\n3. 以低GI食物为主");
        plan.setAuthor_intro("作为一个酸奶重度依赖者，在写这篇文章之前，我需要先告诉大家，这篇文章，这个话题。");
        plan.setAuthor_years(3);
        plan.setAuthor_fatratio(21);
        plan.setAuthor_type(1);
        plan.setAuthor_title("高级营养师");
        //
        ArrayList<DayPlan> dayPlans = new ArrayList<>();
        DayPlan dayPlan = new DayPlan();
        List<PlanItem> items = new ArrayList<>();
        PlanItem item= new PlanItem();
        recipe.setIncreWeight(100);
        item.addContent(recipe);
        item.setImageCover("http://ww1.sinaimg.cn/thumbnail/81f8b0e2gw1evwcdxbiumj20c7077q3x.jpg");
        item.setItemType(PlanItem.ItemType.BREAKFAST);
        items.add(item);
//
        PlanItem item1 = new PlanItem();
        recipe.setIncreWeight(100);
        item1.addContent(recipe);
        item1.setItemType(PlanItem.ItemType.ADDMEAL_01);
        item1.setImageCover("http://ww1.sinaimg.cn/thumbnail/81f8b0e2gw1evwcdxbiumj20c7077q3x.jpg");
        items.add(item1);
        //
        PlanItem item2 = new PlanItem();
        recipe.setIncreWeight(100);
        item2.addContent(recipe);
        item2.setItemType(PlanItem.ItemType.LUNCH);
        item2.setImageCover("http://ww1.sinaimg.cn/thumbnail/81f8b0e2gw1evwcdxbiumj20c7077q3x.jpg");
        items.add(item2);
        //
        PlanItem item3 = new PlanItem();
        recipe.setIncreWeight(100);
        item3.addContent(recipe);
        item3.setItemType(PlanItem.ItemType.ADDMEAL_02);
        item3.setImageCover("http://ww1.sinaimg.cn/thumbnail/81f8b0e2gw1evwcdxbiumj20c7077q3x.jpg");
        items.add(item3);
        //
        PlanItem item4 = new PlanItem();
        recipe.setIncreWeight(100);
        item4.addContent(recipe);
        item4.setItemType(PlanItem.ItemType.SUPPER);
        item4.setImageCover("http://ww1.sinaimg.cn/thumbnail/81f8b0e2gw1evwcdxbiumj20c7077q3x.jpg");
        items.add(item4);
        dayPlan.setPlanItems(items);

//        DayPlan dayPlan1 = FrDbHelper.getInstance(this).getDayPlan(str);
//        DayPlan dayPlan2 = FrDbHelper.getInstance(this).getDayPlan(str);
        dayPlans.add(dayPlan);


        DayPlan dayPlan1 = new DayPlan();
        List<PlanItem> items11 = new ArrayList<>();
        PlanItem item11= new PlanItem();
        recipe.setIncreWeight(100);
        item11.addContent(recipe);
        item11.setImageCover("http://ww4.sinaimg.cn/bmiddle/81f8b0e2gw1evwcbveq40j20b106imxt.jpg");
        item11.setItemType(PlanItem.ItemType.BREAKFAST);
        items11.add(item11);
//
        PlanItem item111 = new PlanItem();
        recipe.setIncreWeight(100);
        item111.addContent(recipe);
        item111.setImageCover("http://ww4.sinaimg.cn/bmiddle/81f8b0e2gw1evwcbveq40j20b106imxt.jpg");
        item111.setItemType(PlanItem.ItemType.ADDMEAL_01);
        items11.add(item111);
        //
        PlanItem item211 = new PlanItem();
        recipe.setIncreWeight(100);
        item211.addContent(recipe);
        item211.setItemType(PlanItem.ItemType.LUNCH);
        item211.setImageCover("http://ww4.sinaimg.cn/bmiddle/81f8b0e2gw1evwcbveq40j20b106imxt.jpg");
        items11.add(item211);
        //
        PlanItem item311 = new PlanItem();
        recipe.setIncreWeight(100);
        item311.addContent(recipe);
        item311.setItemType(PlanItem.ItemType.ADDMEAL_02);
        item311.setImageCover("http://ww4.sinaimg.cn/bmiddle/81f8b0e2gw1evwcbveq40j20b106imxt.jpg");
        items11.add(item311);
        //
        PlanItem item411 = new PlanItem();
        recipe.setIncreWeight(100);
        item411.addContent(recipe);
        item411.setItemType(PlanItem.ItemType.SUPPER);
        item411.setImageCover("http://ww4.sinaimg.cn/bmiddle/81f8b0e2gw1evwcbveq40j20b106imxt.jpg");
        items11.add(item411);
        dayPlan1.setPlanItems(items11);

//        DayPlan dayPlan1 = FrDbHelper.getInstance(this).getDayPlan(str);
//        DayPlan dayPlan2 = FrDbHelper.getInstance(this).getDayPlan(str);
        dayPlans.add(dayPlan1);
        plan.setDayplans(dayPlans);

        plan = FrDbHelper.getInstance(this).addSeriesPlan(plan);


        plans.add(plan);
        planCardAdapter = new PlanCardAdapter(this, plans);
        planChoiceRecyclerView.setAdapter(planCardAdapter);
    }



    private void initEvent() {
        filter_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.filter_sure_btn:
            case R.id.right_btn:
                mRightMenu.toggle();
                break;
            case R.id.left_btn:
                finish();
                break;
        }
    }
}
