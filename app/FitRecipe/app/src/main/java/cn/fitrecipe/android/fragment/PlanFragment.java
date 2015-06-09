package cn.fitrecipe.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.daimajia.androidviewhover.BlurLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.SlidingPage;
import cn.fitrecipe.android.UI.rcPlanViewPagerAdapter;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class PlanFragment extends Fragment implements View.OnClickListener {

    private SlidingPage mRightMenu;

    private ViewPager frFirstViewPager;
    private rcPlanViewPagerAdapter rcFirstViewPagerAdapter;

    private ViewPager frSecondViewPager;
    private rcPlanViewPagerAdapter rcSecondViewPagerAdapter;

    private ViewPager frThirdViewPager;
    private rcPlanViewPagerAdapter rcThirdViewPagerAdapter;

    private List<View> listViews1 = null;
    private List<View> listViews2 = null;
    private List<View> listViews3 = null;
    private int[] imgs = { R.drawable.ztest001, R.drawable.ztest002, R.drawable.ztest003,
            R.drawable.ztest004, R.drawable.ztest005, R.drawable.ztest006};

    private int[][] begin = {
            {0,4,2,6,3},
            {1,3,1,5,2},
            {4,2,3,2,4}
    };
    private int[][] end = {
            {5,8,6,8,5},
            {5,8,7,8,4},
            {8,5,6,5,7}
    };

    private LinearLayoutForListView nutrition_listView;
    private SimpleAdapter nutrition_adapter;
    private List<Map<String, Object>> nutrition_dataList;

    private RelativeLayout plan_title;

    private ImageView prev_btn;
    private ImageView next_btn;
    private ImageView meal_pic;
    private TextView meal_name;
    private TextView meal_name_info;

    private int nowState = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);
        //BlurLayout.setGlobalDefaultDuration(450);

        initView(v);
        initData();
        initEvent();

        return v;
    }

    private void initView(View v) {
        frFirstViewPager = (ViewPager) v.findViewById(R.id.first);
        frSecondViewPager = (ViewPager) v.findViewById(R.id.second);
        frThirdViewPager = (ViewPager) v.findViewById(R.id.third);

        mRightMenu = (SlidingPage) v.findViewById(R.id.filter_menu);

        nutrition_listView = (LinearLayoutForListView) v.findViewById(R.id.recipe_nutrition_list);

        plan_title = (RelativeLayout) v.findViewById(R.id.plan_title);

        prev_btn = (ImageView) v.findViewById(R.id.prev_btn);
        next_btn = (ImageView) v.findViewById(R.id.next_btn);
        meal_pic = (ImageView) v.findViewById(R.id.meal_pic);

        meal_name = (TextView) v.findViewById(R.id.meal_name);
        meal_name_info = (TextView) v.findViewById(R.id.meal_notice);
    }

    private void initData() {
        listViews1 = new ArrayList<View>();
        listViews2 = new ArrayList<View>();
        listViews3 = new ArrayList<View>();

        initMeal();
        rcFirstViewPagerAdapter = new rcPlanViewPagerAdapter(listViews1,this.getActivity());
        frFirstViewPager.setAdapter(rcFirstViewPagerAdapter);
        rcSecondViewPagerAdapter = new rcPlanViewPagerAdapter(listViews2,this.getActivity());
        frSecondViewPager.setAdapter(rcSecondViewPagerAdapter);
        rcThirdViewPagerAdapter = new rcPlanViewPagerAdapter(listViews3,this.getActivity());
        frThirdViewPager.setAdapter(rcThirdViewPagerAdapter);

        nutrition_dataList=new ArrayList<Map<String,Object>>();
        getData();
        nutrition_adapter=new SimpleAdapter(this.getActivity(), nutrition_dataList, R.layout.activity_recipe_info_ingredient_item, new String[]{"item_name","item_weight","item_remark"}, new int[]{R.id.ingredient_name,R.id.ingredient_weight,R.id.ingredient_remark});
        nutrition_listView.setAdapter(nutrition_adapter);
    }

    private void initMeal() {
        listViews1.clear();
        listViews2.clear();
        listViews3.clear();
        meal_name.setText(LocalDemo.mealName[nowState]);
        meal_name_info.setText(LocalDemo.mealNotice[nowState]);
        meal_pic.setImageResource(LocalDemo.mealPic[nowState]);
        for (int i = begin[0][nowState]; i < end[0][nowState]; i++) {
            View view1 = LayoutInflater.from(this.getActivity()).inflate(
                    R.layout.fragment_plan_recipe_list_card, null);
            BlurLayout iv1 = (BlurLayout) view1.findViewById(R.id.sample);
            iv1.setBackgroundResource(LocalDemo.recipeBG[i]);
            TextView tv1 = (TextView) view1.findViewById(R.id.recipe_name);
            tv1.setText(LocalDemo.recipeName[i]);
            listViews1.add(view1);
        }
        for (int i = begin[1][nowState]; i < end[1][nowState]; i++) {
            View view2 = LayoutInflater.from(this.getActivity()).inflate(
                    R.layout.fragment_plan_recipe_list_card, null);
            BlurLayout iv2 = (BlurLayout) view2.findViewById(R.id.sample);
            iv2.setBackgroundResource(LocalDemo.recipeBG[i]);
            TextView tv2 = (TextView) view2.findViewById(R.id.recipe_name);
            tv2.setText(LocalDemo.recipeName[i]);
            listViews2.add(view2);
        }
        for (int i = begin[2][nowState]; i < end[2][nowState]; i++) {
            View view3 = LayoutInflater.from(this.getActivity()).inflate(
                    R.layout.fragment_plan_recipe_list_card, null);
            BlurLayout iv3 = (BlurLayout) view3.findViewById(R.id.sample);
            iv3.setBackgroundResource(LocalDemo.recipeBG[i]);
            TextView tv3 = (TextView) view3.findViewById(R.id.recipe_name);
            tv3.setText(LocalDemo.recipeName[i]);
            listViews3.add(view3);
        }
    }

    private void getData() {
        for(int i=0;i< LocalDemo.nutritionName.length;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("item_name", LocalDemo.nutritionName[i]);
            map.put("item_weight", "20g");
            map.put("item_remark", "12%");
            nutrition_dataList.add(map);
        }
    }

    private void initEvent() {
        prev_btn.setOnClickListener(this);
        next_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prev_btn:{
                if (nowState!=0){
                    nowState--;
                }else {
                    nowState=4;
                }
                initMeal();
                rcFirstViewPagerAdapter.notifyDataSetChanged();
                rcSecondViewPagerAdapter.notifyDataSetChanged();
                rcThirdViewPagerAdapter.notifyDataSetChanged();
                break;
            }
            case R.id.next_btn:{
                if (nowState!=4){
                    nowState++;
                }else {
                    nowState=0;
                }
                initMeal();
                rcFirstViewPagerAdapter.notifyDataSetChanged();
                rcSecondViewPagerAdapter.notifyDataSetChanged();
                rcThirdViewPagerAdapter.notifyDataSetChanged();
                break;
            }
        }
    }
}
