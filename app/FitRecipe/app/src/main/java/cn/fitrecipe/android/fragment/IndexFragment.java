package cn.fitrecipe.android.fragment;

import android.content.Intent;
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
import android.widget.TextView;

import com.umeng.fb.FeedbackAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.CategoryActivity;
import cn.fitrecipe.android.Config.HttpUrl;
import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.LandingPageActivity;
import cn.fitrecipe.android.MainActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.ThemeActivity;
import cn.fitrecipe.android.UI.rcListLinearLayoutManager;
import cn.fitrecipe.android.UI.rcRecommendViewPagerAdapter;
import cn.fitrecipe.android.model.RecipeCard;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class IndexFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager frRecommendViewPager;
    private rcRecommendViewPagerAdapter rcViewPagerAdapter;

    private RecyclerView frUpdateRecipeRecyclerView;
    private rcListLinearLayoutManager frUpdateRecipeLayoutManager;

    private ImageView theme_test;
    private TextView feedback;
    private Button category_btn;

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
        theme_test.setOnClickListener(this);
        feedback.setOnClickListener(this);
        category_btn.setOnClickListener(this);
    }

    private void initData() {
        List<Map<String, Object>> dataList = getRecommendRecipe();
        rcViewPagerAdapter = new rcRecommendViewPagerAdapter(getActivity(),dataList,frRecommendViewPager.getLayoutParams().width,frRecommendViewPager.getLayoutParams().height);
        frRecommendViewPager.setAdapter(rcViewPagerAdapter);

        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(getActivity(), getUpdateRecipe());
        frUpdateRecipeRecyclerView.setAdapter(recipeCardAdapter);
    }

    private List<RecipeCard> getUpdateRecipe() {
        List<RecipeCard> result = new ArrayList<RecipeCard>();
        for (int i=0;i<5;i++){
            RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],0,(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
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

        theme_test = (ImageView) view.findViewById(R.id.theme_test);
        feedback = (TextView) view.findViewById(R.id.feedback);
        category_btn = (Button) view.findViewById(R.id.check_category);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.theme_test:
                startActivity(new Intent(this.getActivity(), ThemeActivity.class));
                break;
            case R.id.feedback:
                FeedbackAgent agent = new FeedbackAgent(getActivity());
                agent.startFeedbackActivity();
                break;
            case R.id.check_category:
                startActivity(new Intent(this.getActivity(), CategoryActivity.class));
                break;
            default:
                break;
        }
    }
}
