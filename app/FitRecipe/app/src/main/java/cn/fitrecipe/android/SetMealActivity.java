package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.Switch;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class SetMealActivity extends Activity implements View.OnClickListener {

    private ImageView back_btn;
    private TextView right_btn;
    private Switch add_meal_01_check;
    private Switch add_meal_02_check;
    private Switch add_meal_03_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_meal);

        initView();
        initEvent();
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        right_btn.setOnClickListener(this);
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        back_btn.setImageResource(R.drawable.icon_back_white);
        right_btn = (TextView) findViewById(R.id.right_btn);
        add_meal_01_check = (Switch) findViewById(R.id.add_meal_01_check);
        add_meal_02_check = (Switch) findViewById(R.id.add_meal_02_check);
        add_meal_03_check = (Switch) findViewById(R.id.add_meal_03_check);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:
                finish();
                break;
            case R.id.right_btn:
                Toast.makeText(this, "上午加餐：" + add_meal_01_check.isChecked() +
                        "，下午加餐：" + add_meal_02_check.isChecked() +
                        "，夜宵：" + add_meal_03_check.isChecked(), Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
