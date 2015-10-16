package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.entity.SeriesPlan;

public class PlanNutritionActivity extends Activity {

    private Report report;

    private SeriesPlan plan;
    private String plan_day_text;
    private int now_day;
    private PieChartView nutrition_chartview;
    private PieChartView dinner_chartview;

    private TextView plan_day, plan_all_calorie, user_need_calorie, plan_calorie_radio;
    private TextView protein_intake, carbohydrate_intake, lipids_intake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_container_1);

        plan = (SeriesPlan) getIntent().getSerializableExtra("plan");
        plan_day_text = getIntent().getStringExtra("plan_day");
        now_day = Integer.parseInt(plan_day_text.substring(0,plan_day_text.indexOf("/")));

        initView();
        initDate();
        initEvent();
    }

    private void initEvent() {

    }

    private void initDate() {
        report = FrApplication.getInstance().getReport();
        plan_day.setText("第 " + plan_day_text + " 天");
        plan_all_calorie.setText(Math.round(plan.getDatePlans().get(now_day-1).getTotalCalories())+"kcal");
        user_need_calorie.setText("(" + report.getCaloriesIntake() + "kcal)");
        plan_calorie_radio.setText(Math.round(plan.getDatePlans().get(now_day-1).getTotalCalories()/report.getCaloriesIntake()*100)+"%");

        nutrition_chartview = (PieChartView) findViewById(R.id.nutrition_chartview);
        List<DatePlanItem> dataPlanItems =  plan.getDatePlans().get(now_day-1).getItems();//一日五餐
        DatePlanItem wholeDateItem = DatePlanItem.getAllItem(dataPlanItems);
        ArrayList<Nutrition> nutritions = wholeDateItem.getNutritions();
        double proteinWeight = nutritions.get(1).getAmount();//蛋白质
        double lipidsWeight = nutritions.get(2).getAmount();//脂类
        double carbohydrateWeight = nutritions.get(3).getAmount();//碳水
        float proteinCalorieRate = Math.round(100* ((float)proteinWeight * 4) / Math.round(plan.getDatePlans().get(now_day-1).getTotalCalories()));
        float lipidsCalorieRate = Math.round(100* ((float)lipidsWeight * 9) / Math.round(plan.getDatePlans().get(now_day-1).getTotalCalories()));
        nutrition_chartview.setValue(new float[]{100 -proteinCalorieRate-lipidsCalorieRate, proteinCalorieRate,lipidsCalorieRate},true, false, true);
        protein_intake.setText(Math.round(proteinWeight) + " g");
        lipids_intake.setText(Math.round(lipidsWeight) + " g");
        carbohydrate_intake.setText(Math.round(carbohydrateWeight) + " g");

        float[] mealCalorieRate = {0, 0, 0};
        String[] mealType = {"breakfast", "add_meal_01", "lunch", "add_meal_02", "supper", "add_meal_03"};
        for(int i = 0; i < dataPlanItems.size(); i++){
            for(int j = 0; j < 3; j++){
                if(dataPlanItems.get(i).getType().equals(mealType[j*2])||dataPlanItems.get(i).getType().equals(mealType[j*2+1])){
                    mealCalorieRate[j] += dataPlanItems.get(i).getCalories_take();
                    j=3;
                }
            }
        }
        for (int i = 0;i < mealCalorieRate.length; i++){
            if(i==(mealCalorieRate.length-1)){
                mealCalorieRate[i] = 100 - mealCalorieRate[0] - mealCalorieRate[1];
            }else {
                mealCalorieRate[i] = (float) Math.round(100 * mealCalorieRate[i] / plan.getDatePlans().get(now_day-1).getTotalCalories());
            }
        }
        dinner_chartview = (PieChartView) findViewById(R.id.dinner_chartview);
        dinner_chartview.setValue(mealCalorieRate, true, true, true);

    }

    private void initView() {
        plan_day = (TextView) findViewById(R.id.plan_day);
        plan_all_calorie = (TextView) findViewById(R.id.plan_all_calorie);
        user_need_calorie = (TextView) findViewById(R.id.user_need_calorie);
        plan_calorie_radio = (TextView) findViewById(R.id.plan_calorie_radio);

        protein_intake = (TextView) findViewById(R.id.protein_intake);
        carbohydrate_intake = (TextView) findViewById(R.id.carbohydrate_intake);
        lipids_intake = (TextView) findViewById(R.id.lipids_intake);

        nutrition_chartview = (PieChartView) findViewById(R.id.nutrition_chartview);
        int a = 20;
        int b = 35;
        int c = 100 - a - b;
        nutrition_chartview.setValue(new float[]{a, b, c}, true, false, true);
        dinner_chartview = (PieChartView) findViewById(R.id.dinner_chartview);
        dinner_chartview.setValue(new float[]{37, 23, 40}, true, true, true);
    }
}
