package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.fitrecipe.android.Adpater.ArticleCardAdapter;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.Article;
import cn.fitrecipe.android.entity.Series;

public class KnowledgeArticleActivity extends Activity implements View.OnClickListener {

    private RecyclerView frKnowledgeArticleRecyclerView;
    private RecyclerViewLayoutManager frKnowledgeArticleLayoutManager;
    private ArticleCardAdapter ArticleCardAdapter;
    private List<Article> dataList;

    //
    private RelativeLayout series_img;
    private TextView series_title;
    private TextView series_introduce;


    private ImageView left_btn;
    private ImageView right_btn;

    private Series series;
    //是否已经收藏
    private boolean isCollected = false;
    private TextView follow_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_articles_list);

        series = (Series) getIntent().getSerializableExtra("series");

        initView();
        getData();
        initEvent();
        
    }

    private void initEvent() {
        left_btn.setOnClickListener(this);
        right_btn.setOnClickListener(this);
        follow_btn.setOnClickListener(this);
    }

    private void getData() {
        String url = FrServerConfig.getArticleInSeries(series.getId());
        GetRequest request = new GetRequest(url, FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res.has("data")) {
                    try {
                        JSONObject data = res.getJSONObject("data");
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
        if(isCollected){
            follow_btn.setText(R.string.cancel_follow);
            follow_btn.setBackground(getResources().getDrawable(R.color.disable_color));
            right_btn.setImageResource(R.drawable.icon_like_green);
        }else{
            follow_btn.setText(R.string.follow);
            follow_btn.setBackground(getResources().getDrawable(R.color.active_color));
            right_btn.setImageResource(R.drawable.icon_like_noshadow);
        }
    }

    private void processData(JSONObject data) {
        series = new Gson().fromJson(data.toString(), Series.class);
        dataList = series.getArticle_set();
        ArticleCardAdapter = new ArticleCardAdapter(this, dataList);
        frKnowledgeArticleRecyclerView.setAdapter(ArticleCardAdapter);
    }

    private void initView() {
        frKnowledgeArticleRecyclerView = (RecyclerView) findViewById(R.id.knowledge_article_recycler_view);
        frKnowledgeArticleLayoutManager = new RecyclerViewLayoutManager(this);
        frKnowledgeArticleLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frKnowledgeArticleRecyclerView.setLayoutManager(frKnowledgeArticleLayoutManager);

        left_btn = (ImageView) findViewById(R.id.left_btn);
        left_btn.setImageResource(R.drawable.icon_back_white);

        right_btn = (ImageView) findViewById(R.id.right_btn);
        right_btn.setImageResource(R.drawable.icon_like_noshadow);

        series_img = (RelativeLayout) findViewById(R.id.series_img);
        FrApplication.getInstance().getMyImageLoader().displayImage(series_img, series.getImg_cover());
        series_title = (TextView) findViewById(R.id.series_title);
        series_title.setText(series.getTitle());
        series_introduce = (TextView) findViewById(R.id.series_introduce);
        series_introduce.setText(series.getIntroduce());

        follow_btn = (TextView) findViewById(R.id.follow_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:{
                finish();
                break;
            }
            case R.id.right_btn:
            case R.id.follow_btn:
                collect_series();
                break;
            default:
                break;
        }        
    }

    public void collect_series(){
        if(isCollected){
            Toast.makeText(this, "取消关注", Toast.LENGTH_LONG).show();
            follow_btn.setText(R.string.follow);
            follow_btn.setBackground(getResources().getDrawable(R.color.active_color));
            right_btn.setImageResource(R.drawable.icon_like_noshadow);
        }else{
            Toast.makeText(this,"关注",Toast.LENGTH_LONG).show();
            follow_btn.setText(R.string.cancel_follow);
            follow_btn.setBackground(getResources().getDrawable(R.color.disable_color));
            right_btn.setImageResource(R.drawable.icon_like_green);
        }
        isCollected=!isCollected;
    }
}
