package cn.fitrecipe.android;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;

import com.pgyersdk.crash.PgyCrashManager;
import com.youku.player.YoukuPlayerBaseConfiguration;

import java.util.List;

import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.ImageLoader.MyImageLoader;
import cn.fitrecipe.android.entity.BasketDao;
import cn.fitrecipe.android.entity.Recipe;

/**
 * Created by 奕峰 on 2015/5/5.
 */
public class FrApplication extends Application {

    private static FrApplication instance;
    private MyImageLoader myImageLoader;
    //save home data json
    private String data;
    private boolean isHomeDataNew = false;
    private String token;
    public static YoukuPlayerBaseConfiguration configuration;

    private SharedPreferences userSp;

    private List<Recipe> basket;

    @Override
    public void onCreate() {
        super.onCreate();

        String appId = "9f7d5e16543dfae53e38d616f3df0818";  //蒲公英注册或上传应用获取的AppId
        PgyCrashManager.register(this, appId);

        MyImageLoader.init(this);
        this.registerActivityLifecycleCallbacks(new MyActivityCallbacks());

        userSp = getSharedPreferences("user", Context.MODE_PRIVATE);

        //init network
        FrRequest.getInstance().init(this);

        myImageLoader = new MyImageLoader();
        instance = this;

        configuration = new YoukuPlayerBaseConfiguration(this){

            @Override
            public Class<? extends Activity> getCachingActivityClass() {
                return null;
            }

            @Override
            public Class<? extends Activity> getCachedActivityClass() {
                return null;
            }

            @Override
            public String configDownloadPath() {
                return null;
            }
        };
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
        SharedPreferences.Editor editor = userSp.edit();
        editor.putString("homeData", data);
        editor.commit();
    }

    public boolean isHomeDataNew() {
        return isHomeDataNew;
    }

    public String getToken() {
        if(token == null)
            token = userSp.getString("token", null);
        return token;
    }

    public List<Recipe> getBasket() {
        if(basket == null) {
            BasketDao basketDao = new BasketDao(this);
            basket = basketDao.getBasket();
        }
        return basket;
    }

    public void clearBasket() {
        basket = null;
        BasketDao basketDao = new BasketDao(this);
        basketDao.clearBasket();
    }

    public void saveBasket(List<Recipe> basket) {
        this.basket = basket;
        BasketDao basketDao = new BasketDao(this);
        basketDao.saveBasket(basket);
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
