package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class SetAboutActivity extends Activity implements View.OnClickListener {

    private ImageView back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_about);

        initView();
        initEvent();
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
        }

    }
}
