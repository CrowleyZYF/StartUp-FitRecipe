package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.fitrecipe.android.Config.HttpUrl;
import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.ImageLoader.ILoadingListener;
import cn.fitrecipe.android.model.RecipeCard;
import cn.fitrecipe.android.model.ThemeCard;
import cn.fitrecipe.android.service.GetHomeDataService;

public class WelcomeActivity extends Activity{
    private final int SPLASH_DISPLAY_LENGTH = 2000;

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
    //test

    @Override
    protected void onDestroy() {
        System.out.println("welcome activity destroy !");
        super.onDestroy();
    }

}
