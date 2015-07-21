package cn.fitrecipe.android.fragment;

import android.app.ProgressDialog;
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
import android.widget.TextView;

import com.umeng.fb.FeedbackAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Adpater.RecommendViewPagerAdapter;
import cn.fitrecipe.android.Adpater.ThemeCardAdapter;
import cn.fitrecipe.android.CategoryActivity;
import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.ImageLoader.MyImageLoader;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.model.RecipeCard;
import cn.fitrecipe.android.model.ThemeCard;
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

    private List<String> urls;
    List<Map<String, Object>>  recommendRecipe;
    List<ThemeCard> themeCards;
    List<RecipeCard> recipeCards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_index, container, false);

        initView(view);
        getData();
        return view;
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
    }

    private void initData() {
        //获得推荐数据，并初始化适配器
        recommendViewPagerAdapter = new RecommendViewPagerAdapter(getActivity(), mImageLoader, recommendRecipe, recommendViewPager.getLayoutParams().width, recommendViewPager.getLayoutParams().height);
        recommendViewPager.setAdapter(recommendViewPagerAdapter);
        //初始化推荐的indicator
        recommendIndicator.setViewPager(recommendViewPager);
        //获得更新数据，并初始化适配器
        recipeCardAdapter = new RecipeCardAdapter(getActivity(), mImageLoader, recipeCards);
        updateRecipeRecyclerView.setAdapter(recipeCardAdapter);
        //获得主题数据，并初始化适配器
        themeCardAdapter = new ThemeCardAdapter(getActivity(), mImageLoader, themeCards);
        themeRecipeRecyclerView.setAdapter(themeCardAdapter);
    }

    private void getData() {
        //提前获取网络图片
        urls = new ArrayList<String>();

        final ProgressDialog pd = ProgressDialog.show(this.getActivity(), "", "请稍后....");
        pd.show();
        recommendRecipe = getRecommendRecipe();
        themeCards = getThemeRecipe();
        recipeCards = getUpdateRecipe();
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

    public List<ThemeCard> getThemeRecipe() {
        List<ThemeCard> result = new ArrayList<ThemeCard>();
        for (int i=0;i<3;i++){
            ThemeCard tc = new ThemeCard(LocalDemo.themeBG[i]);
            result.add(tc);
            urls.add(LocalDemo.themeBG[i]);
        }
        return result;
    }

    private List<RecipeCard> getUpdateRecipe() {
        List<RecipeCard> result = new ArrayList<RecipeCard>();
        for (int i=0;i<5;i++){
            RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],0,(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
            result.add(rc);
            urls.add(LocalDemo.recipeBG[i]);
        }
        return result;
    }

    private List<Map<String, Object>> getRecommendRecipe(){
        //http
        //time

        List<Map<String, Object>> result=new ArrayList<Map<String,Object>>();
        for(int i=0;i<5;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("id", i);
            map.put("type", LocalDemo.recommendType[i]);
            map.put("imgUrl", LocalDemo.recommendBG[i]);
            result.add(map);
            urls.add(LocalDemo.recommendBG[i]);
        }

        return result;
    }

}
