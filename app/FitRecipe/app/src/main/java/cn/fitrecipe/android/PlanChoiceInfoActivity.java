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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.PlanDetailViewPagerAdapter;
import cn.fitrecipe.android.Adpater.PlanInfoViewPagerAdapter;
import cn.fitrecipe.android.UI.PlanDetailViewPager;
import cn.fitrecipe.android.UI.PlanScrollView;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.PlanDetail;
import cn.fitrecipe.android.entity.PlanDetailItem;
import cn.fitrecipe.android.entity.PlanItem;
import cn.fitrecipe.android.entity.Series;
import cn.fitrecipe.android.entity.SeriesPlan;
import me.relex.circleindicator.CircleIndicator;

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
    private ImageView nutrition_btn;

    private PlanDetailViewPager planDetailViewPager;
    private PlanDetailViewPagerAdapter planDetailViewPagerAdapter;
    private ImageView prev_day_btn;
    private ImageView next_day_btn;
    private SeriesPlan plan;

    private int nowY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_choice_info);

        plan = (SeriesPlan) getIntent().getSerializableExtra("plan");

        initView();
        initData();
        initEvent();
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
        nutrition_btn = (ImageView) findViewById(R.id.right_btn);
        nutrition_btn.setBackgroundColor(getResources().getColor(R.color.transparent));
        nutrition_btn.setImageResource(R.drawable.icon_nutrition);
        planDetailViewPager = (PlanDetailViewPager) findViewById(R.id.plan_detail);
        prev_day_btn = (ImageView) findViewById(R.id.prev_day_btn);
        next_day_btn = (ImageView) findViewById(R.id.next_day_btn);
        plan_day.setText(1 + "/" + plan.getDays());
        plan_calories.setText(Math.round(plan.getDatePlans().get(0).getTotalCalories())+"");
    }

    private void initData() {
        planDetailViewPagerAdapter = new PlanDetailViewPagerAdapter(this, plan);
        planDetailViewPager.setAdapter(planDetailViewPagerAdapter);
        planInfoViewPagerAdapter = new PlanInfoViewPagerAdapter(this, plan);
        planInfoViewPager.setAdapter(planInfoViewPagerAdapter);
        planInfoIndicator.setViewPager(planInfoViewPager);
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
        nutrition_btn.setOnClickListener(this);
        prev_day_btn.setOnClickListener(this);
        next_day_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:{
                finish();
                break;
            }
            case R.id.right_btn:{
                Intent intent = new Intent(this, PlanNutritionActivity.class);
                intent.putExtra("plan", plan);
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
        plan_day.setText((planDetailViewPager.getCurrentItem()+1) + "/" + plan.getDays());
        plan_calories.setText(Math.round(plan.getDatePlans().get(planDetailViewPager.getCurrentItem()).getTotalCalories())+"");
        info_container.smoothScrollTo(0, nowY);
    }

    public void goPrev(){
        nowY = info_container.getScrollY();
        planDetailViewPager.setCurrentItem(planDetailViewPager.getCurrentItem()-1, true);
        plan_day.setText((planDetailViewPager.getCurrentItem()+1) + "/" + plan.getDays());
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
