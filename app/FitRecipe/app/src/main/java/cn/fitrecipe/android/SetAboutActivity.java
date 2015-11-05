package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class SetAboutActivity extends Activity implements View.OnClickListener {

    private ImageView back_btn;
    private TextView version_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_about);

        initView();
        initData();
        initEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("SetAboutActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("SetAboutActivity");
        MobclickAgent.onResume(this);
    }

    private void initData() {
        try {
            version_name.setText("当前版本："+this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName+"");
        } catch (Exception e){

        }
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        version_name = (TextView) findViewById(R.id.version_name);
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
