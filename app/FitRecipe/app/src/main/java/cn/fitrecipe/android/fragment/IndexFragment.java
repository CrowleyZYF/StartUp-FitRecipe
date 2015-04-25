package cn.fitrecipe.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Config.HttpUrl;
import cn.fitrecipe.android.MainActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.rcListLinearLayoutManager;
import cn.fitrecipe.android.UI.rcRecommendViewPagerAdapter;
import cn.fitrecipe.android.model.RecipeCard;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class IndexFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private ViewPager frRecommendViewPager;
    private rcRecommendViewPagerAdapter rcViewPagerAdapter;

    private RecyclerView frUpdateRecipeRecyclerView;
    private rcListLinearLayoutManager frUpdateRecipeLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_index, container, false);

        initView(view);
        initData();
        initEvent();

        return view;
    }

    private void initEvent() {
        frRecommendViewPager.setOnPageChangeListener(this);
    }

    private void initData() {
        List<Map<String, Object>> dataList = getRecommendRecipe();
        rcViewPagerAdapter = new rcRecommendViewPagerAdapter(getActivity(),dataList,frRecommendViewPager.getLayoutParams().width,frRecommendViewPager.getLayoutParams().height);
        frRecommendViewPager.setAdapter(rcViewPagerAdapter);

        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(getUpdateRecipe());
        frUpdateRecipeRecyclerView.setAdapter(recipeCardAdapter);
    }

    private List<RecipeCard> getUpdateRecipe() {
        List<RecipeCard> result = new ArrayList<RecipeCard>();
        for (int i=0;i<5;i++){
            RecipeCard rc = new RecipeCard("牛油果鸡蛋三明治"+i,0,(20+i),(200+i*10),(50+i*10));
            result.add(rc);
        }
        return result;
    }

    private List<Map<String, Object>> getRecommendRecipe(){
        List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
        String teString = HttpUrl.RECOMMEND_RECIPE_VIEWPAGER_JSON;
        try {
            JSONArray jsonArray=new JSONArray(teString);
            for(int i=0;i<jsonArray.length();i++){
                Map<String, Object> map=new HashMap<String, Object>();
                JSONObject ob = new JSONObject();
                ob = (JSONObject) jsonArray.get(i);
                map.put("id", ob.get("id"));
                map.put("imgUrl", ob.get("imgUrl"));
                dataList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private void initView(View view) {
        frRecommendViewPager = (ViewPager) view.findViewById(R.id.recommend);

        frUpdateRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.update_recipe_recycler_view);
        frUpdateRecipeLayoutManager = new rcListLinearLayoutManager(this.getActivity());
        frUpdateRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frUpdateRecipeRecyclerView.setLayoutManager(frUpdateRecipeLayoutManager);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
