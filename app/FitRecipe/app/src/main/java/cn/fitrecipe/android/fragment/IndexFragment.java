package cn.fitrecipe.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.umeng.fb.FeedbackAgent;

import org.json.JSONException;

import java.util.List;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Adpater.RecommendViewPagerAdapter;
import cn.fitrecipe.android.Adpater.ThemeCardAdapter;
import cn.fitrecipe.android.CategoryActivity;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.HomeData;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.entity.Recommend;
import cn.fitrecipe.android.entity.Theme;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class IndexFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    //推荐
    private ViewPager recommendViewPager;
    private RecommendViewPagerAdapter recommendViewPagerAdapter;
    private CircleIndicator recommendIndicator;
    //更新
    private RecyclerView updateRecipeRecyclerView;
    private RecipeCardAdapter recipeCardAdapter;
    private RecyclerViewLayoutManager updateRecipeLayoutManager;
    //主题
    private RecyclerView themeRecipeRecyclerView;
    private ThemeCardAdapter themeCardAdapter;
    private RecyclerViewLayoutManager themeRecipeLayoutManager;
    //意见反馈
    private TextView feedback_btn;
    //分类
    private Button category_btn;
    //ScrollView
    private ScrollView scrollView;


    private List<String> urls;
    List<Recommend>  recommendRecipe;
    List<Theme> themeCards;
    List<Recipe> recipeCards;
    private HomeData homeData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        homeData = FrApplication.getInstance().getHomeData();
        initView(view);
        if(homeData != null) {
            try {
                initData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            initEvent();
        }else
            scrollView.setVisibility(View.INVISIBLE);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(!FrApplication.getInstance().isHomeDataNew())
//                    Common.toastNetworkError(IndexFragment.this.getActivity());
//            }
//        }, 3000);
        return view;
    }

    public void fresh() {
        homeData= FrApplication.getInstance().getHomeData();
        if(recipeCardAdapter == null && recommendViewPagerAdapter == null && themeCardAdapter == null) {
            try {
                initData();
                initEvent();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            try {
                processData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            recommendViewPagerAdapter.notifyDataSetChanged();
            recipeCardAdapter.notifyDataSetChanged();
            themeCardAdapter.notifyDataSetChanged();
        }
    }

    private void initView(View view) {
        //初始化推荐组件视图
        recommendViewPager = (ViewPager) view.findViewById(R.id.recommend);
        recommendIndicator = (CircleIndicator) view.findViewById(R.id.indicator_default);
        //初始化更新组件视图
        updateRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.update_recipe_recycler_view);
        updateRecipeLayoutManager = new RecyclerViewLayoutManager(this.getActivity());
        updateRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        updateRecipeRecyclerView.setLayoutManager(updateRecipeLayoutManager);
        //初始化主题组件视图
        themeRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.theme_recipe_recycler_view);
        themeRecipeLayoutManager = new RecyclerViewLayoutManager(this.getActivity());
        themeRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        themeRecipeRecyclerView.setLayoutManager(themeRecipeLayoutManager);
        //初始化反馈、分类按钮
        feedback_btn = (TextView) view.findViewById(R.id.feedback_btn);
        category_btn = (Button) view.findViewById(R.id.category_btn_2);


        scrollView = (ScrollView) view.findViewById(R.id.index_content);
    }

    private void processData() throws JSONException {
        recipeCards = homeData.getUpdate();
        recommendRecipe = homeData.getRecommend();
        themeCards = homeData.getTheme();
    }

    private void initData() throws JSONException {
        //get data
        processData();
        //获得推荐数据，并初始化适配器3
        recommendViewPagerAdapter = new RecommendViewPagerAdapter(getActivity(), recommendRecipe, recommendViewPager.getLayoutParams().width, recommendViewPager.getLayoutParams().height);
        recommendViewPager.setAdapter(recommendViewPagerAdapter);
        //初始化推荐的indicator
        recommendIndicator.setViewPager(recommendViewPager);
        //获得更新数据，并初始化适配器
        recipeCardAdapter = new RecipeCardAdapter(getActivity(),  recipeCards);
        updateRecipeRecyclerView.setAdapter(recipeCardAdapter);
        //获得主题数据，并初始化适配器
        themeCardAdapter = new ThemeCardAdapter(getActivity(), themeCards);
        themeRecipeRecyclerView.setAdapter(themeCardAdapter);
    }

    private void initEvent() {
        recommendIndicator.setOnPageChangeListener(this);
        feedback_btn.setOnClickListener(this);
        category_btn.setOnClickListener(this);
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
}
