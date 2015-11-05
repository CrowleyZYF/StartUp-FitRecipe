package cn.fitrecipe.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.fragment.SelectStageFirstFragment;
import cn.fitrecipe.android.fragment.SelectStageSecondFragment;

public class SelectRecipeActivity extends Activity implements View.OnClickListener{

    Fragment[] fragments;
    FragmentTransaction transaction;
    int last = -1;
    public PlanComponent obj_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipe);

        fragments = new Fragment[2];
        setFragment(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("SelectRecipeActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("SelectRecipeActivity");
        MobclickAgent.onResume(this);
    }

    public void setFragment(int i) {
        transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if(fragments[i] == null) {
            if (i == 0) fragments[i] = new SelectStageFirstFragment();
            if (i == 1) fragments[i] = new SelectStageSecondFragment();
            transaction.add(R.id.fragment_container, fragments[i]);
        }
        if(last != -1)
            transaction.hide(fragments[last]);
        transaction.show(fragments[i]);
        last = i;
        transaction.commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
