package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
                //finish();
                goNext();
                break;
            case R.id.plan_test_begin_btn:
                break;
            default:
                break;
        }

    }
}
