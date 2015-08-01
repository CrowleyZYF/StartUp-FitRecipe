package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.UI.SlidingMenu;
import cn.fitrecipe.android.model.RecipeCard;
import pl.tajchert.sample.DotsTextView;

public class CategoryResultActivity extends Activity implements View.OnClickListener {

    private ScrollView categoryContent;
    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;

    private SlidingMenu mRightMenu;

    private ImageView back_btn;
    private ImageView filter_btn;

    private RecyclerView frThemeRecipeRecyclerView;
    private RecyclerViewLayoutManager frThemeRecipeLayoutManager;
    private RecipeCardAdapter recipeCardAdapter;
    private List<RecipeCard> dataList;

    private LinearLayout time_sort_btn;
    private TextView time_sort_text;
    private ImageView time_sort_icon;

    private LinearLayout like_sort_btn;
    private TextView like_sort_text;
    private ImageView like_sort_icon;

    private LinearLayout calorie_sort_btn;
    private TextView calorie_sort_text;
    private ImageView calorie_sort_icon;

    private int sort_type = 0;
    private boolean sort_des = false;

    private String meat, effect, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_result_container);

        Intent intent = getIntent();
        meat = intent.getStringExtra("meat");
        effect = intent.getStringExtra("effect");
        time = intent.getStringExtra("time");

        initView();
        try {
            getData(0, 7);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initEvent();

    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        back_btn.setImageResource(R.drawable.icon_back_white);

        filter_btn = (ImageView) findViewById(R.id.right_btn);
        filter_btn.setImageResource(R.drawable.icon_filter);

        mRightMenu = (SlidingMenu) findViewById(R.id.container_layout);

        frThemeRecipeRecyclerView = (RecyclerView) findViewById(R.id.theme_recipe_recycler_view);
        frThemeRecipeLayoutManager = new RecyclerViewLayoutManager(this);
        frThemeRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frThemeRecipeRecyclerView.setLayoutManager(frThemeRecipeLayoutManager);

        time_sort_btn = (LinearLayout) findViewById(R.id.time_sort_btn);
        time_sort_text = (TextView) findViewById(R.id.time_sort_text);
        time_sort_icon = (ImageView) findViewById(R.id.time_sort_icon);

        like_sort_btn = (LinearLayout) findViewById(R.id.like_sort_btn);
        like_sort_text = (TextView) findViewById(R.id.like_sort_text);
        like_sort_icon = (ImageView) findViewById(R.id.like_sort_icon);

        calorie_sort_btn = (LinearLayout) findViewById(R.id.calorie_sort_btn);
        calorie_sort_text = (TextView) findViewById(R.id.calorie_sort_text);
        calorie_sort_icon = (ImageView) findViewById(R.id.calorie_sort_icon);

        loadingInterface = (LinearLayout) findViewById(R.id.loading_interface);
        categoryContent = (ScrollView) findViewById(R.id.category_result_list);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading(false,"");
            }
        }, 2000);
    }


    private void getData(int start, int num) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("meat", meat);
        params.put("effect", effect);
        params.put("time", time);
        params.put("order", "duration");
        params.put("start", start);
        params.put("num", num);
        String url = FrServerConfig.getRecipeListByCategory(params);
        System.out.println(url);
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        String token = null;
        if(sp.contains("token"))
            token = sp.getString("token", null);
        GetRequest request = new GetRequest(url, token, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                 if(res.has("data")) {
                     JSONArray data = null;
                     hideLoading(false, "");
                     try {
                         data = res.getJSONArray("data");
                         processData(data);
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideLoading(true, getResources().getString(R.string.network_error));
            }
        });
        FrRequest.getInstance().request(request);
    }

    private void processData(JSONArray data) throws JSONException {
        if(dataList == null)
            dataList = new ArrayList<RecipeCard>();
        else
            dataList.clear();
        if(data != null) {
            for(int i = 0; i < data.length(); i++) {
                JSONObject recipe = data.getJSONObject(i);
                String recipe_name = recipe.getString("title");
                int recipe_id = recipe.getInt("id");
                int duration = recipe.getInt("duration");
                double calories = recipe.getDouble("calories");
                String img = FrServerConfig.getImageCompressed(recipe.getString("img"));
                JSONArray effects = recipe.getJSONArray("effect_labels");
                String function = "不限";
                if(effects != null && effects.length() > 0)
                    function = effects.getJSONObject(0).getString("name");
                RecipeCard rc = new RecipeCard(recipe_name, recipe_id, function, duration, (int)calories, 100, img);
                dataList.add(rc);
            }
        }
        recipeCardAdapter = new RecipeCardAdapter(this, dataList);
        frThemeRecipeRecyclerView.setAdapter(recipeCardAdapter);
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }else{
            categoryContent.setVisibility(View.VISIBLE);
        }
    }

    private void beginLoading(){
        loadingInterface.setVisibility(View.VISIBLE);
        dotsTextView.start();
        categoryContent.setVisibility(View.GONE);
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        filter_btn.setOnClickListener(this);
        time_sort_btn.setOnClickListener(this);
        like_sort_btn.setOnClickListener(this);
        calorie_sort_btn.setOnClickListener(this);
    }

    private void getThemeRecipe(int type,boolean des) {
        dataList.clear();
        beginLoading();



        if(type==0){
            if(des){
                for (int i=5;i<9;i++){
                    RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],i,"",(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
                    dataList.add(rc);
                }
            }else{
                for (int i=8;i>4;i--){
                    RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],i,"",(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
                    dataList.add(rc);
                }
            }
        }
        else if(type==1){
            if(des){
                for (int i=1;i<5;i++){
                    RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],i,"",(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
                    dataList.add(rc);
                }
            }else{
                for (int i=4;i>0;i--){
                    RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],i,"",(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
                    dataList.add(rc);
                }
            }
        }
        else if(type==2){
            if(des){
                for (int i=3;i<7;i++){
                    RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],i,"",(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
                    dataList.add(rc);
                }
            }else{
                for (int i=6;i>2;i--){
                    RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],i,"",(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
                    dataList.add(rc);
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:
                finish();
                break;
            case R.id.right_btn:
                mRightMenu.toggle();
                break;
            case R.id.time_sort_btn:
                resetTextColor();
                time_sort_text.setTextColor(getResources().getColor(R.color.base_color));
                sort_type = 0;
                if(sort_des){
                    time_sort_icon.setImageResource(R.drawable.icon_arrow_up_active);
                }else{
                    time_sort_icon.setImageResource(R.drawable.icon_arrow_down_active);
                }
                sort_des = !sort_des;
                getThemeRecipe(sort_type,sort_des);
                recipeCardAdapter.notifyDataSetChanged();
                break;
            case R.id.like_sort_btn:
                resetTextColor();
                like_sort_text.setTextColor(getResources().getColor(R.color.base_color));
                sort_type = 1;
                if(sort_des){
                    like_sort_icon.setImageResource(R.drawable.icon_arrow_up_active);
                }else{
                    like_sort_icon.setImageResource(R.drawable.icon_arrow_down_active);
                }
                sort_des = !sort_des;
                getThemeRecipe(sort_type,sort_des);
                recipeCardAdapter.notifyDataSetChanged();
                break;
            case R.id.calorie_sort_btn:
                resetTextColor();
                calorie_sort_text.setTextColor(getResources().getColor(R.color.base_color));
                sort_type = 2;
                if(sort_des){
                    calorie_sort_icon.setImageResource(R.drawable.icon_arrow_up_active);
                }else{
                    calorie_sort_icon.setImageResource(R.drawable.icon_arrow_down_active);
                }
                sort_des = !sort_des;
                getThemeRecipe(sort_type,sort_des);
                recipeCardAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void resetTextColor(){
        time_sort_text.setTextColor(getResources().getColor(R.color.login_input_text_color));
        like_sort_text.setTextColor(getResources().getColor(R.color.login_input_text_color));
        calorie_sort_text.setTextColor(getResources().getColor(R.color.login_input_text_color));
        time_sort_icon.setImageResource(R.drawable.icon_arrow_up);
        like_sort_icon.setImageResource(R.drawable.icon_arrow_up);
        calorie_sort_icon.setImageResource(R.drawable.icon_arrow_up);
    }
}
