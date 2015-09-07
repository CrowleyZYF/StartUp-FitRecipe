package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;

import cn.fitrecipe.android.entity.SeriesPlan;

public class PlanNutritionActivity extends Activity {

    private SeriesPlan plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_plan_nutrition);

        plan = (SeriesPlan) getIntent().getSerializableExtra("plan");
    }
}
