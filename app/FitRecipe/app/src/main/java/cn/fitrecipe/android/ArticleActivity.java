package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import cn.fitrecipe.android.Config.HttpUrl;

public class ArticleActivity extends Activity{

    private ImageView left_btn;
    private ImageView right_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_article);
        //获取ID
        Intent intent =getIntent();
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("id",intent.getStringExtra("id"));
        //初始化
        initView();
        initData(HttpUrl.generateURLString(HttpUrl.RECIPE_INFO_TYPE, params));
        initEvent();
    }

    private void initView() {

    }

    private void initData() {

    }

    private void initEvent() {

    }

}
