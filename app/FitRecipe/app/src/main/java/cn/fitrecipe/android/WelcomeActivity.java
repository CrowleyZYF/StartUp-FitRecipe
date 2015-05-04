package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity{
    private final int SPLASH_DISPLAY_LENGHT = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
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
        }, SPLASH_DISPLAY_LENGHT);
    }

}
