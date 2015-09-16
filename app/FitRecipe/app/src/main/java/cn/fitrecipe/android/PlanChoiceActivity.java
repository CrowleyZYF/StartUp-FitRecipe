package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import cn.fitrecipe.android.dao.PlanInUseDao;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.DayPlan;
import cn.fitrecipe.android.entity.Ingredient;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.PlanInUse;
import cn.fitrecipe.android.entity.PlanItem;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.entity.Series;
import cn.fitrecipe.android.entity.SeriesPlan;
import pl.tajchert.sample.DotsTextView;

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

    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;
    private ScrollView scrollView;

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

        loadingInterface = (LinearLayout)findViewById(R.id.loading_interface);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        mRightMenu = (SlidingMenu) findViewById(R.id.container_layout);
    }


    private void processData(JSONObject data) throws JSONException {
        Recipe recipe = Recipe.fromJson(data.toString());
        initData(recipe);
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }else{
            scrollView.setVisibility(View.VISIBLE);
            scrollView.smoothScrollTo(0, 0);
        }
    }

    private void getData() {
        GetRequest request = new GetRequest(FrServerConfig.getRecipeDetails("8"), FrApplication.getInstance().getToken(), new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if (res != null && res.has("data")) {
                    try {
                        JSONObject data = res.getJSONObject("data");
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
                if (volleyError != null && volleyError.networkResponse != null) {
                    hideLoading(true, getResources().getString(R.string.network_error));
                    int statusCode = volleyError.networkResponse.statusCode;
                    if (statusCode == 404) {
                        Toast.makeText(PlanChoiceActivity.this, "食谱不存在！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        FrRequest.getInstance().request(request);
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
        ArrayList<DatePlan> datePlans = new ArrayList<>();
        DatePlan datePlan = new DatePlan();
        List<DatePlanItem> items = new ArrayList<>();
        DatePlanItem item= new DatePlanItem();
        PlanComponent component = PlanComponent.getPlanComponentFromRecipe(recipe, 100);
        item.addContent(component);
        item.setImageCover("http://ww1.sinaimg.cn/thumbnail/81f8b0e2gw1evwcdxbiumj20c7077q3x.jpg");
        item.setType("breakfast");
        items.add(item);

        DatePlanItem item1 = new DatePlanItem();
        PlanComponent component1 = PlanComponent.getPlanComponentFromRecipe(recipe, 200);
        item1.addContent(component1);
        item1.setImageCover("http://ww1.sinaimg.cn/thumbnail/81f8b0e2gw1evwcdxbiumj20c7077q3x.jpg");
        item1.setType("add_meal_01");
        items.add(item1);


        DatePlanItem item2= new DatePlanItem();
        PlanComponent component2 = PlanComponent.getPlanComponentFromRecipe(recipe, 300);
        item2.addContent(component2);
        item2.setImageCover("http://ww1.sinaimg.cn/thumbnail/81f8b0e2gw1evwcdxbiumj20c7077q3x.jpg");
        item2.setType("lunch");
        items.add(item2);


        DatePlanItem item3= new DatePlanItem();
        PlanComponent component3 = PlanComponent.getPlanComponentFromRecipe(recipe, 400);
        item3.addContent(component3);
        item3.setImageCover("http://ww1.sinaimg.cn/thumbnail/81f8b0e2gw1evwcdxbiumj20c7077q3x.jpg");
        item3.setType("add_meal_02");
        items.add(item3);


        DatePlanItem item4 = new DatePlanItem();
        PlanComponent component4 = PlanComponent.getPlanComponentFromRecipe(recipe, 500);
        item4.addContent(component4);
        item4.setImageCover("http://ww1.sinaimg.cn/thumbnail/81f8b0e2gw1evwcdxbiumj20c7077q3x.jpg");
        item4.setType("supper");
        items.add(item4);
//

        DatePlanItem item5 = new DatePlanItem();
        PlanComponent component5 = PlanComponent.getPlanComponentFromRecipe(recipe, 600);
        item5.addContent(component5);
        item5.setImageCover("http://ww1.sinaimg.cn/thumbnail/81f8b0e2gw1evwcdxbiumj20c7077q3x.jpg");
        item5.setType("add_meal_03");
        items.add(item5);

        datePlan.setItems(items);

//        DayPlan dayPlan1 = FrDbHelper.getInstance(this).getDayPlan(str);
//        DayPlan dayPlan2 = FrDbHelper.getInstance(this).getDayPlan(str);
        datePlans.add(datePlan);
        datePlans.add(datePlan);

        //
        PlanInUseDao dao = new PlanInUseDao(this);
        PlanInUse planInUse = dao.getPlanInUse();
        if(planInUse == null)
            plan.setIsUsed(false);
        else
            plan.setIsUsed(planInUse.getIsUsed()==1?true:false);

        plan.setDatePlans(datePlans);
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
