package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class FollowActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_container);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("FollowActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("FollowActivity");
        MobclickAgent.onResume(this);
    }

}
