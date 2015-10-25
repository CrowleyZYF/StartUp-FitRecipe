package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class SetActivity extends Activity implements View.OnClickListener {

    private RelativeLayout set_account_btn;
    private RelativeLayout set_meal_btn;

    private ImageView back_btn;
    private ImageView right_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        initView();
        initEvent();
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        set_account_btn.setOnClickListener(this);
        set_meal_btn.setOnClickListener(this);

    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        back_btn.setImageResource(R.drawable.icon_back_white);
        right_btn = (ImageView) findViewById(R.id.right_btn);
        right_btn.setVisibility(View.GONE);
        set_account_btn = (RelativeLayout) findViewById(R.id.set_account_btn);
        set_meal_btn = (RelativeLayout) findViewById(R.id.set_meal_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:
                finish();
                break;
            case R.id.set_account_btn:
                Intent set_account_btn = new Intent(this,SetAccountActivity.class);
                startActivity(set_account_btn);
                break;
            case R.id.set_meal_btn:
                Intent set_meal_btn = new Intent(this,SetMealActivity.class);
                startActivity(set_meal_btn);
                break;
        }

    }
}
