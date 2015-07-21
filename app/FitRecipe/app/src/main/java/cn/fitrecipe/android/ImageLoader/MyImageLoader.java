package cn.fitrecipe.android.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by wk on 2015/7/17.
 */
public class MyImageLoader {

    private DisplayImageOptions options = null;
    private Context mContext;
    private Map<String, List<View>> loadingFailed = null;
    private boolean completed;
    private AtomicInteger imageCount;
    private IImageLoad iImageLoad;

    public MyImageLoader(){
        init2(Integer.MAX_VALUE);
    }

    public MyImageLoader(IImageLoad iImageLoad, int count) {
        this.iImageLoad = iImageLoad;
        init2(count);
    }



    //��ʼ��MyImageLoader
    private void init2(int count){
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .displayer(new FadeInBitmapDisplayer(500))
                .build();
        loadingFailed = new ConcurrentHashMap<String, List<View>>();
        imageCount = new AtomicInteger(count);
    }

    public static void init(Context context) {
        //Universal Image Loader
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .diskCacheSize(50 * 1024 * 1024)
                .denyCacheImageMultipleSizesInMemory()
//                .memoryCacheSize(2 * 1024 * 1024)
                .threadPoolSize(5)
                .threadPriority(Thread.MAX_PRIORITY)
                .writeDebugLogs()
                .build();

        //��ʼ��ImageLoader
        ImageLoader.getInstance().init(configuration);
    }

    public void setImageCount(int count) {
        stop();
        imageCount.set(count);
    }

    public void loadingBackend(List<String> urls) {
        if(urls != null)
            imageCount.set(urls.size());
        Iterator<String> iterator = urls.iterator();
        while (iterator.hasNext()) {
            String url = iterator.next();
//            try {
//                ImageLoader.getInstance().getDiskCache().save(url, new BaseImageDownloader(mContext, 5000, 5000).getStream(url, null), null);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            ImageLoader.getInstance().loadImage(url, options, new MyImageLoadingListener());
        }

//          iImageLoad.onSuccess();
//        ImageLoader.getInstance().get
    }
//
//    public void load(View view, String imageUrl) {
//        if(view instanceof ImageView)
//             ImageLoader.getInstance().displayImage(imageUrl, (ImageView)view, options, new MyImageLoadingListener(view));
//        else
//            ImageLoader.getInstance().loadImage(imageUrl,  options, new MyImageLoadingListener(view));
//
////        ImageLoader.getInstance().displayImage(imageUrl);
//    }

    public void load(View view, String imageUrl) {
        ImageLoader.getInstance().displayImage(imageUrl, new ViewAware(view, false) {
            @Override
            protected void setImageDrawableInto(Drawable drawable, View view) {
                if(view instanceof  ImageView)
                    ((ImageView) view).setImageDrawable(drawable);
                else
                    view.setBackground(drawable);
            }

            @Override
            protected void setImageBitmapInto(Bitmap bitmap, View view) {
                if(view instanceof  ImageView)
                    ((ImageView) view).setImageBitmap(bitmap);
                else
                    view.setBackground(new BitmapDrawable(null, bitmap));
            }
        });
    }

    public void stop() {
        ImageLoader.getInstance().stop();
        loadingFailed.clear();
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

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            int res = imageCount.getAndDecrement();
            System.out.println("completed: " + res);
            if(res == 1)
                iImageLoad.onSuccess();
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    }
//    class MyImageLoadingListener implements ImageLoadingListener {
//
//        View mView = null;
//        public MyImageLoadingListener(View view) {
//            this.mView = view;
//        }
//
//        @Override
//        public void onLoadingStarted(String s, View view) {
//
//        }
//
//        @Override
//        public void onLoadingFailed(String s, View view, FailReason failReason) {
//            iImageLoad.onFailed();
//            stop();
//            imageCount.set(0);
//        }
//
//        @Override
//        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//            if(!(mView instanceof ImageView)) {
//                mView.setBackground(new BitmapDrawable(null, bitmap));
//            }
////            restoreFailedLoading(s, bitmap);
//            int res = imageCount.decrementAndGet();
//            System.out.println("complete s:" + s +"  "+ imageCount.get());
//            if(res == 0)
//                iImageLoad.onSuccess();
//        }
//
//        @Override
//        public void onLoadingCancelled(String s, View view) {
////            if(mView instanceof ImageView)
////                saveFailedLoading(s, view);
////            else
////                saveFailedLoading(s, mView);
//            int res = imageCount.decrementAndGet();
//            System.out.println("canceled s:" + s +"  "+ imageCount.get());
//            if(res == 0)
//                iImageLoad.onSuccess();
//        }
//
////        private void saveFailedLoading(String url, View view) {
////            List<View> list;
////            if(loadingFailed.containsKey(url))
////                list = loadingFailed.get(url);
////            else
////                list = Collections.synchronizedList(new ArrayList<View>());
////            list.add(view);
////            loadingFailed.put(url, list);
////        }
////
////        private void restoreFailedLoading(String url, Bitmap bitmap) {
////            if(loadingFailed.containsKey(url)) {
////                List<View> list = loadingFailed.get(url);
////                Iterator<View> iterator = list.iterator();
////                synchronized (list) {
////                    while (iterator.hasNext()) {
////                        View view = iterator.next();
////                        if (view instanceof ImageView)
////                            ((ImageView) view).setImageBitmap(bitmap);
////                        else
////                            view.setBackground(new BitmapDrawable(null, bitmap));
////                    }
////                }
////            }
////        }
//    }
}
