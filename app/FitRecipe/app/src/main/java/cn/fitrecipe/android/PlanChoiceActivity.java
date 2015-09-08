package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.fitrecipe.android.Adpater.PlanCardAdapter;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.UI.SlidingMenu;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.DayPlan;
import cn.fitrecipe.android.entity.PlanItem;
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
        initData();
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

    private void initData() {
        List<SeriesPlan> plans = new ArrayList<>();
        SeriesPlan plan = new SeriesPlan();
        plan.setId(1);
        plan.setName("低碳减肥法");
        plan.setDays(2);
        plan.setAuthor_avatar("http://ww3.sinaimg.cn/bmiddle/473dc466jw1evlxreqkm0j20zk0k2774.jpg");
        plan.setBackground("http://ww3.sinaimg.cn/bmiddle/473dc466jw1evlxreqkm0j20zk0k2774.jpg");
        plan.setAuthor_type(0);
        plan.setAuthor_name("姐姐");
        plan.setIsUsed(false);
        plan.setType(0);
        plan.setJoin(100);
        plan.setDelicious_rank(2);
        plan.setHard_rank(1);
        plan.setLabel(0);
        plan.setIntro("很有意思的饮食方案，让你能够在最短的时间里减肥减好多，减脂无敌了");
        plan.setDesc("1. 以低GI食物为主\n2. 以低GI食物为主\n3. 以低GI食物为主");
        plan.setAuthor_intro("作为一个酸奶重度依赖者，在写这篇文章之前，我需要先告诉大家，这篇文章，这个话题。");
        plan.setAuthor_years(3);
        plan.setAuthor_fatratio(21);

        //
        ArrayList<DayPlan> dayPlans = new ArrayList<>();
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(date);
        DayPlan dayPlan1 = FrDbHelper.getInstance(this).getDayPlan(str);
        DayPlan dayPlan2 = FrDbHelper.getInstance(this).getDayPlan(str);
        dayPlans.add(dayPlan1);
        dayPlans.add(dayPlan2);
        plan.setDayplans(dayPlans);
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
