package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.UI.SlidingMenu;

public class CategoryActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private SlidingMenu mRightMenu;

    private GridView gridView;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> dataList;

    private ImageView back_btn;
    private ImageView filter_btn;
    private ImageView filter_btn_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_gridview_container);

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

        gridView = (GridView) findViewById(R.id.category_gridview);

        mRightMenu = (SlidingMenu) findViewById(R.id.container_layout);
    }

    private void initData() {
        dataList=new ArrayList<Map<String,Object>>();
        adapter=new SimpleAdapter(this, getData(), R.layout.activity_category_gridview_list_item, new String[]{"pic","name"}, new int[]{R.id.pic,R.id.name});
        gridView.setAdapter(adapter);
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        filter_btn.setOnClickListener(this);
        filter_btn_2.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
    }

    private List<Map<String, Object>> getData() {
        int[] drawable = { R.drawable.category_chicken, R.drawable.category_beef,
                R.drawable.category_fish, R.drawable.category_egg, R.drawable.category_seafood,
                R.drawable.category_rice, R.drawable.category_fruit,
                R.drawable.category_dessert, R.drawable.category_other };
        String[] iconName = { "鸡肉", "牛肉", "鱼肉", "蛋/奶", "海鲜", "米面", "果蔬",
                "点心", "其他"};
        for (int i = 0; i < drawable.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pic", drawable[i]);
            map.put("name", iconName[i]);
            dataList.add(map);
        }
        Log.i("Main", "size=" + dataList.size());
        return dataList;
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
            case R.id.close_menu_btn:
                mRightMenu.toggle();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int test = view.getId();
        switch (parent.getId()){
            case R.id.category_gridview:
                startActivity(new Intent(this, CategoryResultActivity.class));
                break;
            default:
                break;
        }

    }
}
