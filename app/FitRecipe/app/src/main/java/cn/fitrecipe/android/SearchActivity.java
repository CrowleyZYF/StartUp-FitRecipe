package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.UI.BorderScrollView;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.function.RequestErrorHelper;
import pl.tajchert.sample.DotsTextView;

public class SearchActivity extends Activity implements View.OnClickListener {

    private ImageView back_btn, clear_btn;
    private EditText search_content;
    private TextView search_btn;

    private BorderScrollView scrollView;
    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;

    private RecyclerView frThemeRecipeRecyclerView;
    private RecyclerViewLayoutManager frThemeRecipeLayoutManager;
    private int start = 0;
    private int num = 10;
    private String keyword;
    List<Recipe> dataList;
    RecipeCardAdapter recipeCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        initData();
        initEvent();

    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.back_btn);
        clear_btn = (ImageView) findViewById(R.id.clear_btn);
        search_content = (EditText) findViewById(R.id.search_content);
        search_btn = (TextView) findViewById(R.id.search_btn);

        frThemeRecipeRecyclerView = (RecyclerView) findViewById(R.id.theme_recipe_recycler_view);
        frThemeRecipeLayoutManager = new RecyclerViewLayoutManager(this);
        frThemeRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frThemeRecipeRecyclerView.setLayoutManager(frThemeRecipeLayoutManager);

        loadingInterface = (LinearLayout) findViewById(R.id.loading_interface);
        scrollView = (BorderScrollView) findViewById(R.id.theme_content);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);
    }

    private void initData() {

    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        clear_btn.setOnClickListener(this);
        search_btn.setOnClickListener(this);
        scrollView.setOnBorderListener(new BorderScrollView.OnBorderListener() {
            @Override
            public void onBottom() {
                getData();
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
            case R.id.clear_btn:
                search_content.setText("");
                break;
            case R.id.search_btn:
                search();
                break;
            default:
                break;
        }
    }


    private void getData() {
        GetRequest request = null;
        try {
            request = new GetRequest(FrServerConfig.getSearchRecipeUrl(URLEncoder.encode(keyword, "utf-8"), start, num), FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    if(res.has("data")){
                        try {
                            JSONArray data = res.getJSONArray("data");
                            processData(data);
                            if(start == 0) {
                                hideLoading(false, "");
                                if(data.length() == 0)
                                    Toast.makeText(SearchActivity.this, "没有找到符合条件的菜谱！", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (data.length() == 0) {
                                    scrollView.setNoMore();
                                    Toast.makeText(SearchActivity.this, "没有多余的菜谱！", Toast.LENGTH_SHORT).show();
                                }
                                scrollView.setCompleteMore();
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
                    RequestErrorHelper.toast(SearchActivity.this, volleyError);
                }
            });
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        FrRequest.getInstance().request(request);
    }

    private void showLoading() {
        loadingInterface.setVisibility(View.VISIBLE);
        dotsTextView.start();
        scrollView.setVisibility(View.GONE);
    }

    private void search() {
        keyword = search_content.getText().toString();
        if(keyword == null || keyword.length() == 0) {
            Toast.makeText(SearchActivity.this, "搜索内容不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading();
        start = 0;
        getData();
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }else{
            scrollView.setVisibility(View.VISIBLE);
        }
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


}
