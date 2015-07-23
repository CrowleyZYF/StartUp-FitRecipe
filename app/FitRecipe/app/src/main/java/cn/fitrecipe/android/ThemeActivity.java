package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Config.HttpUrl;
import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.model.RecipeCard;

public class ThemeActivity extends Activity implements View.OnClickListener {

    private ImageView back_btn;

    private RecyclerView frThemeRecipeRecyclerView;
    private RecyclerViewLayoutManager frThemeRecipeLayoutManager;

    private int start = 0;
    private int num = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        //获取ID
        Intent intent =getIntent();
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("id",intent.getStringExtra("id"));
        params.put("start",start);
        params.put("num",num);
        start = start+num;

        initView();
        initData(HttpUrl.generateURLString(HttpUrl.THEME_INFO_TYPE, params));
        initEvent();
    }

    private void initData(String url) {
        Toast.makeText(this, "URL: " + url, Toast.LENGTH_LONG).show();

        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this, getThemeRecipe());
        frThemeRecipeRecyclerView.setAdapter(recipeCardAdapter);
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.back_btn);

        frThemeRecipeRecyclerView = (RecyclerView) findViewById(R.id.theme_recipe_recycler_view);
        frThemeRecipeLayoutManager = new RecyclerViewLayoutManager(this);
        frThemeRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frThemeRecipeRecyclerView.setLayoutManager(frThemeRecipeLayoutManager);

    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
    }

    private List<RecipeCard> getThemeRecipe() {
        List<RecipeCard> result = new ArrayList<RecipeCard>();
        for (int i=0;i<9;i++){
            RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],i,0,(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
            result.add(rc);
        }
        return result;
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
