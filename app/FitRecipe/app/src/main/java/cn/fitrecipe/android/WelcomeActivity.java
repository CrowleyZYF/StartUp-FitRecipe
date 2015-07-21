package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Config.LocalDemo;

public class WelcomeActivity extends Activity{
    private final int SPLASH_DISPLAY_LENGHT = 3000;

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //get image data
        list = getUrls();

        // TODO @WangKun
        // 加载首页数据，加载完成之后调用goToMainActivity进行跳转，或者加载时间超�?3秒之后调用goToMainActivity进行跳转
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                goToMainActivity();
            }
        }, SPLASH_DISPLAY_LENGHT);


    }

    private void goToMainActivity(){
        SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
        boolean isLogined = preferences.getBoolean("isLogined", false);
        if(isLogined){
            Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
            WelcomeActivity.this.startActivity(mainIntent);
            WelcomeActivity.this.finish();
        }else{
            Intent mainIntent = new Intent(WelcomeActivity.this, LandingPageActivity.class);
            WelcomeActivity.this.startActivity(mainIntent);
            WelcomeActivity.this.finish();
        }
    }


    private List<String> getUrls() {
        List<String> urls = new ArrayList<>();
        for (int i=0;i<3;i++){
            urls.add(LocalDemo.themeBG[i]);
        }
        for (int i=0;i<5;i++){
            urls.add(LocalDemo.recipeBG[i]);
        }
        for(int i=0;i<5;i++){
            urls.add(LocalDemo.recommendBG[i]);
        }
        return urls;
    }

}
