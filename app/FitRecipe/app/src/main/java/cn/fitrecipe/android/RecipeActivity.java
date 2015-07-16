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

    private LinearLayoutForListView ingredient_listView;
    private SimpleAdapter ingredient_adapter;
    private List<Map<String, Object>> ingredient_dataList;

    private LinearLayoutForListView nutrition_listView;
    private SimpleAdapter nutrition_adapter;
    private List<Map<String, Object>> nutrition_dataList;

    private ScrollView recipe_scrollView;

    private TextView check_procedure_btn;

    private ImageView set_btn;
    private ImageView back_btn;

    private PopupWindow popupWindow;

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
        ingredient_listView = (LinearLayoutForListView) findViewById(R.id.recipe_ingredient_list);
        nutrition_listView = (LinearLayoutForListView) findViewById(R.id.recipe_nutrition_list);

        recipe_scrollView = (ScrollView) findViewById(R.id.recipe_scrollview);

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
        for(int i=0;i< LocalDemo.ingredientName.length;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("item_name", LocalDemo.ingredientName[i]);
            map.put("item_weight", LocalDemo.ingredientWeight[i]);
            map.put("item_remark", LocalDemo.ingredientRemark[i]);
            ingredient_dataList.add(map);
        }

        for(int i=0;i< LocalDemo.nutritionName.length;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("item_name", LocalDemo.nutritionName[i]);
            map.put("item_weight", "20g");
            map.put("item_remark", "12%");
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
