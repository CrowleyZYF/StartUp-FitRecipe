package cn.fitrecipe.android;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.pgyersdk.crash.PgyCrashManager;
import com.youku.player.YoukuPlayerBaseConfiguration;

import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.ImageLoader.MyImageLoader;
import cn.fitrecipe.android.dao.AuthorDao;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.dao.HomeDataDao;
import cn.fitrecipe.android.entity.Author;
import cn.fitrecipe.android.entity.BasketRecord;
import cn.fitrecipe.android.entity.HomeData;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.PunchRecord;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.entity.SeriesPlan;
import cn.fitrecipe.android.function.Common;

;

/**
 * Created by 奕峰 on 2015/5/5.
 */
public class FrApplication extends Application {

    private static FrApplication instance;
    private MyImageLoader myImageLoader;
    public static YoukuPlayerBaseConfiguration configuration;

    //菜篮子缓存
//    private List<Object> basket;
    //用户缓存
    private Author author;
    private String token;
    //主页数据缓存
    private HomeData homeData;
    //报告缓存
    private Report report;

    private SeriesPlan planInUse;

    private boolean isBasketEmpty;

    private boolean justChangePlan;

    private PlanComponent component;
    private int type;
    private String date;
    private int plan_id;

    private Map<String, List<PunchRecord>> punchData;
    //保存菜篮子
    private Map<String, List<BasketRecord>> basketData;
    private PunchRecord pr;

    private boolean isSettingChanged;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化蒲公英
        initPgy();

        //初始化图片缓存
        initImageLoader();

        //init network
        FrRequest.getInstance().init(this);

        initYouku();
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

    public Report getReport() {
        if(report == null) {
            report = FrDbHelper.getInstance(this).getReport();
        }
        return report;
    }

    public void saveReport(Report report) {
        this.report = report;
        FrDbHelper.getInstance(this).addReport(report);
    }

    public SeriesPlan getPlanInUse() {
        return planInUse;
    }

    public void setPlanInUse(SeriesPlan planInUse) {
        this.planInUse = planInUse;
    }

    public boolean isBasketEmpty() {
        return isBasketEmpty;
    }

    public void setIsBasketEmpty(boolean isBasketEmpty) {
        this.isBasketEmpty = isBasketEmpty;
    }

    public boolean isJustChangePlan() {
        return justChangePlan;
    }

    public void setJustChangePlan(boolean justChangePlan) {
        this.justChangePlan = justChangePlan;
    }

    public PlanComponent getComponent() {
        return component;
    }

    public void setComponent(PlanComponent component) {
        this.component = component;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public Map<String, List<PunchRecord>> getPunchData() {
        if(punchData == null)
            punchData = FrDbHelper.getInstance(this).getPunchInfo(Common.getDate(), Common.getSomeDay(Common.getDate(), 6));
        return punchData;
    }

    public void setPunchData(Map<String, List<PunchRecord>> punchData) {
        this.punchData = punchData;
    }

    public Map<String, List<BasketRecord>> getBasketData() {
        if(basketData == null)
            basketData = FrDbHelper.getInstance(this).getBasketInfo(Common.getDate(), Common.getSomeDay(Common.getDate(), 6));
        return basketData;
    }

    public void setBasketData(Map<String, List<BasketRecord>> basketData) {
        this.basketData = basketData;
    }

    public PunchRecord getPr() {
        return pr;
    }

    public void setPr(PunchRecord pr) {
        this.pr = pr;
    }

    public boolean isSettingChanged() {
        return isSettingChanged;
    }

    public void setIsSettingChanged(boolean isSettingChanged) {
        this.isSettingChanged = isSettingChanged;
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
