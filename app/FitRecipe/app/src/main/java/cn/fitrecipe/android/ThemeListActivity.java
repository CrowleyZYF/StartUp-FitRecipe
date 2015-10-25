package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Adpater.ThemeCardAdapter;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.UI.BorderScrollView;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.entity.Theme;
import cn.fitrecipe.android.function.RequestErrorHelper;
import pl.tajchert.sample.DotsTextView;

public class ThemeListActivity extends Activity implements View.OnClickListener{

    private BorderScrollView scrollView;
    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;

    private ImageView back_btn;
    private RecyclerView recyclerView;
    private RecyclerViewLayoutManager layoutManager;
    private int start = 0;
    private int num = 10;
    List<Theme> dataList;
    ThemeCardAdapter themeCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_list);
        initView();
        getData();
        initEvent();
    }

    private void getData() {
        //get Data from network
        String token = FrApplication.getInstance().getToken();
        String url = FrServerConfig.getAllTheme(start, num);
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
                                scrollView.setNoMore();
                                Toast.makeText(ThemeListActivity.this, "没有多余的主题", Toast.LENGTH_SHORT).show();
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
                RequestErrorHelper.toast(ThemeListActivity.this, volleyError);
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

        Gson gson = new Gson();
        List<Theme> themes =  gson.fromJson(data.toString(), new TypeToken<List<Theme>>() {
        }.getType());
        if(themes != null && themes.size() > 0)
            dataList.addAll(themes);

        if(themeCardAdapter == null) {
            themeCardAdapter = new ThemeCardAdapter(this, dataList);
            recyclerView.setAdapter(themeCardAdapter);
        }
        else
            themeCardAdapter.notifyDataSetChanged();
    }

    private void initView(){
        back_btn = (ImageView) findViewById(R.id.back_btn);

        recyclerView = (RecyclerView) findViewById(R.id.theme_recipe_recycler_view);
        layoutManager = new RecyclerViewLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        loadingInterface = (LinearLayout) findViewById(R.id.loading_interface);
        scrollView = (BorderScrollView) findViewById(R.id.theme_content);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);
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

    private void initEvent() {
        back_btn.setOnClickListener(this);
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
            default:
                break;
        }
    }

}
