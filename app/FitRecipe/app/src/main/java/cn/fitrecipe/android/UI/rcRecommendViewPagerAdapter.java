package cn.fitrecipe.android.UI;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.ImageLoader.ImageLoader;

/**
 * Created by 奕峰 on 2015/4/25.
 */
public class rcRecommendViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Map<String, Object>> dataList;
    private List<ImageView> frRecommendImages = new ArrayList<ImageView>();
    private List<TextView> frRecommendIds = new ArrayList<TextView>();
    private int width;
    private int height;

    public rcRecommendViewPagerAdapter(Context context,List<Map<String, Object>> dataList,int width,int height){
        this.context = context;
        this.dataList = dataList;
        this.width = width;
        this.height = height;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        //ImageLoader imgLoader = new ImageLoader(context,width,height);
        //imgLoader.DisplayImage((dataList.get(position)).get("imgUrl").toString(), imageView);
        imageView.setImageResource(Integer.parseInt((dataList.get(position)).get("imgUrl").toString()));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView);
        frRecommendImages.add(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(frRecommendImages.get(position));
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
