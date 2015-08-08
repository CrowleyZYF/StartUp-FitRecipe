package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.UI.BorderScrollView;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.model.RecipeCard;
import pl.tajchert.sample.DotsTextView;

public class ThemeActivity extends Activity implements View.OnClickListener {

    private BorderScrollView themeContent;
    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;

    private ImageView back_btn;
    private ImageView recipe_img;
    private TextView recipe_content;
    private RecyclerView frThemeRecipeRecyclerView;
    private RecyclerViewLayoutManager frThemeRecipeLayoutManager;
    private String id;
    private int start = 0;
    private int num = 6;
    List<RecipeCard> dataList;
    RecipeCardAdapter recipeCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        //获取ID
        Intent intent =getIntent();
        id = intent.getStringExtra("id");
        String info = intent.getStringExtra("info");
        try {
            initView(info);
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initEvent();
    }

    private void getData() throws JSONException {
        //get Data from network
        String token = FrApplication.getInstance().getToken();
        JSONObject params = new JSONObject();
        params.put("id", id);
        params.put("start", start);
        params.put("num", num);
        String url = FrServerConfig.getThemeDetailsUrl(params);
//        Toast.makeText(this, url, Toast.LENGTH_LONG).show();
        GetRequest request = new GetRequest(url, token, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res.has("data")){
                    try {
                        JSONArray data = res.getJSONArray("data");
                        processData(data);
                        if(start == 0)
                            hideLoading(false, "");
                        else {
                            if (data.length() == 0) {
                                themeContent.setNoMore();
                                Toast.makeText(ThemeActivity.this, "没有多余的菜谱", Toast.LENGTH_SHORT).show();
                            }
                            themeContent.setCompleteMore();
                        }
                        start += num;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(start == 0)
                    hideLoading(true, getResources().getString(R.string.network_error));
                else {
                    themeContent.setCompleteMore();
                    Toast.makeText(ThemeActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
        FrRequest.getInstance().request(request);
    }


    private void processData(JSONArray data) throws JSONException {
        if(data == null || data.length() == 0)
            return;
        if(dataList == null)
            dataList = new ArrayList<RecipeCard>();
        for(int i = 0; i < data.length(); i++) {
            JSONObject recipe = data.getJSONObject(i);
            String recipe_name = recipe.getString("title");
            int recipe_id = recipe.getInt("id");
            JSONArray effect_labels = recipe.getJSONArray("effect_labels");
            String function = "";
            String function_backup = "";
            if(effect_labels.length() > 0)
                function = effect_labels.getJSONObject(0).getString("name");
            if(effect_labels.length() > 1) {
                function_backup = effect_labels.getJSONObject(1).getString("name");
            }
            int duration = recipe.getInt("duration");
            String img = FrServerConfig.getImageCompressed(recipe.getString("img"));
            String total_amount = recipe.getString("total_amount");
            double calories = recipe.getDouble("calories") * Integer.parseInt(total_amount.substring(0, total_amount.indexOf("g"))) / 100;
            RecipeCard rc = new RecipeCard(recipe_name, recipe_id, function, function_backup, duration, (int)calories, 100, img);
            dataList.add(rc);
        }
        if(recipeCardAdapter == null) {
            recipeCardAdapter = new RecipeCardAdapter(this, dataList);
            frThemeRecipeRecyclerView.setAdapter(recipeCardAdapter);
        }
        else
            recipeCardAdapter.notifyDataSetChanged();

    }

    private void initView(String info) throws JSONException {
        JSONObject data = new JSONObject(info);

        recipe_content = (TextView) findViewById(R.id.recipe_content);
        recipe_img = (ImageView) findViewById(R.id.recipe_img);
        FrApplication.getInstance().getMyImageLoader().displayImage(recipe_img, FrServerConfig.getImageCompressed(data.getString("img")));
        recipe_content.setText(data.getString("content"));
        back_btn = (ImageView) findViewById(R.id.back_btn);

        frThemeRecipeRecyclerView = (RecyclerView) findViewById(R.id.theme_recipe_recycler_view);
        frThemeRecipeLayoutManager = new RecyclerViewLayoutManager(this);
        frThemeRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frThemeRecipeRecyclerView.setLayoutManager(frThemeRecipeLayoutManager);

        loadingInterface = (LinearLayout) findViewById(R.id.loading_interface);
        themeContent = (BorderScrollView) findViewById(R.id.theme_content);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);

    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }else{
            themeContent.setVisibility(View.VISIBLE);
        }
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        themeContent.setOnBorderListener(new BorderScrollView.OnBorderListener() {
            @Override
            public void onBottom() {
//                Toast.makeText(ThemeActivity.this, "onBottom", Toast.LENGTH_LONG).show();
                try {
                    getData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTop() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            default:
                break;
        }

    }
}
