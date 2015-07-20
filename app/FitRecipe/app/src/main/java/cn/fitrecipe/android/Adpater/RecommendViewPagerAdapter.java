package cn.fitrecipe.android.Adpater;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.ArticleActivity;
import cn.fitrecipe.android.ImageLoader.MyImageLoader;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RecipeActivity;

/**
 * Created by 奕峰 on 2015/4/25.
 */
public class RecommendViewPagerAdapter extends PagerAdapter implements View.OnClickListener {
    private Context context;
    private List<Map<String, Object>> dataList;
    private List<View> recommendLinearLayout = new ArrayList<View>();
    private int width;
    private int height;
    private MyImageLoader mImageLoader;

    public RecommendViewPagerAdapter(Context context, MyImageLoader mImageLoader, List<Map<String, Object>> dataList, int width, int height){
        this.context = context;
        this.dataList = dataList;
        this.width = width;
        this.height = height;
        this.mImageLoader = mImageLoader;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View recommendContainer = LayoutInflater.from(context).inflate(R.layout.fragment_index_recommend_item, null);
        //背景图片
        ImageView imageView = (ImageView) recommendContainer.findViewById(R.id.recommend_image);
//        imageView.setImageResource();
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageLoader.load(imageView, (String) dataList.get(position).get("imgUrl"));

        //ID
        TextView idTextView = (TextView) recommendContainer.findViewById(R.id.recommend_id);
        idTextView.setText(dataList.get(position).get("id").toString());
        //Type
        TextView typeTextView = (TextView) recommendContainer.findViewById(R.id.recommend_type);
        typeTextView.setText(dataList.get(position).get("type").toString());
        //添加点击事件
        recommendContainer.setOnClickListener(this);
        container.addView(recommendContainer);
        recommendLinearLayout.add(recommendContainer);
        return recommendContainer;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(recommendLinearLayout.get(position));
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void onClick(View v) {
        String type = ((TextView) v.findViewById(R.id.recommend_type)).getText().toString();
        String id = ((TextView) v.findViewById(R.id.recommend_id)).getText().toString();
        if (type.equals("0")){
            //跳转到食谱
            Intent intent=new Intent(context,RecipeActivity.class);
            intent.putExtra("id", id);
            context.startActivity(intent);
        }else if(type.equals("1")){
            //跳转到指南
            Intent intent=new Intent(context,ArticleActivity.class);
            intent.putExtra("id", id);
            context.startActivity(intent);
        }

    }
}
