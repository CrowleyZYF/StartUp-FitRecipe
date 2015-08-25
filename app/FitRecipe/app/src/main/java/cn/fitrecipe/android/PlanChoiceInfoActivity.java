package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class PlanChoiceInfoActivity extends Activity {

    private LinearLayout header;
    private ImageView back_btn;
    private ImageView author_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_choice_info);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        header = (LinearLayout) findViewById(R.id.header);
        header.setBackgroundColor(getResources().getColor(R.color.transparent));
        back_btn = (ImageView) findViewById(R.id.left_btn);
        back_btn.setBackgroundColor(getResources().getColor(R.color.transparent));
        back_btn.setImageResource(R.drawable.icon_back_white);
        author_btn = (ImageView) findViewById(R.id.right_btn);
        author_btn.setBackgroundColor(getResources().getColor(R.color.transparent));
        author_btn.setImageResource(R.drawable.icon_user);
    }

    private void initData() {

    }

    private void initEvent() {

    }

}
