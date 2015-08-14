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

import cn.fitrecipe.android.Adpater.SeriesCardAdapter;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.Series;

public class KnowledgeSeriesActivity extends Activity implements View.OnClickListener {

    private RecyclerView frKnowledgeSeriesRecyclerView;
    private RecyclerViewLayoutManager frKnowledgeSeriesLayoutManager;
    private SeriesCardAdapter seriesCardAdapter;
    private List<Series> dataList;

    private ImageView left_btn;
    private ImageView right_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_series_list);

        initView();
        initData();
        initEvent();

    }

    private void initEvent() {
        left_btn.setOnClickListener(this);
        right_btn.setOnClickListener(this);
    }

    private void initData() {
        dataList = new ArrayList<Series>();
        getKnowledgeSeries();
        seriesCardAdapter = new SeriesCardAdapter(this, dataList);
        frKnowledgeSeriesRecyclerView.setAdapter(seriesCardAdapter);
    }

    private void initView() {
        frKnowledgeSeriesRecyclerView = (RecyclerView) findViewById(R.id.knowledge_series_recycler_view);
        frKnowledgeSeriesLayoutManager = new RecyclerViewLayoutManager(this);
        frKnowledgeSeriesLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frKnowledgeSeriesRecyclerView.setLayoutManager(frKnowledgeSeriesLayoutManager);

        left_btn = (ImageView) findViewById(R.id.left_btn);
        left_btn.setImageResource(R.drawable.icon_back_white);

        right_btn = (ImageView) findViewById(R.id.right_btn);
    }

    public void getKnowledgeSeries() {

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
