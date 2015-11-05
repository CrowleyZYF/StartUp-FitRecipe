package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;


/**
 * Created by 奕峰 on 2015/5/8.
 */
public class PlanTestTipsActivity extends Activity {
    private LinearLayout question_05_tips;
    private LinearLayout question_06_tips;
    private ImageView fat_women_tips;
    private ImageView fat_man_tips;
    private TextView tips_sure_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_test_tips);

        initView(getIntent());
        initEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PlanTestTipsActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PlanTestTipsActivity");
        MobclickAgent.onResume(this);
    }

    private void initEvent() {
        tips_sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView(Intent intent) {
        question_05_tips = (LinearLayout) findViewById(R.id.question_05_tips);
        question_05_tips.setVisibility(View.GONE);
        question_06_tips = (LinearLayout) findViewById(R.id.question_06_tips);
        question_06_tips.setVisibility(View.GONE);
        fat_women_tips = (ImageView) findViewById(R.id.fat_women_tips);
        fat_women_tips.setVisibility(View.GONE);
        fat_man_tips = (ImageView) findViewById(R.id.fat_man_tips);
        fat_man_tips.setVisibility(View.GONE);
        tips_sure_btn = (TextView) findViewById(R.id.tips_sure_btn);
        if (intent.getStringExtra("question").equals("5")){
            question_05_tips.setVisibility(View.VISIBLE);
            if (intent.getStringExtra("data").equals("0"))
                fat_man_tips.setVisibility(View.VISIBLE);
            else
                fat_women_tips.setVisibility(View.VISIBLE);
        }else {
            question_06_tips.setVisibility(View.VISIBLE);
        }
    }
}
