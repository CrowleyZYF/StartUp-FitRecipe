package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.fitrecipe.android.Config.HttpUrl;
import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.SlidingPage;

public class RecipeActivity extends Activity implements View.OnClickListener, PopupWindow.OnDismissListener {
    private ScrollView recipe_scrollView;
    //成品图
    private ImageView recipe_pic;
    //标签
    private TextView recipe_tags;
    //名称
    private TextView recipe_name;
    //特色
    private TextView recipe_feature;
    //烹饪时间
    private TextView recipe_time;
    //每百克热量
    private TextView recipe_calorie;
    //收藏数
    private TextView recipe_collect;
    //评论数
    private TextView recipe_comment;
    //作者头像
    private ImageView avatar_pic;
    //作者名称
    private TextView author_name;
    //食谱简介
    private TextView recipe_intro;
    //食材表默认重量
    private TextView recipe_weight;
    //营养表默认重量
    private TextView ingredient_title_weight;
    //总热量
    private TextView recipe_all_calorie;
    //用户每日所需热量
    private TextView user_need_calorie;
    //所占百分比
    private TextView calorie_radio;
    //食材表
    private LinearLayoutForListView ingredient_listView;
    private SimpleAdapter ingredient_adapter;
    private List<Map<String, Object>> ingredient_dataList;
    //营养表
    private LinearLayoutForListView nutrition_listView;
    private SimpleAdapter nutrition_adapter;
    private List<Map<String, Object>> nutrition_dataList;
    //查看步骤
    private TextView check_procedure_btn;
    //设置按钮
    private ImageView set_btn;
    //返回按钮
    private ImageView back_btn;
    //菜单按钮
    private PopupWindow popupWindow;
    //菜单是否打开
    private boolean open = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_container);
        //获取ID
        Intent intent =getIntent();
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("id",intent.getStringExtra("id"));
        //初始化
        initView();
        initData(HttpUrl.generateURLString(HttpUrl.RECIPE_INFO_TYPE,params));
        initEvent();

        recipe_scrollView.smoothScrollTo(0, 0);
    }



    private void initView() {
        recipe_scrollView = (ScrollView) findViewById(R.id.recipe_scrollview);
        recipe_pic = (ImageView) findViewById(R.id.recipe_pic);
        recipe_tags = (TextView) findViewById(R.id.recipe_tags);
        recipe_name = (TextView) findViewById(R.id.recipe_name);
        recipe_feature = (TextView) findViewById(R.id.recipe_feature);
        recipe_time = (TextView) findViewById(R.id.recipe_time);
        recipe_calorie = (TextView) findViewById(R.id.recipe_calorie);
        recipe_collect = (TextView) findViewById(R.id.recipe_collect);
        recipe_comment = (TextView) findViewById(R.id.recipe_comment);
        avatar_pic = (ImageView) findViewById(R.id.avatar_pic);
        author_name = (TextView) findViewById(R.id.author_name);
        recipe_intro = (TextView) findViewById(R.id.recipe_intro);
        recipe_weight = (TextView) findViewById(R.id.recipe_weight);
        ingredient_title_weight = (TextView) findViewById(R.id.ingredient_title_weight);
        recipe_all_calorie = (TextView) findViewById(R.id.recipe_all_calorie);
        user_need_calorie = (TextView) findViewById(R.id.user_need_calorie);
        calorie_radio = (TextView) findViewById(R.id.calorie_radio);
        ingredient_listView = (LinearLayoutForListView) findViewById(R.id.recipe_ingredient_list);
        nutrition_listView = (LinearLayoutForListView) findViewById(R.id.recipe_nutrition_list);

        check_procedure_btn = (TextView) findViewById(R.id.check_procedure);
        set_btn = (ImageView) findViewById(R.id.set_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);

        View view = LayoutInflater.from(this).inflate(R.layout.activity_recipe_info_set, null);
        popupWindow = new PopupWindow(view, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 152, getResources().getDisplayMetrics()));
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(this);
        //刷新状态
        popupWindow.update();
    }

    private void initData(String url) {
        Toast.makeText(this, "URL: "+ url, Toast.LENGTH_LONG).show();
        ingredient_dataList=new ArrayList<Map<String,Object>>();
        nutrition_dataList=new ArrayList<Map<String,Object>>();

        getData();

        ingredient_adapter=new SimpleAdapter(this, ingredient_dataList, R.layout.activity_recipe_info_ingredient_item, new String[]{"item_name","item_weight","item_remark"}, new int[]{R.id.ingredient_name,R.id.ingredient_weight,R.id.ingredient_remark});
        ingredient_listView.setAdapter(ingredient_adapter);

        nutrition_adapter=new SimpleAdapter(this, nutrition_dataList, R.layout.activity_recipe_info_ingredient_item, new String[]{"item_name","item_weight","item_remark"}, new int[]{R.id.ingredient_name,R.id.ingredient_weight,R.id.ingredient_remark});
        nutrition_listView.setAdapter(nutrition_adapter);
    }

    private void getData() {

        //recipe_pic.setImageResource();
        recipe_tags.setText("早餐、鸡肉、元气、酸辣");
        recipe_name.setText("牛肉面");
        recipe_feature.setText("低脂");
        recipe_time.setText("烹饪时间："+"10"+"min");
        recipe_calorie.setText("热量："+"10"+"kcal/100g");
        recipe_collect.setText("收藏 "+"182");
        recipe_comment.setText("评论 "+"120");
        //avatar_pic.setImageResource();
        author_name.setText("健食记");
        recipe_intro.setText("        菜谱来自我的大姨，她很喜欢研究这些泰式的菜谱");
        recipe_weight.setText("10"+"克");
        ingredient_title_weight.setText("（"+"10"+"克）");
        recipe_all_calorie.setText("218"+"kcal");
        user_need_calorie.setText("（"+"1730"+"kcal）");
        calorie_radio.setText("16"+"%");

        for(int i=0;i< LocalDemo.ingredientName.length;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("item_name", LocalDemo.ingredientName[i]);//食材名称
            map.put("item_weight", LocalDemo.ingredientWeight[i]);//食材重量
            map.put("item_remark", LocalDemo.ingredientRemark[i]);//食材备注
            ingredient_dataList.add(map);
        }

        for(int i=0;i< LocalDemo.nutritionName.length;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("item_name", LocalDemo.nutritionName[i]);//营养元素名称，按照固定的顺序输入
            map.put("item_weight", "20g");//重量，要注意单位
            map.put("item_remark", "12%");//百分比，如果用户没有经过评测，则显示“--”
            nutrition_dataList.add(map);
        }
    }

    private void initEvent() {
        check_procedure_btn.setOnClickListener(this);
        set_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_procedure:{
                startActivity(new Intent(this, RecipeProcedureActivity.class));
                break;
            }
            case R.id.set_btn:{
                if(open){
                    popupWindow.dismiss();
                }else{
                    set_btn.setImageResource(R.drawable.icon_close);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.showAsDropDown(set_btn,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()), 0);
                }
                open=!open;
                break;
            }
            case R.id.back_btn:{
                finish();
                break;
            }

        }
    }

    @Override
    public void onDismiss() {
        open=!open;
        set_btn.setImageResource(R.drawable.icon_more);
    }
}
