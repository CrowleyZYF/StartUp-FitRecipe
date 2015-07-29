package cn.fitrecipe.android.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Adpater.RecommendViewPagerAdapter;
import cn.fitrecipe.android.Adpater.ThemeCardAdapter;
import cn.fitrecipe.android.CategoryActivity;
import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.ImageLoader.MyImageLoader;
import cn.fitrecipe.android.MainActivity;
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

    private String scale = "?imageMogr2/thumbnail/350000@";

    private IntentFilter intentFilter;
    private HomeDataReceiver receiver;
    String dataString = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        dataString = FrApplication.getInstance().getData();
        if(dataString != null) {
            initView(view);
            try {
                initData(dataString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            initEvent();
        }
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

    private void initData(String dataString) throws JSONException {
        //get data
        JSONObject data = null;
        recipeCards = new ArrayList<RecipeCard>();
        themeCards = new ArrayList<ThemeCard>();
        recommendRecipe = new ArrayList<Map<String, Object>>();
        if(dataString != null) {
            data = new JSONObject(dataString);

            JSONArray themes = data.getJSONArray("theme");
            for (int i = 0; i < themes.length(); i++) {
                JSONObject theme = themes.getJSONObject(i);
                String img = theme.getString("thumbnail") + scale;
                ThemeCard tc = new ThemeCard(theme.getInt("id"), img);
                themeCards.add(tc);
            }

            JSONArray updates = data.getJSONArray("update");
            for (int i = 0; i < updates.length(); i++) {
                JSONObject update = updates.getJSONObject(i);
                String name = update.getString("title");
                int id = update.getInt("id");
                int function = 0;
                int duration = update.getInt("duration");
                String total_amount = update.getString("total_amount");
                double calories = update.getDouble("calories") * Integer.parseInt(total_amount.substring(0, total_amount.indexOf("g"))) / 100;
                String img = update.getString("img") + scale;
                RecipeCard rc = new RecipeCard(name, id, function, duration, (int) calories, 0, img);
                recipeCards.add(rc);
            }

            JSONArray recommends = data.getJSONArray("recommend");
            for (int i = 0; i < recommends.length(); i++) {
                JSONObject recommend = recommends.getJSONObject(i);
                String img = recommend.getString("img") + scale;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", recommend.getInt("id"));
                map.put("type", "");
                map.put("imgUrl", img);
                recommendRecipe.add(map);
            }
        }

        //获得推荐数据，并初始化适配器
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


    class HomeDataReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
