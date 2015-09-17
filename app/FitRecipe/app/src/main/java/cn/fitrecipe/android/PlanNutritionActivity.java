package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;

import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.entity.SeriesPlan;

public class PlanNutritionActivity extends Activity {

    private SeriesPlan plan;
    private PieChartView nutrition_chartview;
    private PieChartView dinner_chartview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_container_1);

        plan = (SeriesPlan) getIntent().getSerializableExtra("plan");

        initView();
    }

    private void initView() {
        nutrition_chartview = (PieChartView) findViewById(R.id.nutrition_chartview);
        int a = 20;
        int b = 35;
        int c = 100 - a - b;
        nutrition_chartview.setValue(new float[]{a, b, c}, true, false, true);
        dinner_chartview = (PieChartView) findViewById(R.id.dinner_chartview);
        dinner_chartview.setValue(new float[]{37, 23, 40}, true, true, true);
    }
}
