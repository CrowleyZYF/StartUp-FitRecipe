package cn.fitrecipe.android;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.umeng.fb.FeedbackAgent;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.UI.SlidingPage;
import cn.fitrecipe.android.fragment.MeFragment;
import cn.fitrecipe.android.fragment.IndexFragment;
import cn.fitrecipe.android.fragment.KnowledgeFragment;
import cn.fitrecipe.android.fragment.PlanFragment;

public class MainActivity extends FragmentActivity implements OnClickListener
{
    private LinearLayout frTabIndex;
    private LinearLayout frTabMe;
    private LinearLayout frTabPlan;
    private LinearLayout frTabKnowledge;

    private Fragment frIndexFragment;
    private Fragment frMeFragment;
    private Fragment frPlanFragment;
    private Fragment frKnowledgeFragment;

    private ImageView left_btn;
    private ImageView right_btn;

    private int tab_index = 0;

    private List<LinearLayout> frTabs = new ArrayList<LinearLayout>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.framework_main_container);
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

    private void initEvent()
    {
        frTabIndex.setOnClickListener(this);
        frTabMe.setOnClickListener(this);
        frTabPlan.setOnClickListener(this);
        frTabKnowledge.setOnClickListener(this);

        left_btn.setOnClickListener(this);
        right_btn.setOnClickListener(this);
    }

    private void initView()
    {
        frTabIndex = (LinearLayout) findViewById(R.id.tab_index);
        frTabMe = (LinearLayout) findViewById(R.id.tab_me);
        frTabPlan = (LinearLayout) findViewById(R.id.tab_plan);
        frTabKnowledge = (LinearLayout) findViewById(R.id.tab_knowledge);
        frTabs.add(frTabIndex);
        frTabs.add(frTabPlan);
        frTabs.add(frTabKnowledge);
        frTabs.add(frTabMe);

        left_btn = (ImageView) findViewById(R.id.left_btn);
        right_btn = (ImageView) findViewById(R.id.right_btn);
    }

    private void setSelect(int i)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        hideFragment(transaction);
        switch (i)
        {
            case 0:
                if (frIndexFragment == null){
                    frIndexFragment = new IndexFragment();
                    transaction.add(R.id.content, frIndexFragment);
                } else{
                    transaction.show(frIndexFragment);
                }
                left_btn.setImageResource(R.drawable.icon_category);
                right_btn.setImageResource(R.drawable.icon_search);
                tab_index = 0;
                break;
            case 1:
                if (frPlanFragment == null){
                    frPlanFragment = new PlanFragment();
                    transaction.add(R.id.content, frPlanFragment);
                } else{
                    transaction.show(frPlanFragment);
                }
                left_btn.setImageResource(R.drawable.icon_nutrition);
                right_btn.setImageResource(R.drawable.icon_shopping);
                tab_index = 1;
                break;
            case 2:
                if (frKnowledgeFragment == null){
                    frKnowledgeFragment = new KnowledgeFragment();
                    transaction.add(R.id.content, frKnowledgeFragment);
                } else{
                    transaction.show(frKnowledgeFragment);
                }
                left_btn.setImageResource(R.drawable.icon_knowledge);
                right_btn.setImageResource(R.drawable.icon_search);
                tab_index = 2;
                break;
            case 3:
                if (frMeFragment == null){
                    frMeFragment = new MeFragment();
                    transaction.add(R.id.content, frMeFragment);
                } else{
                    transaction.show(frMeFragment);
                }
                left_btn.setImageResource(R.drawable.icon_letter);
                right_btn.setImageResource(R.drawable.icon_set);
                tab_index = 3;
                break;
            default:
                break;
        }
        transaction.commit();
        frTabs.get(i).setBackgroundColor(getResources().getColor(R.color.active_color));
    }

    private void hideFragment(FragmentTransaction transaction)
    {
        if (frIndexFragment != null){
            transaction.hide(frIndexFragment);
        }
        if (frMeFragment != null){
            transaction.hide(frMeFragment);
        }
        if (frPlanFragment != null){
            transaction.hide(frPlanFragment);
        }
        if (frKnowledgeFragment != null){
            transaction.hide(frKnowledgeFragment);
        }
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
            case R.id.tab_knowledge:
                setSelect(2);
                break;
            case R.id.tab_me:
                setSelect(3);
                break;
            case R.id.left_btn:
                switch (tab_index){
                    case 0:
                        startActivity(new Intent(this, CategoryActivity.class));
                        break;
                    case 1:
                        SlidingPage mRightMenu;
                        mRightMenu = (SlidingPage) findViewById(R.id.filter_menu);
                        mRightMenu.toggle();
                        TextView name = (TextView) findViewById(R.id.meal_name);
                        TextView nutrition = (TextView) findViewById(R.id.ingredient_title);
                        nutrition.setText(name.getText()+"营养表");
                        TextView nutrition_weight = (TextView) findViewById(R.id.ingredient_title_weight);
                        nutrition_weight.setVisibility(View.GONE);
                        ScrollView nutrition_sv = (ScrollView) findViewById(R.id.plan_nutrition);
                        nutrition_sv.smoothScrollTo(0,0);
                        break;
                    case 2:
                        startActivity(new Intent(this, FollowActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(this, LetterActivity.class));
                        break;
                }
                frTabs.get(tab_index).setBackgroundColor(getResources().getColor(R.color.active_color));
                break;
            case R.id.right_btn:
                switch (tab_index){
                    case 0:
                        startActivity(new Intent(this, SearchActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(this, IngredientActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(this, SearchActivity.class));
                        break;
                    case 3:
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
        for(int i=0;i<4;i++){
            frTabs.get(i).setBackgroundColor(getResources().getColor(R.color.base_color));
        }
    }

}
