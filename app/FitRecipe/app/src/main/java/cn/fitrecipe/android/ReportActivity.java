package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import cn.fitrecipe.android.UI.DietStructureView;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.function.Common;

/**
 * Created by 奕峰 on 2015/5/8.
 */

public class ReportActivity extends Activity implements View.OnClickListener{

    private Report report;
    private LinearLayout diet_container;
    private ImageView report_sex;
    private TextView report_age, report_height, report_weight, report_fat;
    private TextView report_bmi_number, report_base_intake_number, report_bmi_judgement, report_bmr_number, report_burning_number_min, report_burning_number_max, report_exercise_type;
    private TextView report_best_weight_number, report_best_weight_judgement, report_base_info_weight_range_min, report_base_info_weight_range_max;
    private TextView calories_intake, calories_intake_range, suggest_fit_calories, suggest_fit_calories_range, suggest_fit_frequency, suggest_fit_time;
    private TextView report_target_weight, report_target_time,report_target_weight_weekly;
    private PieChartView piechartview;
    private DietStructureView dietStructureView;

    private TextView water_min, water_max, protein_min, protein_max, fat_min, fat_max;
    private TextView carbohydrate_min, carbohydrate_max, fiber_min, fiber_max, sodium_min, sodium_max;
    private TextView unsaturatedFattyAcids_min, unsaturatedFattyAcids_max, cholesterol_min, cholesterol_max;
    private TextView vc_min, vc_max, vd_min, vd_max;

    private ImageView back_btn, restart_btn;
    private TextView check_plan_btn;
    private String last;

    private ImageView BMI_intro_btn, BMR_intro_btn, TDEE_intro_btn, heart_intro_btn;

    //view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_container);

        if(getIntent().hasExtra("report")) {
            report = (Report) getIntent().getSerializableExtra("report");
            last = "plan";
            FrApplication.getInstance().saveReport(report);
            FrApplication.getInstance().setIsTested(true);
        }
        else {
            last = "me";
            report = FrApplication.getInstance().getReport();
        }
        if(report != null) {
            initView();
            initEvent();
        }else {
            Toast.makeText(this, "先来做个测评吧!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, PlanTestActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initEvent() {
        check_plan_btn.setOnClickListener(this);
        restart_btn.setOnClickListener(this);
        BMI_intro_btn.setOnClickListener(this);
        BMR_intro_btn.setOnClickListener(this);
        TDEE_intro_btn.setOnClickListener(this);
        heart_intro_btn.setOnClickListener(this);
    }

    private void initView() {
        BMI_intro_btn = (ImageView) findViewById(R.id.BMI_intro_btn);
        BMR_intro_btn = (ImageView) findViewById(R.id.BMR_intro_btn);
        TDEE_intro_btn = (ImageView) findViewById(R.id.TDEE_intro_btn);
        heart_intro_btn = (ImageView) findViewById(R.id.heart_intro_btn);
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
        report_exercise_type = (TextView) findViewById(R.id.report_exercise_type);
        if(report.isGoalType() == 0)
            report_exercise_type.setText("增肌");
        else
            report_exercise_type.setText("减脂");
        report_fat = (TextView) findViewById(R.id.report_fat);
        report_fat.setText("体脂：" + report.getPreciseFat()*100+"%");


        report_bmi_number = (TextView) findViewById(R.id.report_bmi_number);
        report_bmi_number.setText((int)(report.getBMI()*10)/10.0+"");
        report_base_intake_number = (TextView) findViewById(R.id.report_base_intake_number);
        report_base_intake_number.setText(report.getTDEE()+"");

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

        report_target_weight = (TextView) findViewById(R.id.report_target_weight);
        report_target_weight.setText("目标体重：" + report.getWeightGoal()+"kg");

        report_target_time = (TextView) findViewById(R.id.report_target_time);
        report_target_time.setText("计划时间：" + report.getDaysToGoal()+"天");

        report_target_weight_weekly = (TextView) findViewById(R.id.report_target_weight_weekly);
        DecimalFormat df2 = new DecimalFormat("0.00");//这样为保持2位
        if(report.getWeight() < report.getWeightGoal()) {
            report_target_weight_weekly.setText("每周目标：+" + df2.format((double) (7 * (report.getWeightGoal() - report.getWeight()) / report.getDaysToGoal())) + "kg");
        }else
            report_target_weight_weekly.setText("每周目标：" + df2.format((double) (7 * (report.getWeightGoal() - report.getWeight()) / report.getDaysToGoal())) + "kg");




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
        double sum = report.getProteinIntake() * 4 + report.getFatIntake() * 9 + report.getCarbohydrateIntake() * 4;
        int a = (int) Math.round(report.getCarbohydrateIntake() * 100 * 4 / sum);
        int b = (int) Math.round(report.getProteinIntake() * 100 * 4 / sum);
        int c = 100 - a - b;
        piechartview.setValue(new float[]{a, b, c}, true, false, false);

        dietStructureView = (DietStructureView) findViewById(R.id.dietStructureView);
        dietStructureView.setValue(report);
        dietStructureView.setVisibility(View.GONE);
        diet_container = (LinearLayout) findViewById(R.id.diet_container);
        diet_container.setVisibility(View.GONE);

        water_min = (TextView) findViewById(R.id.water_min);
        water_min.setText(Math.round(report.getWaterIntakeMin() * 1000) + "g");
        water_max = (TextView) findViewById(R.id.water_max);
        water_max.setText(Math.round(report.getWaterIntakeMax() * 1000) + "g");

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
        sodium_min.setText(report.getSodiumIntakeMin() + "mg");
        sodium_max = (TextView) findViewById(R.id.sodium_max);
        sodium_max.setText(report.getSodiumIntakeMax()+"mg");


        unsaturatedFattyAcids_min = (TextView) findViewById(R.id.unsaturatedFattyAcids_min);
        unsaturatedFattyAcids_min.setText(Math.round(report.getUnsaturatedFattyAcidsIntakeMin()) + "g");
        unsaturatedFattyAcids_max = (TextView) findViewById(R.id.unsaturatedFattyAcids_max);
        unsaturatedFattyAcids_max.setText(Math.round(report.getUnsaturatedFattyAcidsIntakeMax())+"g");


        cholesterol_min = (TextView) findViewById(R.id.cholesterol_min);
        cholesterol_min.setText(Math.round(report.getCholesterolIntakeMin()) + "mg");
        cholesterol_max = (TextView) findViewById(R.id.cholesterol_max);
        cholesterol_max.setText(Math.round(report.getCholesterolIntakeMax())+"mg");

        vc_min = (TextView) findViewById(R.id.vc_min);
        vc_min.setText(Math.round(report.getVCIntakeMin()) + "mg");
        vc_max = (TextView) findViewById(R.id.vc_max);
        vc_max.setText(Math.round(report.getVCIntakeMax())+"mg");

        vd_min = (TextView) findViewById(R.id.vd_min);
        vd_min.setText(Math.round(report.getVDIntakeMin()) + "ug");
        vd_max = (TextView) findViewById(R.id.vd_max);
        vd_max.setText(Math.round(report.getVDIntakeMax())+"ug");

        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);


        check_plan_btn = (TextView) findViewById(R.id.check_plan);
        restart_btn = (ImageView) findViewById(R.id.restart_btn);
    }


    @Override
    public void onClick(View v) {
        SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        switch (v.getId()) {
            case R.id.back_btn:
                /*if(!last.equals("me")) {
                    editor.putBoolean("isSpecial", true);
                    editor.commit();
                    finish();
                }*/
                editor.putBoolean("isSpecial", true);
                editor.commit();
                finish();
                break;
            case R.id.check_plan:
                editor.putBoolean("isSpecial", true);
                editor.commit();
                finish();
                break;
            case R.id.restart_btn:
                Intent intent = new Intent(ReportActivity.this, PlanTestActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.BMI_intro_btn:
                Common.infoDialog(this, "BMI", "BMI指数（即身体质量指数)是目前国际上常用的衡量人体胖瘦程度以及是否健康的一个标准。通常分为5个等级，低于18.5属于过轻，18.5~24.99属于正常，25~28属于过重，28~32属于肥胖，高于32属于非常肥胖。另外需要注意的是，BMI指数并不适合增肌人士作为身体状况的参考。").show();
                break;
            case R.id.BMR_intro_btn:
                Common.infoDialog(this, "BMR", "基础代谢率（BMR）是指人体在清醒而又极端安静的状态下，不受肌肉活动、环境温度、食物及精神紧张等影响时的能量代谢率。简单来说，就是一个人即便一天躺在床上什么都不做都会消耗的热量。").show();
                break;
            case R.id.TDEE_intro_btn:
                Common.infoDialog(this, "TDEE", "每日热量总消耗（TDEE　Total Daily Energy Expenditure）是指根据工作强度或者运动频率估算得出的身体每日所需的热量，一般而言，增肌增重阶段，每日摄入热量可高于该数值；减脂减肥阶段，可低于该数值；维持体型阶段，可等于该数值。").show();
                break;
            case R.id.heart_intro_btn:
                Common.infoDialog(this, "燃脂心率", "即人体在进行燃脂运动时保持的心率状态。").show();
                break;
            default:
                break;
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
            if(!last.equals("me")) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isSpecial", true);
                editor.commit();
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
