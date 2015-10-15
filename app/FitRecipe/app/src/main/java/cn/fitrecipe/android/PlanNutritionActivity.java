package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.entity.SeriesPlan;

public class PlanNutritionActivity extends Activity {

    private SeriesPlan plan;
    private String plan_day_text;
    private int now_day;
    private PieChartView nutrition_chartview;
    private PieChartView dinner_chartview;

    private TextView plan_day, plan_all_calorie, user_need_calorie, plan_calorie_radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_container_1);

        plan = (SeriesPlan) getIntent().getSerializableExtra("plan");
        plan_day_text = getIntent().getStringExtra("plan_day_text");
        now_day = Integer.parseInt(plan_day_text.substring(0,plan_day_text.indexOf("/")));

        initView();
        initDate();
        initEvent();
    }

    private void initEvent() {

    }

    private void initDate() {
        plan_day.setText(plan_day_text);

    }

    private void initView() {
        plan_day = (TextView) findViewById(R.id.plan_day);
        plan_all_calorie = (TextView) findViewById(R.id.plan_all_calorie);
        user_need_calorie = (TextView) findViewById(R.id.user_need_calorie);
        plan_calorie_radio = (TextView) findViewById(R.id.plan_calorie_radio);

        nutrition_chartview = (PieChartView) findViewById(R.id.nutrition_chartview);
        int a = 20;
        int b = 35;
        int c = 100 - a - b;
        nutrition_chartview.setValue(new float[]{a, b, c}, true, false, true);
        dinner_chartview = (PieChartView) findViewById(R.id.dinner_chartview);
        dinner_chartview.setValue(new float[]{37, 23, 40}, true, true, true);
    }
}
