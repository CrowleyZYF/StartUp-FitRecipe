package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);

        initView();
        initData();
        initEvent();

    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.back_btn);
        filter_btn = (ImageView) findViewById(R.id.filter_btn);

        gridView = (GridView) findViewById(R.id.gridView);

        mRightMenu = (SlidingMenu) findViewById(R.id.filter_menu);

    }

    private void initData() {
        dataList=new ArrayList<Map<String,Object>>();
        adapter=new SimpleAdapter(this, getData(), R.layout.category_item, new String[]{"pic","name"}, new int[]{R.id.pic,R.id.name});
        gridView.setAdapter(adapter);
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        filter_btn.setOnClickListener(this);
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
            case R.id.back_btn:
                finish();
                break;
            case R.id.filter_btn:
                mRightMenu.toggle();
               /* View popupWindow_view = getLayoutInflater().inflate(R.layout.filter, null,false);
                // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
                popupWindow = new PopupWindow(popupWindow_view, 600, LinearLayout.LayoutParams.MATCH_PARENT, true);
                // 设置动画效果
                popupWindow.setAnimationStyle(R.style.AnimationFade);
                // 这里是位置显示方式,在屏幕的左侧
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                popupWindow.showAtLocation(CategoryActivity.this.findViewById(R.id.category_layout), Gravity.RIGHT, 0, 0);
                // 点击其他地方消失
                popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                            popupWindow = null;
                        }
                        return false;
                    }
                });*/
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int test = view.getId();
        switch (parent.getId()){
            case R.id.gridView:
                startActivity(new Intent(this, CategoryResultActivity.class));
                break;
            default:
                break;
        }

    }
}
