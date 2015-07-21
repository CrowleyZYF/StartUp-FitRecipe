package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.ImageLoader.ILoadingListener;
import cn.fitrecipe.android.model.RecipeCard;
import cn.fitrecipe.android.model.ThemeCard;

public class WelcomeActivity extends Activity{
    private final int SPLASH_DISPLAY_LENGHT = 3000;

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //get image data
        list = getUrls();
        FrApplication.getInstance().getMyImageLoader().setiLoadingListener(new ILoadingListener() {
            @Override
            public void loadComplete() {
                goToMainActivity();
            }

            @Override
            public void loadFailed() {

            }
        });
        FrApplication.getInstance().getMyImageLoader().loadImages(list, SPLASH_DISPLAY_LENGHT);
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

    public List<ThemeCard> getThemeRecipe() {
        List<ThemeCard> result = new ArrayList<ThemeCard>();
        for (int i=0;i<3;i++){
            ThemeCard tc = new ThemeCard(LocalDemo.themeBG[i]);
            result.add(tc);
        }
        return result;
    }

    private List<RecipeCard> getUpdateRecipe() {
        List<RecipeCard> result = new ArrayList<RecipeCard>();
        for (int i=0;i<5;i++){
            RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],0,(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
            result.add(rc);
        }
        return result;
    }

    private List<Map<String, Object>> getRecommendRecipe(){
        List<Map<String, Object>> result=new ArrayList<Map<String,Object>>();
        for(int i=0;i<5;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("id", i);
            map.put("type", LocalDemo.recommendType[i]);
            map.put("imgUrl", LocalDemo.recommendBG[i]);
            result.add(map);
        }
        return result;
    }
}
