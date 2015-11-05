package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.TestViewPagerAdapter;
import cn.fitrecipe.android.UI.TestViewPager;


/**
 * Created by 奕峰 on 2015/5/8.
 */
public class PlanTestActivity extends Activity implements View.OnClickListener {

    private ImageView back_btn;
    private TestViewPager testViewPager;
    private TestViewPagerAdapter testViewPagerAdapter;
    List<Map<String, Object>> testData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_test);

        initView();
        initData();
        initEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PlanTestActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PlanTestActivity");
        MobclickAgent.onResume(this);
    }

    public void goNext(){
        testViewPager.setCurrentItem(testViewPager.getCurrentItem()+1, true);
    }

    public void goPrev(){
        testViewPager.setCurrentItem(testViewPager.getCurrentItem()-1, true);
    }



    private void initData() {
        testData = new ArrayList<Map<String, Object>>();
        for(int i=0;i<13;i++){
            Map<String, Object> data = new HashMap<String, Object>();
            if(i==0){
                data.put("type","-1");
            }else if(i==1||i==5||i==6||i==7||i==8||i==9||i==10){
                data.put("type","0");
            }else{
                data.put("type","1");
            }
            data.put("data","-1");
            testData.add(data);
        }
        testViewPagerAdapter = new TestViewPagerAdapter(this, testData);
        testViewPager.setAdapter(testViewPagerAdapter);

    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        testViewPager = (TestViewPager) findViewById(R.id.test_viewpager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:
                if(testViewPager.getCurrentItem() == 0)
                    finish();
                else
                    goPrev();
                break;
            case R.id.plan_test_begin_btn:
                break;
            default:
                break;
        }

    }
}
