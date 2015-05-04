package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class ThemeActivity extends Activity implements View.OnClickListener {

    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme);

        initView();
        initEvent();
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.back_btn);

    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            default:
                break;
        }

    }
}
