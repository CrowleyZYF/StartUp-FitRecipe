package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * Created by 奕峰 on 2015/5/8.
 */
public class PlanTestTipsActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_test_tips);

        initView(getIntent());
    }

    private void initView(Intent intent) {

    }
}
