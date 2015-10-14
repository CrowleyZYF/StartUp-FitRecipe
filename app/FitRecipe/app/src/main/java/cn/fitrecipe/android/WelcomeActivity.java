package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.fitrecipe.android.service.GetHomeDataService;

public class WelcomeActivity extends Activity{
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startService(new Intent(this, GetHomeDataService.class));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToMainActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }


    private void goToMainActivity(){;
        boolean isLogined = FrApplication.getInstance().isLogin();
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

    @Override
    protected void onDestroy() {
        System.out.println("welcome activity destroy !");
        super.onDestroy();
    }

}
