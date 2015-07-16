package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.ArticleCardAdapter;
import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.model.ArticleCard;

public class KnowledgeArticleActivity extends Activity implements View.OnClickListener {

    private RecyclerView frKnowledgeArticleRecyclerView;
    private RecyclerViewLayoutManager frKnowledgeArticleLayoutManager;
    private ArticleCardAdapter ArticleCardAdapter;
    private List<ArticleCard> dataList;

    private ImageView left_btn;
    private ImageView right_btn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_articles_list);

        initView();
        initData();
        initEvent();
        
    }

    private void initEvent() {
        left_btn.setOnClickListener(this);
        right_btn.setOnClickListener(this);
    }

    private void initData() {
        dataList = new ArrayList<ArticleCard>();
        getKnowledgeArticle();
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
    }

    public void getKnowledgeArticle() {
        for (int i=0;i< LocalDemo.authorName.length;i++){
            //String name, String author_name, int author_type, int Article_follow, int Article_read, int Article_background, int Article_author_background
            ArticleCard ac = new ArticleCard(LocalDemo.articleName[i],LocalDemo.articleTime[i],i*63,LocalDemo.articleBG[i]);
            dataList.add(ac);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:{
                finish();
                break;
            }
            case R.id.right_btn:{
                startActivity(new Intent(this, SearchActivity.class));
                break;
            }
        }        
    }
}
