package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SearchActivity extends Activity implements View.OnClickListener {

    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        initData();
        initEvent();

    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.back_btn);
    }

    private void initData() {

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
