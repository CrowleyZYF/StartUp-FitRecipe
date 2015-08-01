package cn.fitrecipe.android;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.pgyersdk.crash.PgyCrashManager;

import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.ImageLoader.MyImageLoader;

/**
 * Created by 奕峰 on 2015/5/5.
 */
public class FrApplication extends Application {

    private static FrApplication instance;
    private MyImageLoader myImageLoader;
    //save home data json
    private String data;
    private boolean isHomeDataNew = false;

//    private List<ThemeCard> themeCards;
//    private List<Map<String, Object>> recommendRecipes;
//    private List<RecipeCard> recipeCards;

    @Override
    public void onCreate() {
        super.onCreate();

        String appId = "9f7d5e16543dfae53e38d616f3df0818";  //蒲公英注册或上传应用获取的AppId
        PgyCrashManager.register(this, appId);

        MyImageLoader.init(this);
        this.registerActivityLifecycleCallbacks(new MyActivityCallbacks());

        //init network
        FrRequest.getInstance().init(this);

        myImageLoader = new MyImageLoader();
        instance = this;
    }

    public static FrApplication getInstance() {
        return instance;
    }

    public MyImageLoader getMyImageLoader() {
        return myImageLoader;
    }

    public String getData() {
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        if(data == null || data.length() == 0) {
            isHomeDataNew = false;
            if(preferences.contains("homeData"))
                data = preferences.getString("homeData", null);
        }
        return data;
    }

    public void saveData(String data) {
        this.data = data;
        isHomeDataNew = true;
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("homeData", data);
        editor.commit();
    }

    public boolean isHomeDataNew() {
        return isHomeDataNew;
    }


    class MyActivityCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

            myImageLoader.resume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            myImageLoader.pause();
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }

    @Override
    public void onTerminate() {
        myImageLoader.stop();
        super.onTerminate();
    }
}
