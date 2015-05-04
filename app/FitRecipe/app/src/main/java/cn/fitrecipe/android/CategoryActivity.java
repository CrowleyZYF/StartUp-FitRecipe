package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends Activity{

    private GridView gridView;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        initView();
        initData();
        initEvent();
        dataList=new ArrayList<Map<String,Object>>();
        adapter=new SimpleAdapter(this, getData(), R.layout.category_item, new String[]{"pic","name"}, new int[]{R.id.pic,R.id.name});
        gridView.setAdapter(adapter);
    }

    private void initView() {

    }

    private void initData() {

    }

    private void initEvent() {

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

}
