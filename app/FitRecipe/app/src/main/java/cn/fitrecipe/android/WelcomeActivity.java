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
    private List<ThemeCard> themeCards;
    private List<RecipeCard> recipeCards;
    private List<Map<String, Object>> recommendRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //get new data from network
        getDataFromNetwork();

        //get old data from local
        //TODO

        //pass data to MainActivity

        //get image data
        list = getUrls();
        FrApplication.getInstance().getMyImageLoader().setiLoadingListener(new ILoadingListener() {
        // 加载首页数据，加载完成之后调用goToMainActivity进行跳转，或者加载时间超过3秒之后调用goToMainActivity进行跳转
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

    private void getDataFromNetwork() {
        themeCards = new ArrayList<ThemeCard>();
        for (int i=0;i<3;i++){
            ThemeCard tc = new ThemeCard(LocalDemo.themeBG[i]);
            themeCards.add(tc);
        }

        recipeCards= new ArrayList<RecipeCard>();
        for (int i=0;i<5;i++){
            RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],0,(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
            recipeCards.add(rc);
        }

        recommendRecipes =new ArrayList<Map<String,Object>>();
        for(int i=0;i<5;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("id", i);
            map.put("type", LocalDemo.recommendType[i]);
            map.put("imgUrl", LocalDemo.recommendBG[i]);
            recommendRecipes.add(map);
        }
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


    public List<ThemeCard> getThemeCards() {
        return themeCards;
    }

    public List<RecipeCard> getRecipeCards() {
        return recipeCards;
    }

    public List<Map<String, Object>> getRecommendRecipes() {
        return recommendRecipes;
    }

    //test
}
