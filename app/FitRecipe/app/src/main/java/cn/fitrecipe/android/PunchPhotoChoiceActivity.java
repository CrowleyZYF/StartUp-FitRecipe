package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class PunchPhotoChoiceActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_photo_choice);

        initView();
        initEvent();
    }

    private void initEvent() {
    }

    private void initView() {
    }

    @Override
    public void onClick(View v) {

    }
}
