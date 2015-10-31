package cn.fitrecipe.android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.entity.Collection;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.entity.Series;
import cn.fitrecipe.android.entity.Theme;
import cn.fitrecipe.android.fragment.KnowledgeCollectFragment;
import cn.fitrecipe.android.fragment.RecipeCollectFragment;
import cn.fitrecipe.android.fragment.RecipeFragment;
import cn.fitrecipe.android.fragment.ThemeFragment;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class CollectActivity extends FragmentActivity implements View.OnClickListener {
    private ImageView back_btn;
    private ImageView right_btn;
    private TextView header_name_text;

    private LinearLayout me_collect_recipe;
    private LinearLayout me_collect_theme;

    private Fragment recipe_fragment;
    private Fragment theme_fragment;

    private ImageView collect_recipe_active_line_1;
    private ImageView collect_recipe_active_line_2;
    private ImageView collect_theme_active_line_1;
    private ImageView collect_theme_active_line_2;



    private Boolean hasDelete = false;
    private int delete_id = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        Intent intent =getIntent();
        initView();
        initEvent();
        if(intent.hasExtra("tab")){
            setSelect(intent.getIntExtra("tab",2));
        }else {
            setSelect(0);
        }
    }

    public Boolean getHasDelete() {
        return hasDelete;
    }

    public int getDelete_id() {
        return delete_id;
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        me_collect_recipe.setOnClickListener(this);
        me_collect_theme.setOnClickListener(this);
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        right_btn = (ImageView) findViewById(R.id.right_btn);
        back_btn.setImageResource(R.drawable.icon_back_white);
        right_btn.setVisibility(View.GONE);
        header_name_text = (TextView) findViewById(R.id.header_name_text);
        header_name_text.setText("我的收藏");

        me_collect_recipe = (LinearLayout) findViewById(R.id.collect_recipe);
        me_collect_theme = (LinearLayout) findViewById(R.id.collect_theme);

        collect_recipe_active_line_1 = (ImageView) findViewById(R.id.collect_recipe_active_line_1);
        collect_recipe_active_line_2 = (ImageView) findViewById(R.id.collect_recipe_active_line_2);
        collect_theme_active_line_1 = (ImageView) findViewById(R.id.collect_theme_active_line_1);
        collect_theme_active_line_2 = (ImageView) findViewById(R.id.collect_theme_active_line_2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:
                finish();
                break;
            case R.id.collect_recipe:
                setSelect(0);
                break;
            case R.id.collect_theme:
                setSelect(1);
                break;
        }
    }

    private void setSelect(int tab) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        hideFragment(transaction);
        collect_recipe_active_line_1.setVisibility(View.INVISIBLE);
        collect_recipe_active_line_2.setBackground(getResources().getDrawable(R.drawable.theme_line));
        collect_theme_active_line_1.setVisibility(View.INVISIBLE);
        collect_theme_active_line_2.setBackground(getResources().getDrawable(R.drawable.theme_line));
        switch (tab)
        {
            case 0:
                if (recipe_fragment == null){
                    recipe_fragment = new RecipeCollectFragment();
                    transaction.add(R.id.collect_content, recipe_fragment);
                } else{
                    transaction.show(recipe_fragment);
                }
                right_btn.setVisibility(View.GONE);
                collect_recipe_active_line_1.setVisibility(View.VISIBLE);
                collect_recipe_active_line_2.setBackground(getResources().getDrawable(R.drawable.theme_line_active));
                break;
            case 1:
                if (theme_fragment == null){
                    theme_fragment = new ThemeFragment();
                    transaction.add(R.id.collect_content, theme_fragment);
                } else{
                    transaction.show(theme_fragment);
                }
                right_btn.setVisibility(View.GONE);
                collect_theme_active_line_1.setVisibility(View.VISIBLE);
                collect_theme_active_line_2.setBackground(getResources().getDrawable(R.drawable.theme_line_active));
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (theme_fragment != null){
            transaction.hide(theme_fragment);
        }
        if (recipe_fragment != null){
            transaction.hide(recipe_fragment);
        }
    }
}
