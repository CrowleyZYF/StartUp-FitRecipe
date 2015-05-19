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
import cn.fitrecipe.android.Adpater.ThemeCardAdapter;
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
import cn.fitrecipe.android.model.ThemeCard;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class IndexFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    //推荐
    private ViewPager frRecommendViewPager;
    private rcRecommendViewPagerAdapter rcViewPagerAdapter;
    private CircleIndicator defaultIndicator;
    //更新
    private RecyclerView frUpdateRecipeRecyclerView;
    private rcListLinearLayoutManager frUpdateRecipeLayoutManager;
    //主题
    private RecyclerView frThemeRecipeRecyclerView;
    private rcListLinearLayoutManager frThemeRecipeLayoutManager;
    //意见反馈
    private TextView feedback_btn;
    //分类
    private Button category_btn;
    boolean misScrolled = false;

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
        defaultIndicator.setOnPageChangeListener(this);

        feedback_btn.setOnClickListener(this);
        category_btn.setOnClickListener(this);
    }

    private void initData() {
        List<Map<String, Object>> dataList = getRecommendRecipe();
        rcViewPagerAdapter = new rcRecommendViewPagerAdapter(getActivity(), dataList, frRecommendViewPager.getLayoutParams().width, frRecommendViewPager.getLayoutParams().height);
        frRecommendViewPager.setAdapter(rcViewPagerAdapter);
        defaultIndicator.setViewPager(frRecommendViewPager);

        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(getActivity(), getUpdateRecipe());
        frUpdateRecipeRecyclerView.setAdapter(recipeCardAdapter);

        ThemeCardAdapter themeCardAdapter = new ThemeCardAdapter(getActivity(), getThemeRecipe());
        frThemeRecipeRecyclerView.setAdapter(themeCardAdapter);
    }



    private void initView(View view) {
        frRecommendViewPager = (ViewPager) view.findViewById(R.id.recommend);
        defaultIndicator = (CircleIndicator) view.findViewById(R.id.indicator_default);

        frUpdateRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.update_recipe_recycler_view);
        frUpdateRecipeLayoutManager = new rcListLinearLayoutManager(this.getActivity());
        frUpdateRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frUpdateRecipeRecyclerView.setLayoutManager(frUpdateRecipeLayoutManager);

        frThemeRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.theme_recipe_recycler_view);
        frThemeRecipeLayoutManager = new rcListLinearLayoutManager(this.getActivity());
        frThemeRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frThemeRecipeRecyclerView.setLayoutManager(frThemeRecipeLayoutManager);

        feedback_btn = (TextView) view.findViewById(R.id.feedback_btn);
        category_btn = (Button) view.findViewById(R.id.category_btn_2);
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
            case R.id.feedback_btn:
                FeedbackAgent agent = new FeedbackAgent(getActivity());
                agent.startFeedbackActivity();
                break;
            case R.id.category_btn_2:
                startActivity(new Intent(this.getActivity(), CategoryActivity.class));
                break;
            default:
                break;
        }
    }

    public List<ThemeCard> getThemeRecipe() {
        List<ThemeCard> result = new ArrayList<ThemeCard>();
        for (int i=0;i<3;i++){
            ThemeCard tc = new ThemeCard(LocalDemo.themeBG[i]);
            result.add(tc);
        }
        return result;
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
        for(int i=0;i<5;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("id", i);
            map.put("imgUrl", LocalDemo.recommendBG[i]);
            dataList.add(map);
        }
        /*String teString = HttpUrl.RECOMMEND_RECIPE_VIEWPAGER_JSON;
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
        }*/
        return dataList;
    }
}
