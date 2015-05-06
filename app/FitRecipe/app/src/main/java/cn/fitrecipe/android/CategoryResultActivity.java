package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.UI.SlidingMenu;
import cn.fitrecipe.android.UI.rcListLinearLayoutManager;
import cn.fitrecipe.android.model.RecipeCard;

public class CategoryResultActivity extends Activity implements View.OnClickListener, View.OnTouchListener {

    private SlidingMenu mRightMenu;

    private ImageView back_btn;
    private ImageView filter_btn;

    private RecyclerView frThemeRecipeRecyclerView;
    private rcListLinearLayoutManager frThemeRecipeLayoutManager;

    private ScrollView category_result_list;
    private LinearLayout category_intro;

    private int needShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_result);

        initView();
        initData();
        initEvent();

    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.back_btn);
        filter_btn = (ImageView) findViewById(R.id.filter_btn);

        mRightMenu = (SlidingMenu) findViewById(R.id.filter_menu);

        frThemeRecipeRecyclerView = (RecyclerView) findViewById(R.id.theme_recipe_recycler_view);
        frThemeRecipeLayoutManager = new rcListLinearLayoutManager(this);
        frThemeRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frThemeRecipeRecyclerView.setLayoutManager(frThemeRecipeLayoutManager);

        category_result_list = (ScrollView) findViewById(R.id.category_result_list);
        category_intro = (LinearLayout) findViewById(R.id.category_intro);
    }

    private void initData() {
        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this, getThemeRecipe());
        frThemeRecipeRecyclerView.setAdapter(recipeCardAdapter);
        needShow = 0;
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        filter_btn.setOnClickListener(this);
        category_result_list.setOnTouchListener(this);
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
            case R.id.filter_btn:
                mRightMenu.toggle();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (v.getScrollY() == 0) {
                    needShow++;
                    if(needShow==10){
                        //category_intro.setVisibility(View.VISIBLE);
                        needShow=0;
                    }
                    //Toast.makeText(this, "Top", Toast.LENGTH_SHORT).show();

                }/* else if (category_result_list.getChildAt(0).getMeasuredHeight() <= v.getHeight() + v.getScrollY()) {
                    Toast.makeText(this, "Bottom", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "view.getMeasuredHeight() = " + v.getMeasuredHeight()
                            + ", v.getHeight() = " + v.getHeight()
                            + ", v.getScrollY() = " + v.getScrollY()
                            + ", view.getChildAt(0).getMeasuredHeight() = " + category_result_list.getChildAt(0).getMeasuredHeight(), Toast.LENGTH_SHORT).show();
                }*/
                else{
                    //category_intro.setVisibility(View.GONE);
                }
                Log.i("my_info",Integer.toString(v.getScrollY()));
                break;
            default:
                break;
        }
        return false;
    }
}
