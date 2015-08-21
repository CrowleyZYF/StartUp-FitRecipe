package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
<<<<<<< HEAD
=======
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
>>>>>>> d10c1ecdb042e4bfd667e9f64c0cba2a73e78c75

import org.w3c.dom.Text;

import cn.fitrecipe.android.UI.DietStructureView;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.entity.Report;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class ReportActivity extends Activity {

    private Report report;
    private ImageView report_sex;
    private TextView report_age, report_height, report_weight, report_fat;
    private TextView report_bmi_number, report_bmi_judgement, report_bmr_number, report_burning_number_min, report_burning_number_max;
    private TextView report_best_weight_number, report_best_weight_judgement, report_base_info_weight_range_min, report_base_info_weight_range_max;
    private TextView calories_intake, calories_intake_range, suggest_fit_calories, suggest_fit_calories_range, suggest_fit_frequency, suggest_fit_time;
    private PieChartView piechartview;
    private DietStructureView dietStructureView;

    private TextView water_min, water_max, protein_min, protein_max, fat_min, fat_max;
    private TextView carbohydrate_min, carbohydrate_max, fiber_min, fiber_max, sodium_min, sodium_max;
    private TextView unsaturatedFattyAcids_min, unsaturatedFattyAcids_max, cholesterol_min, cholesterol_max;
    private TextView vc_min, vc_max, vd_min, vd_max;

    //view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_container);

        report = (Report) getIntent().getSerializableExtra("report");

        initView();
    }

    private void initView() {
        report_sex = (ImageView) findViewById(R.id.report_sex);
        if(report.getGender() == 0)
            report_sex.setImageResource(R.drawable.icon_male);
        else
            report_sex.setImageResource(R.drawable.icon_female);
        report_age = (TextView) findViewById(R.id.report_age);
        report_age.setText("年龄：" + report.getAge() + " 岁");
        report_height = (TextView) findViewById(R.id.report_height);
        report_height.setText("身高：" + report.getHeight() + " cm");
        report_weight = (TextView) findViewById(R.id.report_weight);
        report_weight.setText("体重：" + report.getWeight() + " kg");
        report_fat = (TextView) findViewById(R.id.report_fat);
        if(report.getRoughFat() == null)
            report_fat.setText("体脂：" + report.getPreciseFat()*100+"%");
        else
            report_fat.setText("体脂：" + report.getRoughFat());


        report_bmi_number = (TextView) findViewById(R.id.report_bmi_number);
        report_bmi_number.setText((int)(report.getBMI()*10)/10.0+"");

        report_bmi_judgement = (TextView) findViewById(R.id.report_bmi_judgement);
        String judge = "";
        if(report.getBMI() < 18.5)
            judge = "过轻";
        else
            if(report.getBMI() < 24.99)
                judge = "正常";
            else
                if(report.getBMI() < 28)
                    judge = "过重";
                else
                    if(report.getBMI() < 32)
                        judge = "肥胖";
                    else
                        judge = "非常肥胖";
        report_bmi_judgement.setText(judge);

        report_bmr_number = (TextView) findViewById(R.id.report_bmr_number);
        report_bmr_number.setText(Math.round(report.getBMR())+"");

        report_burning_number_min = (TextView) findViewById(R.id.report_burning_number_min);
        report_burning_number_min.setText(Math.round(report.getBurningRateMin())+"");

        report_burning_number_max = (TextView) findViewById(R.id.report_burning_number_max);
        report_burning_number_max.setText(Math.round(report.getBurningRateMax())+"");

        report_best_weight_number = (TextView) findViewById(R.id.report_best_weight_number);
        report_best_weight_number.setText(Math.round(report.getBestWeight())+"");

        report_best_weight_judgement = (TextView) findViewById(R.id.report_best_weight_judgement);
        double sub = Math.round(report.getBestWeight()) - report.getWeight();
        sub = (int)(sub * 10) /10.0;
        report_best_weight_judgement.setText((sub>0?"+"+sub:""+sub) +"kg");

        report_base_info_weight_range_min = (TextView) findViewById(R.id.report_base_info_weight_range_min);
        report_base_info_weight_range_min.setText(Math.round(report.getBestWeightMin())+"");

        report_base_info_weight_range_max = (TextView) findViewById(R.id.report_base_info_weight_range_max);
        report_base_info_weight_range_max.setText(Math.round(report.getBestWeightMax())+"");

        calories_intake = (TextView) findViewById(R.id.calories_intake);
        calories_intake.setText(Math.round(report.getCaloriesIntake())+"kcal");

        calories_intake_range = (TextView) findViewById(R.id.calories_intake_range);
        calories_intake_range.setText(Math.round(report.getCaloriesIntakeMin()) + "~" + Math.round(report.getCaloriesIntakeMax()) + "kcal");

        suggest_fit_calories = (TextView) findViewById(R.id.suggest_fit_calories);
        suggest_fit_calories.setText(Math.round(report.getSuggestFitCalories())+"kcal");

        suggest_fit_calories_range = (TextView) findViewById(R.id.suggest_fit_calories_range);
        suggest_fit_calories_range.setText(Math.round(report.getSuggestFitCaloriesMin()) + "~" + Math.round(report.getSuggestFitCaloriesMax()) + "kcal");

        suggest_fit_frequency = (TextView) findViewById(R.id.suggest_fit_frequency);
        suggest_fit_frequency.setText(Math.round(report.getSuggestFitFrequency())+"次");

        suggest_fit_time = (TextView) findViewById(R.id.suggest_fit_time);
        suggest_fit_time.setText(Math.round(report.getSuggestFitTime()) + "分钟");

        piechartview = (PieChartView) findViewById(R.id.piechartview);
        double sum = report.getProteinIntake() + report.getFatIntake() + report.getCarbohydrateIntake();
        int a = (int) Math.round(report.getCarbohydrateIntake() * 100 / sum);
        int b = (int) Math.round(report.getProteinIntake() * 100 / sum);
        int c = 100 - a - b;
        piechartview.setValue(new float[]{a, b, c});

        dietStructureView = (DietStructureView) findViewById(R.id.dietStructureView);
        dietStructureView.setValue(report);

        water_min = (TextView) findViewById(R.id.water_min);
        water_min.setText(Math.round(report.getWaterIntakeMin() * 1000) + "mL");
        water_max = (TextView) findViewById(R.id.water_max);
        water_max.setText(Math.round(report.getWaterIntakeMax() * 1000) + "mL");

        protein_min = (TextView) findViewById(R.id.protein_min);
        protein_min.setText(Math.round(report.getProteinIntakeMin()) + "g");
        protein_max = (TextView) findViewById(R.id.protein_max);
        protein_max.setText(Math.round(report.getProteinIntakeMax())+"g");

        fat_min = (TextView) findViewById(R.id.fat_min);
        fat_min.setText(Math.round(report.getFatIntakeMin()) + "g");
        fat_max = (TextView) findViewById(R.id.fat_max);
        fat_max.setText(Math.round(report.getFatIntakeMax())+"g");

        carbohydrate_min = (TextView) findViewById(R.id.carbohydrate_min);
        carbohydrate_min.setText(Math.round(report.getCarbohydrateIntakeMin()) + "g");
        carbohydrate_max = (TextView) findViewById(R.id.carbohydrate_max);
        carbohydrate_max.setText(Math.round(report.getCarbohydrateIntakeMax())+"g");

        fiber_min = (TextView) findViewById(R.id.fiber_min);
        fiber_min.setText(Math.round(report.getFiberIntakeMin()) + "g");
        fiber_max = (TextView) findViewById(R.id.fiber_max);
        fiber_max.setText(Math.round(report.getFiberIntakeMax())+"g");

        sodium_min = (TextView) findViewById(R.id.sodium_min);
        sodium_min.setText(report.getSodiumIntakeMin() + "g");
        sodium_max = (TextView) findViewById(R.id.sodium_max);
        sodium_max.setText(report.getSodiumIntakeMax()+"g");


        unsaturatedFattyAcids_min = (TextView) findViewById(R.id.unsaturatedFattyAcids_min);
        unsaturatedFattyAcids_min.setText(Math.round(report.getUnsaturatedFattyAcidsIntakeMin()) + "mg");
        unsaturatedFattyAcids_max = (TextView) findViewById(R.id.unsaturatedFattyAcids_max);
        unsaturatedFattyAcids_max.setText(Math.round(report.getUnsaturatedFattyAcidsIntakeMax())+"mg");


        cholesterol_min = (TextView) findViewById(R.id.cholesterol_min);
        cholesterol_min.setText(Math.round(report.getCholesterolIntakeMin()) + "mg");
        cholesterol_max = (TextView) findViewById(R.id.cholesterol_max);
        cholesterol_max.setText(Math.round(report.getCholesterolIntakeMax())+"mg");

        vc_min = (TextView) findViewById(R.id.vc_min);
        vc_min.setText(Math.round(report.getVCIntakeMin()) + "g");
        vc_max = (TextView) findViewById(R.id.vc_max);
        vc_max.setText(Math.round(report.getVCIntakeMax())+"g");

        vd_min = (TextView) findViewById(R.id.vd_min);
        vd_min.setText(Math.round(report.getVDIntakeMin()) + "g");
        vd_max = (TextView) findViewById(R.id.vd_max);
        vd_max.setText(Math.round(report.getVDIntakeMax())+"g");
    }

    private double round(double x) {
        int a = (int)(x * 10);
        if(a % 10 == 0) return a/10;
        else return a / 10.0;
    }
}
