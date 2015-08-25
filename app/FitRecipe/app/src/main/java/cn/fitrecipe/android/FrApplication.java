package cn.fitrecipe.android;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.pgyersdk.crash.PgyCrashManager;
import com.youku.player.YoukuPlayerBaseConfiguration;

import java.util.List;

import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.ImageLoader.MyImageLoader;
import cn.fitrecipe.android.dao.AuthorDao;
import cn.fitrecipe.android.dao.BasketDao;
import cn.fitrecipe.android.dao.CollectionDao;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.dao.HomeDataDao;
import cn.fitrecipe.android.entity.Author;
import cn.fitrecipe.android.entity.Collection;
import cn.fitrecipe.android.entity.HomeData;
import cn.fitrecipe.android.entity.Recipe;

/**
 * Created by 奕峰 on 2015/5/5.
 */
public class FrApplication extends Application {

    private static FrApplication instance;
    private MyImageLoader myImageLoader;
    public static YoukuPlayerBaseConfiguration configuration;

    //菜篮子缓存
    private List<Recipe> basket;
    //用户缓存
    private Author author;
    private String token;
    //主页数据缓存
    private HomeData homeData;
    //收藏
    private List<Collection> collections;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化蒲公英
        initPgy();

        //初始化图片缓存
        initImageLoader();

        //init network
        FrRequest.getInstance().init(this);

//        initYouku();
        instance = this;
    }

    private void initPgy() {
        String appId = "9f7d5e16543dfae53e38d616f3df0818";  //蒲公英注册或上传应用获取的AppId
        PgyCrashManager.register(this, appId);
    }

    private void initImageLoader() {
        MyImageLoader.init(this);
        this.registerActivityLifecycleCallbacks(new MyActivityCallbacks());
        myImageLoader = new MyImageLoader();
    }

    private void initYouku() {
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

    public String getToken() {
        Author author = getAuthor();
        if(author != null)
            return author.getToken();
        return null;
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

    public Author getAuthor() {
        if(author == null) {
            author = new AuthorDao(this).getAuthor();
        }
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
        FrDbHelper.getInstance(this).saveAuthor(author);
    }

    public void setIsTested(boolean isTested) {
        if(author == null)  getAuthor();
        if(author != null) {
            author.setIsTested(isTested);
            FrDbHelper.getInstance(this).saveAuthor(author);
        }
    }

    public void logOut() {
        FrDbHelper.getInstance(this).authorLogout(author);
        author = null;
//        new CollectionDao(this).clear();
    }

    public boolean isLogin() {
        Author author = getAuthor();
        return author == null ? false : author.getIsLogin();
    }

    public boolean isTested() {
        Author author = getAuthor();
        return author == null ? false : author.getIsTested();
    }

    public HomeData getHomeData() {
        if(homeData == null) {
            homeData = new HomeDataDao(this).getHomeData();
        }
        return homeData;
    }

    public void setHomeData(HomeData homeData) {
        this.homeData = homeData;
        new HomeDataDao(this).savHomeData(homeData);
    }

//    public List<Collection> getCollections() {
//        if(collections == null) {
//            collections = new CollectionDao(this).getCollections();
//        }
//        return collections;
//    }

//    public void setCollections(List<Collection> collections) {
//        this.collections = collections;
//        new CollectionDao(this).saveCollections(collections);
//    }


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
