package cn.fitrecipe.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import cn.fitrecipe.android.fragment.IndexFragment;
import cn.fitrecipe.android.fragment.KnowledgeFragment;
import cn.fitrecipe.android.fragment.MeFragment;
import cn.fitrecipe.android.fragment.PlanFragment;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class PlanTestActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView back_btn;
    private Fragment question[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_test);

        initView();
        initData();
        initEvent();
    }

    private void setSelect(int i)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        hideFragment(transaction);
        frTabs.get(i).setBackgroundColor(getResources().getColor(R.color.active_color));
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
                SharedPreferences preferences=getSharedPreferences("user", this.MODE_PRIVATE);
                boolean isTest = preferences.getBoolean("isTested", false);
                if(isTest){
                    if (frPlanFragment == null){
                        frPlanFragment = new PlanFragment();
                        transaction.add(R.id.content, frPlanFragment);
                    } else{
                        transaction.show(frPlanFragment);
                    }
                    left_btn.setImageResource(R.drawable.icon_nutrition);
                    right_btn.setImageResource(R.drawable.icon_shopping);
                    tab_index = 1;
                }else{
                    frTabs.get(tab_index).setBackgroundColor(getResources().getColor(R.color.active_color));
                    frTabs.get(i).setBackgroundColor(getResources().getColor(R.color.base_color));
                    Intent intent=new Intent(this,PlanTestActivity.class);
                    startActivity(intent);
                }
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
    }

    private void initData() {

    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:
                finish();
                break;
            default:
                break;
        }

    }
}
