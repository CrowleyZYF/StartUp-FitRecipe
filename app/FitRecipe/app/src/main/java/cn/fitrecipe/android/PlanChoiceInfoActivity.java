package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.PlanDetailViewPagerAdapter;
import cn.fitrecipe.android.Adpater.PlanInfoViewPagerAdapter;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.UI.PlanDetailViewPager;
import cn.fitrecipe.android.UI.PlanScrollView;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.PlanAuthor;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.SeriesPlan;
import cn.fitrecipe.android.function.JsonParseHelper;
import cn.fitrecipe.android.function.RequestErrorHelper;
import me.relex.circleindicator.CircleIndicator;
import pl.tajchert.sample.DotsTextView;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class PlanChoiceInfoActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private PlanScrollView info_container;

    private ViewPager planInfoViewPager;
    private PlanInfoViewPagerAdapter planInfoViewPagerAdapter;
    private CircleIndicator planInfoIndicator;

    private LinearLayout header;
    private TextView header_name, plan_day, plan_calories;
    private ImageView back_btn;
    private ImageView author_btn;
    private ImageView nutrition_btn;

    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;


    private LinearLayout choice_info_background;
    private PlanDetailViewPager planDetailViewPager;
    private PlanDetailViewPagerAdapter planDetailViewPagerAdapter;
    private ImageView prev_day_btn;
    private ImageView next_day_btn;
    private SeriesPlan plan;

    private int nowY;
    private boolean isUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_choice_info);

        int plan_id =  getIntent().getIntExtra("plan_id", 0);
        isUsed = getIntent().getBooleanExtra("isUsed", false);
        initView();
        getData(plan_id);
    }

    private void getData(int id) {
        String url = FrServerConfig.getPlanDetails(id);
        GetRequest request = new GetRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if (res != null && res.has("data")) {
                    try {
                        JSONObject data = res.getJSONObject("data");
                        processData(data);
                        hideLoading(false, "");
                        initData();
                        initEvent();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                RequestErrorHelper.toast(PlanChoiceInfoActivity.this, volleyError);
            }
        });
        FrRequest.getInstance().request(request);

    }

    private void processData(JSONObject data) throws JSONException{

        plan = JsonParseHelper.getSeriesPlanFromJson(data);
        plan.setIsUsed(isUsed);
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            //Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }else{
            info_container.setVisibility(View.VISIBLE);
            info_container.smoothScrollTo(0, 0);
        }
    }

    private void initView() {
        info_container = (PlanScrollView) findViewById(R.id.info_container);

        planInfoViewPager = (ViewPager) findViewById(R.id.choice_intro_viewpager);
        planInfoIndicator = (CircleIndicator) findViewById(R.id.choice_intro_indicator);

        header = (LinearLayout) findViewById(R.id.header);
        header.setBackgroundColor(getResources().getColor(R.color.transparent));
        header_name = (TextView) findViewById(R.id.header_name_text);
        plan_day = (TextView) findViewById(R.id.plan_day);
        plan_calories = (TextView) findViewById(R.id.plan_calories);
        header_name.setText("计划详情");
        back_btn = (ImageView) findViewById(R.id.left_btn);
        back_btn.setBackgroundColor(getResources().getColor(R.color.transparent));
        back_btn.setImageResource(R.drawable.icon_back_white);
        author_btn = (ImageView) findViewById(R.id.right_btn);
        author_btn.setBackgroundColor(getResources().getColor(R.color.transparent));
        author_btn.setImageResource(R.drawable.icon_user);
        author_btn.setVisibility(View.GONE);
        planDetailViewPager = (PlanDetailViewPager) findViewById(R.id.plan_detail);
        prev_day_btn = (ImageView) findViewById(R.id.prev_day_btn);
        next_day_btn = (ImageView) findViewById(R.id.next_day_btn);
        nutrition_btn = (ImageView) findViewById(R.id.nutrition_btn);
        loadingInterface = (LinearLayout)findViewById(R.id.loading_interface);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);

        choice_info_background = (LinearLayout) findViewById(R.id.choice_info_background);
    }

    private void initData() {
        plan_day.setText(1 + "/" + plan.getTotal_days());
        plan_calories.setText(Math.round(plan.getDatePlans().get(0).getTotalCalories())+"");
        planDetailViewPagerAdapter = new PlanDetailViewPagerAdapter(this, plan);
        planDetailViewPager.setAdapter(planDetailViewPagerAdapter);
        planInfoViewPagerAdapter = new PlanInfoViewPagerAdapter(this, plan);
        planInfoViewPager.setAdapter(planInfoViewPagerAdapter);
        planInfoIndicator.setViewPager(planInfoViewPager);
        FrApplication.getInstance().getMyImageLoader().displayImage(choice_info_background, plan.getCover());
    }

    private void initEvent() {
        planInfoIndicator.setOnPageChangeListener(this);

        info_container.setOnBorderListener(new PlanScrollView.OnBorderListener(){

            @Override
            public void onBottom() {

            }

            @Override
            public void onTop() {
                header.setBackgroundColor(getResources().getColor(R.color.transparent));
            }

            @Override
            public void onScroll() {
                int scrollY=info_container.getScrollY();
                int transparent = 255/80*scrollY;
                if(transparent>255) {
                    transparent=255;
                }
                header.setBackgroundColor(Color.argb(transparent, 73, 189, 204));
            }
        });
        back_btn.setOnClickListener(this);
        author_btn.setOnClickListener(this);
        nutrition_btn.setOnClickListener(this);
        prev_day_btn.setOnClickListener(this);
        next_day_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:{
                Intent intent = new Intent();
                intent.putExtra("plan_id", plan.getId());
                intent.putExtra("isUsed", plan.isUsed());
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
            case R.id.nutrition_btn:{
                Intent intent = new Intent(this, PlanNutritionActivity.class);
                intent.putExtra("plan", plan);
                intent.putExtra("plan_day", plan_day.getText().toString());
                startActivity(intent);
                break;
            }
            case R.id.prev_day_btn:{
                goPrev();
                break;
            }
            case R.id.next_day_btn:{
                goNext();
                break;
            }
        }
    }

    public void goNext(){
        nowY = info_container.getScrollY();
        planDetailViewPager.setCurrentItem(planDetailViewPager.getCurrentItem()+1, true);
        plan_day.setText((planDetailViewPager.getCurrentItem()+1) + "/" + plan.getTotal_days());
        plan_calories.setText(Math.round(plan.getDatePlans().get(planDetailViewPager.getCurrentItem()).getTotalCalories())+"");
        info_container.smoothScrollTo(0, nowY);
    }

    public void goPrev(){
        nowY = info_container.getScrollY();
        planDetailViewPager.setCurrentItem(planDetailViewPager.getCurrentItem()-1, true);
        plan_day.setText((planDetailViewPager.getCurrentItem()+1) + "/" + plan.getTotal_days());
        info_container.smoothScrollTo(0, nowY);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
