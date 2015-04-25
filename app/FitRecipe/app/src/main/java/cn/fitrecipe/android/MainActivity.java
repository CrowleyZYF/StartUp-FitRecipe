package cn.fitrecipe.android;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.fragment.CategoryFragment;
import cn.fitrecipe.android.fragment.IndexFragment;
import cn.fitrecipe.android.fragment.KnowledgeFragment;
import cn.fitrecipe.android.fragment.PlanFragment;

public class MainActivity extends FragmentActivity implements OnClickListener
{
    private LinearLayout frTabIndex;
    private LinearLayout frTabCategory;
    private LinearLayout frTabPlan;
    private LinearLayout frTabKnowledge;

    private Fragment frIndexFragment;
    private Fragment frCategoryFragment;
    private Fragment frPlanFragment;
    private Fragment frKnowledgeFragment;

    private List<LinearLayout> frTabs = new ArrayList<LinearLayout>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        setSelect(0);
    }

    private void initEvent()
    {
        frTabIndex.setOnClickListener(this);
        frTabCategory.setOnClickListener(this);
        frTabPlan.setOnClickListener(this);
        frTabKnowledge.setOnClickListener(this);
    }

    private void initView()
    {
        frTabIndex = (LinearLayout) findViewById(R.id.tab_index);
        frTabCategory = (LinearLayout) findViewById(R.id.tab_category);
        frTabPlan = (LinearLayout) findViewById(R.id.tab_plan);
        frTabKnowledge = (LinearLayout) findViewById(R.id.tab_knowledge);
        frTabs.add(frTabIndex);
        frTabs.add(frTabCategory);
        frTabs.add(frTabPlan);
        frTabs.add(frTabKnowledge);
    }

    private void setSelect(int i)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
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
                break;
            case 1:
                if (frCategoryFragment == null){
                    frCategoryFragment = new CategoryFragment();
                    transaction.add(R.id.content, frCategoryFragment);
                } else{
                    transaction.show(frCategoryFragment);
                }
                break;
            case 2:
                if (frPlanFragment == null){
                    frPlanFragment = new PlanFragment();
                    transaction.add(R.id.content, frPlanFragment);
                } else{
                    transaction.show(frPlanFragment);
                }
                break;
            case 3:
                if (frKnowledgeFragment == null){
                    frKnowledgeFragment = new KnowledgeFragment();
                    transaction.add(R.id.content, frKnowledgeFragment);
                } else{
                    transaction.show(frKnowledgeFragment);
                }
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
        if (frCategoryFragment != null){
            transaction.hide(frCategoryFragment);
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
            case R.id.tab_category:
                setSelect(1);
                break;
            case R.id.tab_plan:
                setSelect(2);
                break;
            case R.id.tab_knowledge:
                setSelect(3);
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
