package cn.fitrecipe.android.ImageLoader;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import cn.fitrecipe.android.R;


/**
 * Created by wk on 2015/7/17.
 */
public class MyImageLoader {

    private DisplayImageOptions options = null, options2;
    private Context mContext;


    //count images that have not been loaded
    private AtomicInteger count;
    private int total;


    public MyImageLoader() {
        init();
    }


    private void init() {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)          //permit cache image in memory and disk
//                .displayer(new FadeInBitmapDisplayer(500)) // set image fade in
                .build();

        options2 = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.loading_pic1)
                .showImageForEmptyUri(R.drawable.loading_pic1)
                .showImageOnLoading(R.drawable.loading_pic1)
                .resetViewBeforeLoading(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer())
//                .delayBeforeLoading(200)
                .cacheInMemory(true)
                .cacheOnDisk(true)          //permit cache image in memory and disk
//                .displayer(new FadeInBitmapDisplayer(500)) // set image fade in
                .build();

        //init
        count = new AtomicInteger(0);
    }

    public static void init(Context context) {
        //Universal Image Loader
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .diskCacheSize(100 * 1024 * 1024)
                .denyCacheImageMultipleSizesInMemory()
                .threadPoolSize(3)
                .memoryCacheSize(10 * 1024 * 1024)
//                .threadPriority(Thread.MAX_PRIORITY)
//                .writeDebugLogs()
                .build();

        //init Universal ImageLoader
        ImageLoader.getInstance().init(configuration);
    }

    //this is method is load image backend, it only load images in the urls
    public void loadImages(Set<String> urls, ILoadingListener listener ) {
        System.out.println(Calendar.getInstance().get(Calendar.MINUTE) + " " + Calendar.getInstance().get(Calendar.SECOND));
        if(urls != null) {
            total = urls.size();
            Iterator<String> iterator = urls.iterator();
            while (iterator.hasNext()) {
                String url = iterator.next();
                ImageLoader.getInstance().loadImage(url, options2, new MyLoadingListener(listener));
            }
        }
    }

    public void displayImage(View view, final String imageUrl) {
        displayImage(view, imageUrl, null);
    }

    public void displayImage(View view, final String imageUrl, ILoadingListener listener) {
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
//                System.out.println(imageUrl + "    " + bitmap.getByteCount());
                if (view instanceof ImageView)
                    ((ImageView) view).setImageBitmap(bitmap);
                else
                    view.setBackground(new BitmapDrawable(null, bitmap));
            }
        }, options2, new MyDisplayListener(listener));
    }

    public void stop() {
        ImageLoader.getInstance().stop();
        Log.i("ImageLoader", "stop");
    }

    public void clearMemoryCache() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    public void clearDiskCache() {
        ImageLoader.getInstance().clearDiskCache();
    }


    class MyLoadingListener implements ImageLoadingListener {

        private ILoadingListener listener;

        public MyLoadingListener(ILoadingListener listener) {
            this.listener = listener;
        }

        @Override
        public void onLoadingStarted(String s, View view) {
        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {
            listener.loadFailed();
        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            // it is load not display
            synchronized (this) {
                int res = count.incrementAndGet();
                System.out.println("completed: " + res);
                if (count.compareAndSet(total, total)) {
                    listener.loadComplete();
                }
            }
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    }


    class MyDisplayListener implements ImageLoadingListener {

        private ILoadingListener listener;

        public MyDisplayListener(ILoadingListener listener) {
            this.listener = listener;
        }

        @Override
        public void onLoadingStarted(String s, View view) {

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {
            if(listener != null)
                listener.loadFailed();
        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            if(listener != null)
                listener.loadComplete();
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    }

    public void pause() {
        ImageLoader.getInstance().pause();
        Log.i("ImageLoader", "pause");
    }

    public void resume() {
        ImageLoader.getInstance().resume();
        Log.i("ImageLoader", "resume");
    }
}
