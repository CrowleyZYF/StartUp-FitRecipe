package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.SlidingPage;

public class RecipeActivity extends Activity implements View.OnClickListener {

    private LinearLayoutForListView listView;
    private SimpleAdapter list_adapter;
    private List<Map<String, Object>> dataList;

    private LinearLayoutForListView listView2;
    private SimpleAdapter list_adapter2;
    private List<Map<String, Object>> dataList2;

    private LinearLayout toggle_btn;
    private LinearLayout toggle_btn_back;

    private SlidingPage mRightMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_container);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        listView = (LinearLayoutForListView) findViewById(R.id.recipe_ingredient_list);
        listView2 = (LinearLayoutForListView) findViewById(R.id.recipe_nutrition_list);

        toggle_btn = (LinearLayout) findViewById(R.id.toggle_btn);
        toggle_btn_back = (LinearLayout) findViewById(R.id.toggle_btn_back);
        mRightMenu = (SlidingPage) findViewById(R.id.nutrition_ingredient);
    }

    private void initData() {
        dataList=new ArrayList<Map<String,Object>>();
        dataList2=new ArrayList<Map<String,Object>>();

        getData();
        list_adapter=new SimpleAdapter(this, dataList, R.layout.activity_recipe_info_ingredient_item, new String[]{"item_name","item_weight"}, new int[]{R.id.ingredient_name,R.id.ingredient_weight});
        listView.setAdapter(list_adapter);

        list_adapter2=new SimpleAdapter(this, dataList2, R.layout.activity_recipe_info_ingredient_item, new String[]{"item_name","item_weight"}, new int[]{R.id.ingredient_name,R.id.ingredient_weight});
        listView2.setAdapter(list_adapter2);

    }

    private void getData() {
        for(int i=0;i<10;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("item_name", "米饭");
            map.put("item_weight", "200g");
            dataList.add(map);
        }

        for(int i=0;i<14;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("item_name", "米饭");
            map.put("item_weight", "200g");
            dataList2.add(map);
        }
    }

    private void initEvent() {
        toggle_btn.setOnClickListener(this);
        toggle_btn_back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toggle_btn:
                mRightMenu.toggle();
                break;
            case R.id.toggle_btn_back:
                mRightMenu.toggle();
                break;
        }

    }
}
