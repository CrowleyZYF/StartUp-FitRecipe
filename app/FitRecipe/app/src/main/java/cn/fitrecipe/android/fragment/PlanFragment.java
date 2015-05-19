package cn.fitrecipe.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidviewhover.BlurLayout;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.SlidingPage;
import cn.fitrecipe.android.UI.rcPlanViewPagerAdapter;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class PlanFragment extends Fragment
{

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
            R.drawable.ztest004, R.drawable.ztest005, R.drawable.ztest006, };

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
    }

    private void initData() {
        listViews1 = new ArrayList<View>();
        listViews2 = new ArrayList<View>();
        listViews3 = new ArrayList<View>();
        for (int i = 0; i < imgs.length; i++) {
            View view1 = LayoutInflater.from(this.getActivity()).inflate(
                    R.layout.fragment_plan_recipe_list_card, null);
            BlurLayout iv1 = (BlurLayout) view1.findViewById(R.id.sample);
            iv1.setBackgroundResource(imgs[i]);
            listViews1.add(view1);

            View view2 = LayoutInflater.from(this.getActivity()).inflate(
                    R.layout.fragment_plan_recipe_list_card, null);
            BlurLayout iv2 = (BlurLayout) view2.findViewById(R.id.sample);
            iv2.setBackgroundResource(imgs[i]);
            listViews2.add(view2);

            View view3 = LayoutInflater.from(this.getActivity()).inflate(
                    R.layout.fragment_plan_recipe_list_card, null);
            BlurLayout iv3 = (BlurLayout) view3.findViewById(R.id.sample);
            iv3.setBackgroundResource(imgs[i]);
            listViews3.add(view3);
        }
        rcFirstViewPagerAdapter = new rcPlanViewPagerAdapter(listViews1,this.getActivity());
        frFirstViewPager.setAdapter(rcFirstViewPagerAdapter);
        rcSecondViewPagerAdapter = new rcPlanViewPagerAdapter(listViews2,this.getActivity());
        frSecondViewPager.setAdapter(rcSecondViewPagerAdapter);
        rcThirdViewPagerAdapter = new rcPlanViewPagerAdapter(listViews3,this.getActivity());
        frThirdViewPager.setAdapter(rcThirdViewPagerAdapter);
    }

    private void initEvent() {

    }
}
