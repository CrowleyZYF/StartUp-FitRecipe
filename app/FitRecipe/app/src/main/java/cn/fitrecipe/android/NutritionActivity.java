package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class NutritionActivity extends Activity implements View.OnClickListener {

    private ImageView back_btn;
    private ImageView right_btn;
    private TextView header_name_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_nutrition);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        back_btn.setImageResource(R.drawable.icon_back_white);
        right_btn = (ImageView) findViewById(R.id.right_btn);
        right_btn.setVisibility(View.GONE);
        header_name_text = (TextView) findViewById(R.id.header_name_text);
        header_name_text.setText("营养分析");
    }

    private void initData() {

    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn :
                finish();
                break;
            default:
                break;
        }
    }
}
