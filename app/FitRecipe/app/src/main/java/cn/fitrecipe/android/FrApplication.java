package cn.fitrecipe.android;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pgyersdk.crash.PgyCrashManager;

import cn.fitrecipe.android.Http.FRRequest;
import cn.fitrecipe.android.ImageLoader.MyImageLoader;
import cn.fitrecipe.android.function.Common;

/**
 * Created by 奕峰 on 2015/5/5.
 */
public class FrApplication extends Application {
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        String appId = "9f7d5e16543dfae53e38d616f3df0818";  //蒲公英注册或上传应用获取的AppId
        PgyCrashManager.register(this, appId);

        MyImageLoader.init(this);

        //初始化网络
        if(Common.isOpenNetwork(this))
           FRRequest.getInstance().init(this);
    }
}
