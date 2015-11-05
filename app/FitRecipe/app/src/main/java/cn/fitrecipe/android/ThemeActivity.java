package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.Http.PostRequest;
import cn.fitrecipe.android.UI.BorderScrollView;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.entity.Theme;
import cn.fitrecipe.android.function.RequestErrorHelper;
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
    private int start = 0;
    private int num = 6;
    List<Recipe> dataList;
    RecipeCardAdapter recipeCardAdapter;
    Theme theme;

    private TextView follow_btn;
    //食谱是否已经收藏
    private boolean isCollected = false;
    //删除收藏记录的ID
    private int collect_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        //获取ID
        Intent intent =getIntent();
        theme = (Theme) intent.getSerializableExtra("theme");
        try {
            initView();
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ThemeActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ThemeActivity");
        MobclickAgent.onResume(this);
    }

    private void initData() {
        GetRequest request = new GetRequest(FrServerConfig.getThemeInfo(theme.getId()), FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res.has("data")){
                    try {
                        JSONObject data = res.getJSONObject("data");
                        if (data != null) {
                            isCollected = data.getBoolean("has_collected");
                            collect_id = data.getInt("collect_id");
                            if (isCollected){
                                follow_btn.setText(R.string.cancel_follow);
                                follow_btn.setBackground(getResources().getDrawable(R.color.disable_color));
                            }else{
                                follow_btn.setText(R.string.follow);
                                follow_btn.setBackground(getResources().getDrawable(R.color.active_color));
                            }
                            getData();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                RequestErrorHelper.toast(ThemeActivity.this, volleyError);
            }
        });
        FrRequest.getInstance().request(request);
    }

    private void getData() throws JSONException {
        //get Data from network
        String token = FrApplication.getInstance().getToken();
        JSONObject params = new JSONObject();
        params.put("id", theme.getId());
        params.put("start", start);
        params.put("num", num);
        String url = FrServerConfig.getThemeDetailsUrl(params);
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
                        if(data.length() > 0)
                            start += num;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                RequestErrorHelper.toast(ThemeActivity.this, volleyError);
            }
        });
        FrRequest.getInstance().request(request);
    }


    private void processData(JSONArray data) throws JSONException {
        if(data == null || data.length() == 0)
            return;
        if(dataList == null) {
            dataList = new ArrayList<>();
        }

       for(int i = 0; i < data.length(); i++) {
           Recipe recipe = Recipe.fromJson(data.getJSONObject(i).toString());
           dataList.add(recipe);
       }
        if(recipeCardAdapter == null) {
            recipeCardAdapter = new RecipeCardAdapter(this, dataList);
            frThemeRecipeRecyclerView.setAdapter(recipeCardAdapter);
        }
        else
            recipeCardAdapter.notifyDataSetChanged();
    }

    private void initView() throws JSONException {
        recipe_content = (TextView) findViewById(R.id.recipe_content);
        recipe_img = (ImageView) findViewById(R.id.recipe_img);
        FrApplication.getInstance().getMyImageLoader().displayImage(recipe_img, theme.getImg());
        recipe_content.setText(theme.getContent());
        back_btn = (ImageView) findViewById(R.id.back_btn);

        frThemeRecipeRecyclerView = (RecyclerView) findViewById(R.id.theme_recipe_recycler_view);
        frThemeRecipeLayoutManager = new RecyclerViewLayoutManager(this);
        frThemeRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frThemeRecipeRecyclerView.setLayoutManager(frThemeRecipeLayoutManager);

        loadingInterface = (LinearLayout) findViewById(R.id.loading_interface);
        themeContent = (BorderScrollView) findViewById(R.id.theme_content);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);

        follow_btn = (TextView) findViewById(R.id.follow_btn);
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
        follow_btn.setOnClickListener(this);
        themeContent.setOnBorderListener(new BorderScrollView.OnBorderListener() {
            @Override
            public void onBottom() {
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
            case R.id.follow_btn:
                collect_recipe();
                break;
            default:
                break;
        }
    }

    public void collect_recipe(){
        if(!FrApplication.getInstance().isLogin()) {
            Toast.makeText(this, getResources().getString(R.string.login_tip), Toast.LENGTH_SHORT).show();
            return;
        }
        if(isCollected){
            String url = FrServerConfig.getDeleteCollectionUrl("theme", collect_id);
            PostRequest request = new PostRequest(url, FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    follow_btn.setText(R.string.follow);
                    follow_btn.setBackground(getResources().getDrawable(R.color.active_color));
                    SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("hasDelete", true);
                    editor.putInt("delete_id", collect_id);
                    editor.commit();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    RequestErrorHelper.toast(ThemeActivity.this, volleyError);
                }
            });
            FrRequest.getInstance().request(request);
        }else{
            String url = FrServerConfig.getCreateCollectionUrl();
            JSONObject params = new JSONObject();
            try {
                params.put("type", "theme");
                params.put("id", theme.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PostRequest request = new PostRequest(url, FrApplication.getInstance().getToken(), params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    follow_btn.setText(R.string.cancel_follow);
                    follow_btn.setBackground(getResources().getDrawable(R.color.disable_color));
                    SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("hasDelete", false);
                    editor.putInt("delete_id", -1);
                    editor.commit();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    RequestErrorHelper.toast(ThemeActivity.this, volleyError);
                }
            });
            FrRequest.getInstance().request(request);
        }
        isCollected=!isCollected;
    }
}
