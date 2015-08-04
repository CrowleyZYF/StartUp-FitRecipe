package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.rey.material.widget.CheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.UI.BorderScrollView;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.UI.SlidingMenu;
import cn.fitrecipe.android.model.RecipeCard;
import pl.tajchert.sample.DotsTextView;

public class CategoryResultActivity extends Activity implements View.OnClickListener {

    private BorderScrollView categoryContent;
    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;

    private SlidingMenu mRightMenu;

    private ImageView back_btn;
    private ImageView filter_btn;

    private RecyclerView frThemeRecipeRecyclerView;
    private RecyclerViewLayoutManager frThemeRecipeLayoutManager;
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
    private TextView filter_sure_btn;

    private int[] tese_ids = new int[] {R.id.perfect_check, R.id.hp_check, R.id.lk_check, R.id.lf_check};
    private int[] time_ids = new int[]{R.id.breakfast_check, R.id.add_meal_check, R.id.dinner_check};

    private int[] values = new int[]{17, 2, 4, 3, 5, 7, 6};
    private CheckBox[] checkBoxes;

    private int sort_type = 0;
    private boolean sort_des = false;

    private String meat, effect, time, order;
    private boolean desc;
    private int start, num;

    private final String DURATION = "duration";
    private final String CALORIES = "calories";
    private final String LIKE = "like";

    private boolean getMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_result_container);

        Intent intent = getIntent();
        meat = intent.getStringExtra("meat");
        effect = intent.getStringExtra("effect");
        time = intent.getStringExtra("time");
        desc = false;
        order = DURATION;
        start = 0;
        num = 4;

        initView();
        getData();
        initEvent();
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        back_btn.setImageResource(R.drawable.icon_back_white);

        filter_btn = (ImageView) findViewById(R.id.right_btn);
        filter_btn.setImageResource(R.drawable.icon_filter);

        filter_sure_btn = (TextView) findViewById(R.id.filter_sure_btn);

        mRightMenu = (SlidingMenu) findViewById(R.id.container_layout);

        frThemeRecipeRecyclerView = (RecyclerView) findViewById(R.id.theme_recipe_recycler_view);
        frThemeRecipeLayoutManager = new RecyclerViewLayoutManager(this);
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

        loadingInterface = (LinearLayout) findViewById(R.id.loading_interface);
        categoryContent = (BorderScrollView) findViewById(R.id.category_result_list);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);

        checkBoxes = new CheckBox[tese_ids.length + time_ids.length];
        for(int i = 0; i < tese_ids.length; i++)
            checkBoxes[i] = (CheckBox) findViewById(tese_ids[i]);
        for(int i = tese_ids.length; i < checkBoxes.length; i++)
            checkBoxes[i] = (CheckBox) findViewById(time_ids[i - tese_ids.length]);

    }

    private void getData(){
        if(!getMore) {
            start = 0;
            beginLoading();
        }
        JSONObject params = new JSONObject();
        try {
            params.put("meat", meat);
            params.put("effect", effect);
            params.put("time", time);
            params.put("order", order);
            params.put("desc", desc);
            params.put("start", start);
            params.put("num", num);
            String url = FrServerConfig.getRecipeListByCategory(params);
            System.out.println(url);
            SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
            String token = null;
            if(sp.contains("token"))
                token = sp.getString("token", null);
            GetRequest request = new GetRequest(url, token, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    if(res.has("data")) {
                        JSONArray data = null;
                        try {
                            data = res.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(!getMore)
                            hideLoading(false, "");
                        else {
                            if(data.length() == 0)
                                Toast.makeText(CategoryResultActivity.this, "没有多余的食谱", Toast.LENGTH_SHORT).show();
                            categoryContent.setCompleteMore();
                        }
                        start += num;
                        try {
                            processData(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if(!getMore)
                        hideLoading(true, getResources().getString(R.string.network_error));
                    else {
                        categoryContent.setCompleteMore();
                        Toast.makeText(CategoryResultActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            FrRequest.getInstance().request(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void processData(JSONArray data) throws JSONException {
        if(dataList == null)
            dataList = new ArrayList<RecipeCard>();
        else
            if(!getMore)
                dataList.clear();
        if(data != null) {
            for(int i = 0; i < data.length(); i++) {
                JSONObject recipe = data.getJSONObject(i);
                String recipe_name = recipe.getString("title");
                int recipe_id = recipe.getInt("id");
                int duration = recipe.getInt("duration");
                double calories = recipe.getDouble("calories");
                String img = FrServerConfig.getImageCompressed(recipe.getString("img"));
                JSONArray effects = recipe.getJSONArray("effect_labels");
                String function = "不限";
                if(effects != null && effects.length() > 0)
                    function = effects.getJSONObject(0).getString("name");
                RecipeCard rc = new RecipeCard(recipe_name, recipe_id, function, duration, (int)calories, 100, img);
                dataList.add(rc);
            }
        }
        if(recipeCardAdapter == null) {
            recipeCardAdapter = new RecipeCardAdapter(this, dataList);
            frThemeRecipeRecyclerView.setAdapter(recipeCardAdapter);
        }else
            if(data != null && data.length() > 0)
                recipeCardAdapter.notifyDataSetChanged();
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }else{
            categoryContent.setVisibility(View.VISIBLE);
        }
    }

    private void beginLoading(){
        loadingInterface.setVisibility(View.VISIBLE);
        dotsTextView.start();
        categoryContent.setVisibility(View.GONE);
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        filter_btn.setOnClickListener(this);
        time_sort_btn.setOnClickListener(this);
        like_sort_btn.setOnClickListener(this);
        calorie_sort_btn.setOnClickListener(this);
        filter_sure_btn.setOnClickListener(this);
        categoryContent.setOnBorderListener(new BorderScrollView.OnBorderListener() {
            @Override
            public void onBottom() {
                getMore = true;
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
                desc = sort_des;
                order=DURATION;
                getMore = false;
                getData();
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
                desc = sort_des;
                order = "";
                getMore = false;
                getData();
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
                desc = sort_des;
                order = CALORIES;
                getMore = false;
                getData();
                break;
            case R.id.filter_sure_btn:
                mRightMenu.toggle();
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < tese_ids.length; i++) {
                    if(checkBoxes[i].isChecked()) {
                        sb.append(values[i]);
                        sb.append(",");
                    }
                }
                if(sb.length() > 0)
                    sb.substring(0, sb.length() - 1);
                effect = sb.toString();
                sb.setLength(0);

                for(int i = 0; i < time_ids.length; i++) {
                    if(checkBoxes[i + tese_ids.length].isChecked()) {
                        sb.append(values[i + tese_ids.length]);
                        sb.append(",");
                    }
                }

                if(sb.length() > 0)
                    sb.substring(0, sb.length() - 1);
                time = sb.toString();
                getMore = false;
                getData();
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
