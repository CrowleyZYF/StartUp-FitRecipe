package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.ProcedurePagerAdapter;

public class RecipeProcedureActivity extends Activity{

    private ViewPager procedureViewPager;
    private ProcedurePagerAdapter procedureViewPagerAdapter;
    private List<View> listViews1 = null;

    private int[] imgs = { R.drawable.ztest001, R.drawable.ztest002, R.drawable.ztest003,
            R.drawable.ztest004, R.drawable.ztest005, R.drawable.ztest006};

    private String[] texts = {
            "鸡蛋煮熟后，切成条状待用",
            "紫甘蓝切丝后，用沸水滚30秒软化捞出沥干",
            "生菜，洋葱，洗干净切丝待用",
            "用一个大碗，将紫甘蓝，鸡蛋，洋葱少许，金枪鱼肉，淋上番茄酱，芝麻酱，橄榄油，混合搅拌",
            "菠菜卷饼用平底锅加热1分钟，放入生菜垫底，将搅拌后的食材倒入卷饼",
            "裹上卷饼后，即可食用"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_procedure_container);

        initView();
        initData();
        initEvent();

    }

    private void initEvent() {

    }

    private void initData() {
        listViews1 = new ArrayList<View>();
        for (int i = 0; i < imgs.length; i++) {
            View view1 = LayoutInflater.from(this).inflate(
                    R.layout.activity_recipe_procedure_item, null);
            ImageView pic = (ImageView) view1.findViewById(R.id.recipe_procedure_pic);
            pic.setImageResource(imgs[i]);
            TextView number = (TextView) view1.findViewById(R.id.recipe_procedure_number);
            number.setText((i+1)+"");
            TextView count = (TextView) view1.findViewById(R.id.recipe_procedure_count);
            count.setText("/"+imgs.length);
            TextView text = (TextView) view1.findViewById(R.id.recipe_procedure_text);
            text.setText(texts[i]);
            listViews1.add(view1);
        }
        procedureViewPagerAdapter = new ProcedurePagerAdapter(listViews1,this);
        procedureViewPager.setAdapter(procedureViewPagerAdapter);
    }

    private void initView() {
        procedureViewPager = (ViewPager) findViewById(R.id.procedure_viewpager);
    }

}
