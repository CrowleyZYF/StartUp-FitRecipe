package cn.fitrecipe.android;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qiniu.android.http.AsyncHttpClientMod;
import com.umeng.fb.FeedbackAgent;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.entity.PlanItem;
import cn.fitrecipe.android.fragment.IndexFragment;
import cn.fitrecipe.android.fragment.MeFragment;
import cn.fitrecipe.android.fragment.PlanFragment;
import cn.fitrecipe.android.service.GetHomeDataService;

public class MainActivity extends FragmentActivity implements OnClickListener
{
    private LinearLayout frTabIndex;
    private LinearLayout frTabMe;
    private LinearLayout frTabPlan;
    //private LinearLayout frTabKnowledge;

    private Fragment frIndexFragment;
    private Fragment frMeFragment;
    private Fragment frPlanFragment;
    //private Fragment frKnowledgeFragment;

    private ImageView left_btn;
    private ImageView right_btn;

    private int tab_index = 0;

    private List<LinearLayout> frTabs = new ArrayList<LinearLayout>();
    private View layout;

    private final String action = "cn.fitrecipe.android.homedataready";
    private HomeDataReadyRececiver readyRececiver;
    private IntentFilter intentFilter;

    private int last = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        layout = View.inflate(this, R.layout.framework_main_container, null);
        setContentView(layout);

        AsyncHttpClientMod.create(null).setRedirectHandler(null);

        //init receiver
        readyRececiver = new HomeDataReadyRececiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(action);

        FeedbackAgent agent = new FeedbackAgent(this);
        agent.sync();
        initView();
        initEvent();
        setSelect(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        frTabs.get(tab_index).setBackgroundColor(getResources().getColor(R.color.active_color));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent =getIntent();
        if (intent.hasExtra("tab")){
            tab_index = intent.getIntExtra("tab",0);
            intent.removeExtra("tab");
        }
        setSelect(tab_index);
        registerReceiver(readyRececiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(readyRececiver);
        super.onPause();
    }

    private void initEvent()
    {
        frTabIndex.setOnClickListener(this);
        frTabMe.setOnClickListener(this);
        frTabPlan.setOnClickListener(this);
        //frTabKnowledge.setOnClickListener(this);

        left_btn.setOnClickListener(this);
        right_btn.setOnClickListener(this);
    }

    private void initView()
    {
        frTabIndex = (LinearLayout) findViewById(R.id.tab_index);
        frTabMe = (LinearLayout) findViewById(R.id.tab_me);
        frTabPlan = (LinearLayout) findViewById(R.id.tab_plan);
        //frTabKnowledge = (LinearLayout) findViewById(R.id.tab_knowledge);
        frTabs.add(frTabIndex);
        frTabs.add(frTabPlan);
        //frTabs.add(frTabKnowledge);
        frTabs.add(frTabMe);

        left_btn = (ImageView) findViewById(R.id.left_btn);
        right_btn = (ImageView) findViewById(R.id.right_btn);
    }

    private void setSelect(int i)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        hideFragment(transaction);
        if(last != -1) {
            if(last == 0 && frIndexFragment != null)
                transaction.hide(frIndexFragment);
            if(last == 1 && frPlanFragment != null)
                transaction.hide(frPlanFragment);
            if(last == 2 && frMeFragment != null)
                transaction.hide(frMeFragment);
            frTabs.get(last).setBackgroundColor(getResources().getColor(R.color.base_color));
        }else {
//            frPlanFragment = new PlanFragment();
//            transaction.add(R.id.content, frPlanFragment).hide(frPlanFragment);
        }
        last = i;
        frTabs.get(i).setBackgroundColor(getResources().getColor(R.color.active_color));
        switch (i)
        {
            case 0:
                if (frIndexFragment == null){
                    frIndexFragment = new IndexFragment();
                    transaction.add(R.id.content, frIndexFragment);
                }
                transaction.show(frIndexFragment);
                left_btn.setImageResource(R.drawable.icon_category);
                right_btn.setImageResource(R.drawable.icon_search);
                tab_index = 0;
                break;
            case 1:
                long t = System.currentTimeMillis();

                if(!FrApplication.getInstance().isLogin()) {
                    Toast.makeText(this, getResources().getString(R.string.login_tip), Toast.LENGTH_SHORT).show();
                    break;
                }
                boolean isTest = FrApplication.getInstance().isTested();
                if(isTest) {
                    if (frPlanFragment == null) {
                        frPlanFragment = new PlanFragment();
                        transaction.add(R.id.content, frPlanFragment);
                    }
                    transaction.show(frPlanFragment);
                    left_btn.setImageResource(R.drawable.icon_nutrition);
                    right_btn.setImageResource(R.drawable.icon_change);
                    tab_index = 1;
                }else{
                    frTabs.get(tab_index).setBackgroundColor(getResources().getColor(R.color.active_color));
                    frTabs.get(i).setBackgroundColor(getResources().getColor(R.color.base_color));
                    Intent intent=new Intent(this,PlanTestActivity.class);
                    startActivity(intent);
                }
                long tt = System.currentTimeMillis();
                Toast.makeText(this, "计划" + (tt-t)+"ms", Toast.LENGTH_SHORT).show();
                break;
            /*case 2:
                if (frKnowledgeFragment == null){
                    frKnowledgeFragment = new KnowledgeFragment();
                    transaction.add(R.id.content, frKnowledgeFragment);
                } else{
                    transaction.show(frKnowledgeFragment);
                }
                left_btn.setImageResource(R.drawable.icon_knowledge);
                right_btn.setImageResource(R.drawable.icon_search);
                tab_index = 2;
                break;*/
            case 2:
                if (frMeFragment == null){
                    frMeFragment = new MeFragment();
                    transaction.add(R.id.content, frMeFragment);
                }
                transaction.show(frMeFragment);
                left_btn.setImageResource(R.drawable.icon_letter);
                right_btn.setImageResource(R.drawable.icon_set);
                tab_index = 2;
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction)
    {
        if (frIndexFragment != null) {
            transaction.hide(frIndexFragment);
            frTabs.get(0).setBackgroundColor(getResources().getColor(R.color.base_color));
        }
        if (frMeFragment != null) {
            transaction.hide(frMeFragment);
            frTabs.get(2).setBackgroundColor(getResources().getColor(R.color.base_color));
        }
        if (frPlanFragment != null) {
            transaction.hide(frPlanFragment);
            frTabs.get(1).setBackgroundColor(getResources().getColor(R.color.base_color));
        }/*
        if (frKnowledgeFragment != null){
            transaction.hide(frKnowledgeFragment);
            frTabs.get(3).setBackgroundColor(getResources().getColor(R.color.base_color));
        }*/
    }

    @Override
    public void onClick(View v)
    {
        resetTabs();
        switch (v.getId())
        {
            case R.id.tab_index:
                setSelect(0);
                break;
            case R.id.tab_plan:
                setSelect(1);
                break;
            /*case R.id.tab_knowledge:
                setSelect(2);
                break;*/
            case R.id.tab_me:
                setSelect(2);
                break;
            case R.id.left_btn:
                switch (tab_index){
                    case 0:
                        startActivity(new Intent(this, CategoryActivity.class));
                        break;
                    case 1:
                        if(frPlanFragment != null) ((PlanFragment)frPlanFragment).toggle("all");
                        /*
                        TextView name = (TextView) findViewById(R.id.meal_name);
                        TextView nutrition = (TextView) findViewById(R.id.ingredient_title);
                        nutrition.setText(name.getText()+"营养表");
                        TextView nutrition_weight = (TextView) findViewById(R.id.ingredient_title_weight);
                        nutrition_weight.setVisibility(View.GONE);
                        ScrollView nutrition_sv = (ScrollView) findViewById(R.id.plan_nutrition);
                        nutrition_sv.smoothScrollTo(0,0);*/
                        break;
                    /*case 2:
                        Intent intent=new Intent(this,CollectActivity.class);
                        intent.putExtra("tab", 2);
                        startActivity(intent);
                        break;*/
                    case 2:
                        startActivity(new Intent(this, LetterActivity.class));
                        break;
                }
                frTabs.get(tab_index).setBackgroundColor(getResources().getColor(R.color.active_color));
                break;
            case R.id.right_btn:
                switch (tab_index){
                    case 0:
                        //startActivity(new Intent(this, SearchActivity.class));
                        startActivity(new Intent(this, SetActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(this, PlanChoiceActivity.class));
                        break;
                    /*case 2:
                        startActivity(new Intent(this, SearchActivity.class));
                        break;*/
                    case 2:
                        startActivity(new Intent(this, SetActivity.class));
                        break;
                }
                frTabs.get(tab_index).setBackgroundColor(getResources().getColor(R.color.active_color));
                break;
            default:
                break;
        }
    }

    private void resetTabs()
    {
        for(int i=0;i<3;i++){
            frTabs.get(i).setBackgroundColor(getResources().getColor(R.color.base_color));
        }
    }

    @Override
    protected void onDestroy() {
        System.out.println("Main activity destroy !");
        unbindDrawables(layout);
        super.onDestroy();
    }

    public void unbindDrawables(View view) {//pass your parent view here
        try {
            if (view.getBackground() != null)
                view.getBackground().setCallback(null);

            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                imageView.setImageBitmap(null);
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++)
                    unbindDrawables(viewGroup.getChildAt(i));

                if (!(view instanceof AdapterView))
                    viewGroup.removeAllViews();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    class HomeDataReadyRececiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(action)) {
//                new Handler() {
//
//                }.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                        ((IndexFragment) frIndexFragment).fresh();
                        stopService(new Intent(MainActivity.this, GetHomeDataService.class));
//                    }
//                }, 1000);
            }
        }
    }
}
