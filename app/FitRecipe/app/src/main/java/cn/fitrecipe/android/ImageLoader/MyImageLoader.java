package cn.fitrecipe.android.ImageLoader;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.BaseDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.imageaware.NonViewAware;
import com.nostra13.universalimageloader.core.imageaware.ViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by wk on 2015/7/17.
 */
public class MyImageLoader {

    private DisplayImageOptions options = null;
    private Context mContext;
    private ILoadingListener iLoadingListener;


    //count images that have not been loaded
    private AtomicInteger count;
    //mark the iloadingListener if invoked
    private AtomicBoolean isCompleted;


    //open a time out task
    Timer timer = null;

    public MyImageLoader() {
        init();
    }


    private void init() {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)          //permit cache image in memory and disk
                .displayer(new FadeInBitmapDisplayer(500)) // set image fade in
                .build();

        //init
        count = new AtomicInteger(0);
        isCompleted = new AtomicBoolean(true);
        timer = new Timer();
    }

    public static void init(Context context) {
        //Universal Image Loader
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .diskCacheSize(50 * 1024 * 1024)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2 * 1024 * 1024)  //设置最大内存缓存大小为2M
                .threadPoolSize(5)                 //设置线程池的大小
//                .memoryCacheSize(2 * 1024 * 1024)
                .threadPoolSize(5)
//                .threadPriority(Thread.MAX_PRIORITY)
                .writeDebugLogs()
                .build();

        //init Universal ImageLoader
        ImageLoader.getInstance().init(configuration);
    }

    //this is method is load image backend, it only load images in the urls
    public void loadImages(List<String> urls, int loadingTimeout) {
        if(urls != null) {
            count.set(urls.size());
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (isCompleted.compareAndSet(false, true)) {
                        iLoadingListener.loadComplete();
                    }
                }
            }, loadingTimeout);
            isCompleted.set(false);
            Iterator<String> iterator = urls.iterator();
            while (iterator.hasNext()) {
                String url = iterator.next();
                ImageLoader.getInstance().loadImage(url, options, new MyImageLoadingListener());
            }
        }
    }

    public void displayImage(View view, String imageUrl) {
        ImageLoader.getInstance().displayImage(imageUrl, new ViewAware(view, false) {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void setImageDrawableInto(Drawable drawable, View view) {
                if (view instanceof ImageView)
                    ((ImageView) view).setImageDrawable(drawable);
                else
                    view.setBackground(drawable);
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void setImageBitmapInto(Bitmap bitmap, View view) {
                if (view instanceof ImageView)
                    ((ImageView) view).setImageBitmap(bitmap);
                else
                    view.setBackground(new BitmapDrawable(null, bitmap));
            }
        }, new MyImageLoadingListener());
    }

    public void stop() {
        ImageLoader.getInstance().stop();
    }

    public void clearMemoryCache() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    public void clearDiskCache() {
        ImageLoader.getInstance().clearDiskCache();
    }


    class MyImageLoadingListener implements ImageLoadingListener {

        @Override
        public void onLoadingStarted(String s, View view) {
            //TODO
            //set image before
        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {
            if(view == null)
                iLoadingListener.loadFailed();
        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            // it is load not display
            synchronized (this) {
                if (view == null) {
                    int res = count.decrementAndGet();
                    System.out.println("completed: " + res);
                    if (count.compareAndSet(0, 0) && isCompleted.compareAndSet(false,true)) {
                        iLoadingListener.loadComplete();
                        timer.cancel();
                    }
                }
            }
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    }

    public ILoadingListener getiLoadingListener() {
        return iLoadingListener;
    }

    public void setiLoadingListener(ILoadingListener iLoadingListener) {
        this.iLoadingListener = iLoadingListener;
    }
}
