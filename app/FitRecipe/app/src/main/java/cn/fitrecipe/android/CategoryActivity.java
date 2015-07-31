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
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;

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
    private TextView sure_btn;

    private CheckBox perfect_check;
    private CheckBox hp_check;
    private CheckBox lk_check;
    private CheckBox lf_check;
    private CheckBox breakfast_check;
    private CheckBox add_meal_check;
    private CheckBox dinner_check;
    private boolean[] filter_check;

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

        sure_btn = (TextView) findViewById(R.id.filter_sure_btn);

        gridView = (GridView) findViewById(R.id.category_gridview);

        mRightMenu = (SlidingMenu) findViewById(R.id.container_layout);

        perfect_check = (CheckBox) findViewById(R.id.perfect_check);
        hp_check = (CheckBox) findViewById(R.id.hp_check);
        lk_check = (CheckBox) findViewById(R.id.lk_check);
        lf_check = (CheckBox) findViewById(R.id.lf_check);
        breakfast_check = (CheckBox) findViewById(R.id.breakfast_check);
        add_meal_check = (CheckBox) findViewById(R.id.add_meal_check);
        dinner_check = (CheckBox) findViewById(R.id.dinner_check);
    }

    private void initData() {
        dataList=new ArrayList<Map<String,Object>>();
        adapter=new SimpleAdapter(this, getData(), R.layout.activity_category_gridview_list_item, new String[]{"pic","name"}, new int[]{R.id.pic,R.id.name});
        gridView.setAdapter(adapter);

        filter_check = new boolean[7];
        for(int i=0;i<7;i++){
            filter_check[i]=true;
        }
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        filter_btn.setOnClickListener(this);
        filter_btn_2.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
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
            case R.id.close_menu_btn:
            case R.id.filter_sure_btn:
                mRightMenu.toggle();
                break;
            default:
                break;
        }
    }

    private void updateFilter(){
        filter_check[0]=perfect_check.isChecked();
        filter_check[1]=hp_check.isChecked();
        filter_check[2]=lk_check.isChecked();
        filter_check[3]=lf_check.isChecked();
        filter_check[4]=breakfast_check.isChecked();
        filter_check[5]=add_meal_check.isChecked();
        filter_check[6]=dinner_check.isChecked();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.category_gridview:
                updateFilter();
                Intent intent=new Intent(this,CategoryResultActivity.class);
                String meat="";
                switch (position){
                    case 0:
                        meat="8";
                        break;
                    case 1:
                        meat="9";
                        break;
                    case 2:
                        meat="10";
                        break;
                    case 3:
                        meat="11";
                        break;
                    case 4:
                        meat="12";
                        break;
                    case 5:
                        meat="13";
                        break;
                    case 6:
                        meat="14";
                        break;
                    case 7:
                        meat="15";
                        break;
                    case 8:
                        meat="16";
                        break;
                }
                String effect="";
                if(filter_check[0]){
                    effect+="17,";
                }
                if(filter_check[1]){
                    effect+="2,";
                }
                if(filter_check[2]){
                    effect+="4,";
                }
                if(filter_check[3]){
                    effect+="3,";
                }
                String time="";
                if(filter_check[4]){
                    time+="5,";
                }
                if(filter_check[5]){
                    time+="7,";
                }
                if(filter_check[6]){
                    time+="6,";
                }
                intent.putExtra("meat", meat);
                intent.putExtra("effect", effect.substring(0,effect.length()-1));
                intent.putExtra("time", time.substring(0,time.length()-1));
                Toast.makeText(this, "Meat: " + meat+ "Effect: " + effect.substring(0,effect.length()-1) + "Time: " + time.substring(0,time.length()-1), Toast.LENGTH_LONG).show();
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
