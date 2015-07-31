package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Config.HttpUrl;
import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.model.RecipeCard;
import pl.tajchert.sample.DotsTextView;

public class ThemeActivity extends Activity implements View.OnClickListener {

    private ScrollView themeContent;
    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;

    private ImageView back_btn;
    private ImageView recipe_img;
    private TextView recipe_content;
    private RecyclerView frThemeRecipeRecyclerView;
    private RecyclerViewLayoutManager frThemeRecipeLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        //获取ID
        Intent intent =getIntent();
        String id = intent.getStringExtra("id");
        String info = intent.getStringExtra("info");
        try {
            initView(info);
            getData(FrServerConfig.getThemeDetailsUrl(id), 0, 7);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initEvent();
    }

    private void getData(String url, int start, int num) throws JSONException {
        Toast.makeText(this, "URL: " + url, Toast.LENGTH_LONG).show();

        //get Data from network
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        String token = sp.getString("token", null);
        JSONObject params = new JSONObject();
        params.put("start", start);
        params.put("num", num);
        GetRequest request = new GetRequest(url, token, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res.has("data")){
                    try {
                        JSONArray data = res.getJSONArray("data");
                        processData(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        FrRequest.getInstance().request(request);

    }


    private void processData(JSONArray data) throws JSONException {

        List<RecipeCard> result = new ArrayList<RecipeCard>();
        for(int i = 0; i < data.length(); i++) {
            JSONObject recipe = data.getJSONObject(i);
            String recipe_name = recipe.getString("title");
            int recipe_id = recipe.getInt("id");
            int duration = recipe.getInt("duration");
            String img = FrServerConfig.getImageCompressed(recipe.getString("img"));
            String total_amount = recipe.getString("total_amount");
            double calories = recipe.getDouble("calories") * Integer.parseInt(total_amount.substring(0, total_amount.indexOf("g"))) / 100;
            RecipeCard rc = new RecipeCard(recipe_name, recipe_id, 0, duration, (int)calories, 100, img);
            result.add(rc);
        }
        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this, result);
        frThemeRecipeRecyclerView.setAdapter(recipeCardAdapter);
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
        themeContent = (ScrollView) findViewById(R.id.theme_content);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading();
            }
        }, 2000);

    }

    private void hideLoading(){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        themeContent.setVisibility(View.VISIBLE);
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
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
