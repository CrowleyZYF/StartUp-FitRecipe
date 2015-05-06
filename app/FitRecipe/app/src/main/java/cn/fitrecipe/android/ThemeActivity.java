package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.UI.rcListLinearLayoutManager;
import cn.fitrecipe.android.model.RecipeCard;

public class ThemeActivity extends Activity implements View.OnClickListener {

    private ImageView back_btn;

    private RecyclerView frThemeRecipeRecyclerView;
    private rcListLinearLayoutManager frThemeRecipeLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme);

        initView();
        initData();
        initEvent();
    }

    private void initData() {
        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this, getThemeRecipe());
        frThemeRecipeRecyclerView.setAdapter(recipeCardAdapter);
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.back_btn);

        frThemeRecipeRecyclerView = (RecyclerView) findViewById(R.id.theme_recipe_recycler_view);
        frThemeRecipeLayoutManager = new rcListLinearLayoutManager(this);
        frThemeRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frThemeRecipeRecyclerView.setLayoutManager(frThemeRecipeLayoutManager);

    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
    }

    private List<RecipeCard> getThemeRecipe() {
        List<RecipeCard> result = new ArrayList<RecipeCard>();
        for (int i=5;i<9;i++){
            RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],0,(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
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
