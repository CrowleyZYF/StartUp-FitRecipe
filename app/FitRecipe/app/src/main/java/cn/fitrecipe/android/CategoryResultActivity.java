package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.UI.SlidingMenu;
import cn.fitrecipe.android.UI.rcListLinearLayoutManager;
import cn.fitrecipe.android.model.RecipeCard;

public class CategoryResultActivity extends Activity implements View.OnClickListener {

    private SlidingMenu mRightMenu;

    private ImageView back_btn;
    private ImageView filter_btn;
    private ImageView filter_btn_2;

    private RecyclerView frThemeRecipeRecyclerView;
    private rcListLinearLayoutManager frThemeRecipeLayoutManager;
    private RecipeCardAdapter recipeCardAdapter;
    private List<RecipeCard> dataList;

    private LinearLayout time_sort_btn;
    private TextView time_sort_text;
    private ImageView time_sort_icon;

    private LinearLayout like_sort_btn;
    private TextView like_sort_text;
    private ImageView like_sort_icon;

    private LinearLayout calorie_sort_btn;
    private TextView calorie_sort_text;
    private ImageView calorie_sort_icon;

    private int sort_type = 0;
    private boolean sort_des = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_result_container);

        initView();
        initData();
        initEvent();

    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        back_btn.setImageResource(R.drawable.icon_back_white);

        filter_btn = (ImageView) findViewById(R.id.right_btn);
        filter_btn.setImageResource(R.drawable.icon_filter);

        filter_btn_2 = (ImageView) findViewById(R.id.close_menu_btn);

        mRightMenu = (SlidingMenu) findViewById(R.id.container_layout);

        frThemeRecipeRecyclerView = (RecyclerView) findViewById(R.id.theme_recipe_recycler_view);
        frThemeRecipeLayoutManager = new rcListLinearLayoutManager(this);
        frThemeRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frThemeRecipeRecyclerView.setLayoutManager(frThemeRecipeLayoutManager);

        time_sort_btn = (LinearLayout) findViewById(R.id.time_sort_btn);
        time_sort_text = (TextView) findViewById(R.id.time_sort_text);
        time_sort_icon = (ImageView) findViewById(R.id.time_sort_icon);

        like_sort_btn = (LinearLayout) findViewById(R.id.like_sort_btn);
        like_sort_text = (TextView) findViewById(R.id.like_sort_text);
        like_sort_icon = (ImageView) findViewById(R.id.like_sort_icon);

        calorie_sort_btn = (LinearLayout) findViewById(R.id.calorie_sort_btn);
        calorie_sort_text = (TextView) findViewById(R.id.calorie_sort_text);
        calorie_sort_icon = (ImageView) findViewById(R.id.calorie_sort_icon);
    }

    private void initData() {
        dataList = new ArrayList<RecipeCard>();
        getThemeRecipe(sort_type,sort_des);
        recipeCardAdapter = new RecipeCardAdapter(this, dataList);
        frThemeRecipeRecyclerView.setAdapter(recipeCardAdapter);
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        filter_btn.setOnClickListener(this);
        filter_btn_2.setOnClickListener(this);
        time_sort_btn.setOnClickListener(this);
        like_sort_btn.setOnClickListener(this);
        calorie_sort_btn.setOnClickListener(this);
    }

    private void getThemeRecipe(int type,boolean des) {
        dataList.clear();
        if(type==0){
            if(des){
                for (int i=5;i<9;i++){
                    RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],0,(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
                    dataList.add(rc);
                }
            }else{
                for (int i=8;i>4;i--){
                    RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],0,(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
                    dataList.add(rc);
                }
            }
        }
        else if(type==1){
            if(des){
                for (int i=1;i<5;i++){
                    RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],0,(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
                    dataList.add(rc);
                }
            }else{
                for (int i=4;i>0;i--){
                    RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],0,(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
                    dataList.add(rc);
                }
            }
        }
        else if(type==2){
            if(des){
                for (int i=3;i<7;i++){
                    RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],0,(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
                    dataList.add(rc);
                }
            }else{
                for (int i=6;i>2;i--){
                    RecipeCard rc = new RecipeCard(LocalDemo.recipeName[i],0,(20+i),(200+i*10),(50+i*10),LocalDemo.recipeBG[i]);
                    dataList.add(rc);
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:
                finish();
                break;
            case R.id.right_btn:
                mRightMenu.toggle();
                break;
            case R.id.time_sort_btn:
                resetTextColor();
                time_sort_text.setTextColor(getResources().getColor(R.color.base_color));
                sort_type = 0;
                if(sort_des){
                    time_sort_icon.setImageResource(R.drawable.icon_arrow_up_active);
                }else{
                    time_sort_icon.setImageResource(R.drawable.icon_arrow_down_active);
                }
                sort_des = !sort_des;
                getThemeRecipe(sort_type,sort_des);
                recipeCardAdapter.notifyDataSetChanged();
                break;
            case R.id.like_sort_btn:
                resetTextColor();
                like_sort_text.setTextColor(getResources().getColor(R.color.base_color));
                sort_type = 1;
                if(sort_des){
                    like_sort_icon.setImageResource(R.drawable.icon_arrow_up_active);
                }else{
                    like_sort_icon.setImageResource(R.drawable.icon_arrow_down_active);
                }
                sort_des = !sort_des;
                getThemeRecipe(sort_type,sort_des);
                recipeCardAdapter.notifyDataSetChanged();
                break;
            case R.id.calorie_sort_btn:
                resetTextColor();
                calorie_sort_text.setTextColor(getResources().getColor(R.color.base_color));
                sort_type = 2;
                if(sort_des){
                    calorie_sort_icon.setImageResource(R.drawable.icon_arrow_up_active);
                }else{
                    calorie_sort_icon.setImageResource(R.drawable.icon_arrow_down_active);
                }
                sort_des = !sort_des;
                getThemeRecipe(sort_type,sort_des);
                recipeCardAdapter.notifyDataSetChanged();
                break;
            case R.id.close_menu_btn:
                mRightMenu.toggle();
                break;
            default:
                break;
        }
    }

    private void resetTextColor(){
        time_sort_text.setTextColor(getResources().getColor(R.color.login_input_text_color));
        like_sort_text.setTextColor(getResources().getColor(R.color.login_input_text_color));
        calorie_sort_text.setTextColor(getResources().getColor(R.color.login_input_text_color));
        time_sort_icon.setImageResource(R.drawable.icon_arrow_up);
        like_sort_icon.setImageResource(R.drawable.icon_arrow_up);
        calorie_sort_icon.setImageResource(R.drawable.icon_arrow_up);
    }
}
