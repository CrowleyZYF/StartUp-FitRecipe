package cn.fitrecipe.android.Adpater;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.PlanTestActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.ReportActivity;
import cn.fitrecipe.android.function.Evaluation;

/**
 * Created by 奕峰 on 2015/4/25.
 */
public class TestViewPagerAdapter extends PagerAdapter implements View.OnClickListener {
    private PlanTestActivity context;
    private List<Map<String, Object>> dataList;
    private List<View> questionLinearLayout = new ArrayList<View>();
    private Evaluation userEvaluation;
    private double lose_weight_max;
    private double gain_muslce_max;
    private int gain_goal_time;
    private final int AGE=2;
    private final int AGE_MIN=18;
    private final int AGE_MAX=80;
    private final int HEIGHT=3;
    private final int HEIGHT_MIN=40;
    private final int HEIGHT_MAX=300;
    private final int WEIGHT=4;
    private final int WEIGHT_MIN=40;
    private final int WEIGHT_MAX=300;
    private final int FAT=5;
    private final int FAT_MIN=0;
    private final int FAT_MAX=100;
    private final String[][] input={{},{},{},{},{},
            {"小于12%","12%~20%","20%~30%","30%","20%以下","20%~30%","30~40%","40%以上"},
            {"轻劳动","中等劳动","重劳动","极重劳动"},
            {"增肌增重","减脂减肥"},
            {"1-2天","3-4天","5-6天","7天"},
            {"小于30分钟","30-60分钟","60-90分钟","90分钟以上"},
            {"早餐前","午餐前","晚餐前","晚餐后"},
            {},{}};
    private final String[][] output = {
            //BaseInfo
            {"BMI","身体质量指数（BMI）"},
            {"BMR","基础代谢率（BMR）"},
            {"SuggestFitFrequency","建议每周运动频率"},
            {"SuggestFitTime","建议每次运动时长"},
            {"CaloriesIntake","每日所需热量"},
            {"CaloriesIntakeMin","每日所需热量下限"},
            {"CaloriesIntakeMax","每日所需热量上限"},
            {"SuggestFitCalories","每日运动量"},
            {"SuggestFitCaloriesMin","每日运动量下限"},
            {"SuggestFitCaloriesMax","每日运动量上限"},
            {"BestWeight","最佳体重"},
            {"BestWeightMin","健康体重下限"},
            {"BestWeightMax","健康体重上限"},
            {"BurningRateMin","燃脂心率下限"},
            {"BurningRateMax","燃脂心率上限"},
            //NutritionInfo
            {"ProteinIntakeMin","蛋白质摄入量下限"},
            {"ProteinIntakeMax","蛋白质摄入量上限"},
            {"CarbohydrateIntakeMin","碳水化合物摄入量下限"},
            {"CarbohydrateIntakeMax","碳水化合物摄入量上限"},
            {"FatIntakeMin","脂类摄入量下限"},
            {"FatIntakeMax","脂类摄入量上限"},
            {"WaterIntakeMin","水摄入量下限"},
            {"WaterIntakeMax","水摄入量上限"},
            {"FiberIntakeMin","纤维素摄入量下限"},
            {"FiberIntakeMax","纤维素摄入量上限"},
            {"UnsaturatedFattyAcidsIntakeMin","不饱和脂肪酸摄入量下限"},
            {"UnsaturatedFattyAcidsIntakeMax","不饱和脂肪酸摄入量上限"},
            {"CholesterolIntakeMin","胆固醇摄入量下限"},
            {"CholesterolIntakeMax","胆固醇摄入量上限"},
            {"SodiumIntakeMin","钠摄入量下限"},
            {"SodiumIntakeMax","钠摄入量上限"},
            {"VCIntakeMin","维生素C摄入量下限"},
            {"VCIntakeMax","维生素C摄入量上限"},
            {"VDIntakeMin","维生素D摄入量下限"},
            {"VDIntakeMax","维生素D摄入量上限"},
            //DietStructure
            {"DietStructureOilMin", "油脂下限"},
            {"DietStructureOilMax", "油脂上限"},
            {"DietStructureMeatMin", "肉类下限"},
            {"DietStructureMeatMax", "肉类上限"},
            {"DietStructureMilkMin", "蛋奶下限"},
            {"DietStructureMilkMax", "蛋奶上限"},
            {"DietStructureVegetableMin", "蔬菜下限"},
            {"DietStructureVegetableMax", "蔬菜上限"},
            {"DietStructureFruitMin", "水果下限"},
            {"DietStructureFruitMax", "水果上限"},
            {"DietStructureGrainMin", "谷物下限"},
            {"DietStructureGrainMax", "谷物上限"},
            //OtherInfo
            {"BreakfastRate","早餐摄入"},
            {"SnackMorningRate","上午加餐摄入"},
            {"LunchRate","午餐摄入"},
            {"SnackAfternoonRate","下午加餐摄入"},
            {"DinnerRate","晚餐摄入"}
    };

    public TestViewPagerAdapter(PlanTestActivity context, List<Map<String, Object>> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View questionContainer = null;
        switch (position) {
            case 0:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_00, null);
                LinearLayout begin = (LinearLayout) questionContainer.findViewById(R.id.plan_test_begin_btn);
                begin.setOnClickListener(this);
                break;
            case 1:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_01, null);
                initRadio(questionContainer);
                break;
            case 2:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_02, null);
                initCal(questionContainer);
                break;
            case 3:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_03, null);
                initCal(questionContainer);
                break;
            case 4:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_04, null);
                initCal(questionContainer);
                break;
            case 5:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_05, null);
                final View q5 = questionContainer;
                initRadio(questionContainer);
                initCal(questionContainer);
                TextView switchBtn_01 = (TextView) questionContainer.findViewById(R.id.plan_test_switch_01);
                switchBtn_01.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataList.get(5).put("type",1);
                        RelativeLayout test_05_01_up = (RelativeLayout) q5.findViewById(R.id.test_05_01_up);
                        RelativeLayout test_05_02_up = (RelativeLayout) q5.findViewById(R.id.test_05_02_up);
                        LinearLayout test_05_01_down = (LinearLayout) q5.findViewById(R.id.test_05_01_down);
                        LinearLayout test_05_02_down = (LinearLayout) q5.findViewById(R.id.radioGroup);
                        test_05_01_up.setVisibility(View.VISIBLE);
                        test_05_02_up.setVisibility(View.GONE);
                        test_05_01_down.setVisibility(View.VISIBLE);
                        test_05_02_down.setVisibility(View.GONE);
                    }
                });
                TextView switchBtn_02 = (TextView) questionContainer.findViewById(R.id.plan_test_switch);
                switchBtn_02.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataList.get(5).put("type",0);
                        RelativeLayout test_05_01_up = (RelativeLayout) q5.findViewById(R.id.test_05_01_up);
                        RelativeLayout test_05_02_up = (RelativeLayout) q5.findViewById(R.id.test_05_02_up);
                        LinearLayout test_05_01_down = (LinearLayout) q5.findViewById(R.id.test_05_01_down);
                        LinearLayout test_05_02_down = (LinearLayout) q5.findViewById(R.id.radioGroup);
                        test_05_01_up.setVisibility(View.GONE);
                        test_05_02_up.setVisibility(View.VISIBLE);
                        test_05_01_down.setVisibility(View.GONE);
                        test_05_02_down.setVisibility(View.VISIBLE);
                    }
                });
                break;
            case 6:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_06, null);
                initRadio(questionContainer);
                break;
            case 7:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_07, null);
                initRadio(questionContainer);
                break;
            case 8:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_08, null);
                initRadio(questionContainer);
                break;
            case 9:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_09, null);
                initRadio(questionContainer);
                break;
            case 10:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_10, null);
                initRadio(questionContainer);
                break;
            case 11:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_11, null);
                initCal(questionContainer);
                TextView tips = (TextView) questionContainer.findViewById(R.id.plan_tips);
                if(userEvaluation.getGoalType()){
                    tips.setText(context.getResources().getString(R.string.plan_test_question_11_tips_gain_muscle) + this.gain_muslce_max + "公斤");
                }else{
                    tips.setText(context.getResources().getString(R.string.plan_test_question_11_tips_lose_weight) + this.lose_weight_max + "公斤");
                }
                break;
            case 12:
                questionContainer = LayoutInflater.from(context).inflate(R.layout.fragment_plan_test_12, null);
                initCal(questionContainer);
                TextView tips2 = (TextView) questionContainer.findViewById(R.id.plan_tips);
                tips2.setText(context.getResources().getString(R.string.plan_test_question_12_tips_prefix) + this.gain_goal_time + context.getResources().getString(R.string.plan_test_question_12_tips_suffix));
                break;
        }
        container.addView(questionContainer);
        questionLinearLayout.add(questionContainer);
        return questionContainer;
    }

    private void initRadio(final View questionContainer) {
        LinearLayout radioGroup = (LinearLayout) questionContainer.findViewById(R.id.radioGroup);
        final int position = Integer.parseInt(((TextView) questionContainer.findViewById(R.id.position)).getText().toString());
        //获取所有radio
        List<RadioButton> radioButtons = new ArrayList<RadioButton>();
        for(int i=0;i<radioGroup.getChildCount();i++){
            RadioButton radio = (RadioButton) radioGroup.getChildAt(i);
            radioButtons.add(radio);
            i++;
        }
        //如果data为-1,则代表没有选择过，将所有radio设置为false;否则，将某个radio设置为true
        if(Integer.parseInt(dataList.get(position).get("data").toString())==-1){
            for (int i = 0;i<radioButtons.size();i++){
                radioButtons.get(i).setChecked(false);
            }
        }else{
            resetChoice(radioGroup,radioButtons.get(Integer.parseInt(dataList.get(position).get("data").toString())));
        }
        //创建监听函数
        View.OnClickListener choiceHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout radioGroup = (LinearLayout) questionContainer.findViewById(R.id.radioGroup);
                int position = Integer.parseInt(((TextView) questionContainer.findViewById(R.id.position)).getText().toString());
                for(int i=0;i<radioGroup.getChildCount();i++){
                    RadioButton resetBtn = (RadioButton) radioGroup.getChildAt(i);
                    resetBtn.setChecked(false);
                    i++;
                    if(v == resetBtn) {
                        resetBtn.setChecked(true);
                        dataList.get(position).put("data",i/2);
                    }
                }
                //如果第九个问题 可以计算相关的数值
                if(position==9){
                    int roughFat;
                    double preciseFat;
                    boolean goalType;
                    if(Integer.parseInt(dataList.get(5).get("type").toString())==0){
                        roughFat = Integer.parseInt(dataList.get(5).get("data").toString());
                        preciseFat = 0;
                    }else{
                        roughFat = 4;
                        preciseFat = Double.parseDouble(dataList.get(5).get("data").toString())/100;
                    }
                    if(Integer.parseInt(dataList.get(7).get("data").toString())==0){
                        goalType = true;
                    }else{
                        goalType = false;
                    }
                    userEvaluation = new Evaluation(
                            Integer.parseInt(dataList.get(1).get("data").toString()),
                            Integer.parseInt(dataList.get(2).get("data").toString()),
                            Integer.parseInt(dataList.get(3).get("data").toString()),
                            Double.parseDouble(dataList.get(4).get("data").toString()),
                            roughFat,
                            preciseFat,
                            Integer.parseInt(dataList.get(6).get("data").toString()),
                            goalType,
                            Integer.parseInt(dataList.get(8).get("data").toString()),
                            Integer.parseInt(dataList.get(9).get("data").toString())
                    );
                    lose_weight_max = userEvaluation.getWeightBoundary()[0];
                    gain_muslce_max = userEvaluation.getWeightBoundary()[1];
                }
                if (position==10){
                    userEvaluation.setExerciseTime(Integer.parseInt(dataList.get(10).get("data").toString()));
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        context.goNext();
                    }
                }, 600);
            }
        };
        //设置监听事件
        for (int i = 0;i<radioButtons.size();i++){
            radioButtons.get(i).setOnClickListener(choiceHandler);
        }
        if(position!=1){
            TextView prev = (TextView) questionContainer.findViewById(R.id.plan_test_last_question);
            prev.setOnClickListener(this);
        }
        if(position==5){
            TextView prev = (TextView) questionContainer.findViewById(R.id.plan_test_last_question_01);
            prev.setOnClickListener(this);
        }
    }

    private void initCal(final View questionContainer) {
        TextView prev = (TextView) questionContainer.findViewById(R.id.plan_test_last_question);
        ImageView delete = (ImageView) questionContainer.findViewById(R.id.delete_btn);
        TextView plan_num_00 = (TextView) questionContainer.findViewById(R.id.plan_num_00);
        TextView plan_num_01 = (TextView) questionContainer.findViewById(R.id.plan_num_01);
        TextView plan_num_02 = (TextView) questionContainer.findViewById(R.id.plan_num_02);
        TextView plan_num_03 = (TextView) questionContainer.findViewById(R.id.plan_num_03);
        TextView plan_num_04 = (TextView) questionContainer.findViewById(R.id.plan_num_04);
        TextView plan_num_05 = (TextView) questionContainer.findViewById(R.id.plan_num_05);
        TextView plan_num_06 = (TextView) questionContainer.findViewById(R.id.plan_num_06);
        TextView plan_num_07 = (TextView) questionContainer.findViewById(R.id.plan_num_07);
        TextView plan_num_08 = (TextView) questionContainer.findViewById(R.id.plan_num_08);
        TextView plan_num_09 = (TextView) questionContainer.findViewById(R.id.plan_num_09);
        TextView plan_num_dash = (TextView) questionContainer.findViewById(R.id.plan_num_dash);
        TextView plan_num_sure = (TextView) questionContainer.findViewById(R.id.plan_num_sure);
        View.OnClickListener calHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView value = (TextView) questionContainer.findViewById(R.id.plan_test_question_value);
                String num = value.getText().toString();
                int position = Integer.parseInt(((TextView) questionContainer.findViewById(R.id.position)).getText().toString());
                int isInt = Integer.parseInt(((TextView) questionContainer.findViewById(R.id.isInt)).getText().toString());
                switch (v.getId()){
                    case R.id.plan_num_00:
                        value.setText(getNewValue(num, "0", isInt));
                        break;
                    case R.id.plan_num_01:
                        value.setText(getNewValue(num, "1", isInt));
                        break;
                    case R.id.plan_num_02:
                        value.setText(getNewValue(num, "2", isInt));
                        break;
                    case R.id.plan_num_03:
                        value.setText(getNewValue(num, "3", isInt));
                        break;
                    case R.id.plan_num_04:
                        value.setText(getNewValue(num, "4", isInt));
                        break;
                    case R.id.plan_num_05:
                        value.setText(getNewValue(num, "5", isInt));
                        break;
                    case R.id.plan_num_06:
                        value.setText(getNewValue(num, "6", isInt));
                        break;
                    case R.id.plan_num_07:
                        value.setText(getNewValue(num, "7", isInt));
                        break;
                    case R.id.plan_num_08:
                        value.setText(getNewValue(num, "8", isInt));
                        break;
                    case R.id.plan_num_09:
                        value.setText(getNewValue(num, "9", isInt));
                        break;
                    case R.id.plan_num_dash:
                        value.setText(getNewValue(num, ".", isInt));
                        break;
                    case R.id.plan_num_sure:
                        if(checkVaild(Double.parseDouble(value.getText().toString()), position)){
                            dataList.get(position).put("data",value.getText().toString());
                            if(position==11){
                                userEvaluation.setWeightGoal(Integer.parseInt(dataList.get(11).get("data").toString()));
                                TextView tips2 = (TextView) (questionLinearLayout.get(12)).findViewById(R.id.plan_tips);
                                tips2.setText(context.getResources().getString(R.string.plan_test_question_12_tips_prefix) + userEvaluation.getShortestDaysToGoal() + context.getResources().getString(R.string.plan_test_question_12_tips_suffix));
                            }
                            if(position==12){
                                userEvaluation.setDaysToGoal(Integer.parseInt(dataList.get(12).get("data").toString()));
                                getAllData();
                            }else{
                                context.goNext();
                            }
                        }
                        break;
                    case R.id.delete_btn:
                        if(value.length()==1){
                            value.setText("0");
                        }else{
                            value.setText((value.getText().toString()).substring(0,value.length()-1));
                        }
                        break;
                }
            }
        };
        plan_num_00.setOnClickListener(calHandler);
        plan_num_01.setOnClickListener(calHandler);
        plan_num_02.setOnClickListener(calHandler);
        plan_num_03.setOnClickListener(calHandler);
        plan_num_04.setOnClickListener(calHandler);
        plan_num_05.setOnClickListener(calHandler);
        plan_num_06.setOnClickListener(calHandler);
        plan_num_07.setOnClickListener(calHandler);
        plan_num_08.setOnClickListener(calHandler);
        plan_num_09.setOnClickListener(calHandler);
        plan_num_dash.setOnClickListener(calHandler);
        plan_num_sure.setOnClickListener(calHandler);
        delete.setOnClickListener(calHandler);
        prev.setOnClickListener(this);
    }

    private void getAllData() {
        /*String input = "性别：" + (Integer.parseInt(dataList.get(1).get("data").toString())==0?"男":"女") + "\n"
                + "年龄：" + (Integer.parseInt(dataList.get(2).get("data").toString())) + "\n"
                + "身高：" + (Double.parseDouble(dataList.get(3).get("data").toString())) + "\n"
                + "体重：" + (Double.parseDouble(dataList.get(4).get("data").toString())) + "\n"
                + "体脂：" + (Integer.parseInt(dataList.get(5).get("type").toString())==0?
                (this.input[5][Integer.parseInt(dataList.get(5).get("data").toString())+Integer.parseInt(dataList.get(1).get("data").toString())]):
                (Integer.parseInt(dataList.get(5).get("data").toString()))) + "\n"
                + "劳动等级：" + (this.input[6][Integer.parseInt(dataList.get(6).get("data").toString())]) + "\n"
                + "运动类型：" + (this.input[7][Integer.parseInt(dataList.get(7).get("data").toString())]) + "\n"
                + "一周运动几天：" + (this.input[8][Integer.parseInt(dataList.get(8).get("data").toString())]) + "\n"
                + "每次运动的时长：" + (this.input[9][Integer.parseInt(dataList.get(9).get("data").toString())]) + "\n"
                + "运动时间段：" + (this.input[10][Integer.parseInt(dataList.get(10).get("data").toString())]) + "\n"
                + "目标：" + (Double.parseDouble(dataList.get(11).get("data").toString())) + "\n"
                + "时间：" + (Double.parseDouble(dataList.get(12).get("data").toString())) + "\n"
        ;
        Map<String, Object> report = userEvaluation.report();
        String output = "";
        for(int i=0;i<this.output.length;i++){
            output += this.output[i][1] + ": " + report.get(this.output[i][0]) + "\n";
        }*/
        Map<String, Object> report = userEvaluation.report();
        Intent intent=new Intent(context,ReportActivity.class);
        intent.putExtra("sex", Integer.parseInt(dataList.get(1).get("data").toString()));
        intent.putExtra("age", Integer.parseInt(dataList.get(2).get("data").toString()));
        intent.putExtra("height", (Double.parseDouble(dataList.get(3).get("data").toString())));
        intent.putExtra("weight", (Double.parseDouble(dataList.get(4).get("data").toString())));
        intent.putExtra("fat", (Integer.parseInt(dataList.get(5).get("type").toString())==0?
                (this.input[5][Integer.parseInt(dataList.get(5).get("data").toString())+Integer.parseInt(dataList.get(1).get("data").toString())]):
                (Integer.parseInt(dataList.get(5).get("data").toString()))));
        for(int i=0;i<this.output.length;i++){
            intent.putExtra(this.output[i][0], report.get(this.output[i][0]).toString());
        }
        context.startActivity(intent);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(questionLinearLayout.get(position));
    }


    @Override
    public int getCount() {
        return 13;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.plan_test_last_question:
                context.goPrev();
                break;
            case R.id.plan_test_begin_btn:
                context.goNext();
                break;
            default:
                break;
        }

    }

    public void resetChoice(LinearLayout radioGroup, View v){
        for(int i=0;i<radioGroup.getChildCount();i++){
            RadioButton resetBtn = (RadioButton) radioGroup.getChildAt(i);
            resetBtn.setChecked(false);
            i++;
        }
        ((RadioButton) v).setChecked(true);
    }

    private boolean checkVaild(double value, int position){
        boolean result = false;
        switch(position){
            case AGE:
                if (value<AGE_MIN){
                    Toast.makeText(context,"年龄不要小于"+AGE_MIN+"岁",Toast.LENGTH_SHORT).show();
                } else if(value>AGE_MAX){
                    Toast.makeText(context,"年龄不要大于"+AGE_MAX+"岁",Toast.LENGTH_SHORT).show();
                }else{
                    result = true;
                }
                break;
            case HEIGHT:
                if (value<HEIGHT_MIN){
                    Toast.makeText(context,"身高不要低于"+HEIGHT_MIN+"厘米",Toast.LENGTH_SHORT).show();
                } else if(value>HEIGHT_MAX){
                    Toast.makeText(context,"身高不要高于"+HEIGHT_MAX+"厘米",Toast.LENGTH_SHORT).show();
                }else{
                    result = true;
                }
                break;
            case WEIGHT:
                if (value<WEIGHT_MIN){
                    Toast.makeText(context,"体重不要低于"+WEIGHT_MIN+"千克",Toast.LENGTH_SHORT).show();
                } else if(value>WEIGHT_MAX){
                    Toast.makeText(context,"体重不要大于"+WEIGHT_MAX+"千克",Toast.LENGTH_SHORT).show();
                }else{
                    result = true;
                }
                break;
            case FAT:
                if (value<FAT_MIN){
                    Toast.makeText(context,"体脂不要低于"+FAT_MIN+"%",Toast.LENGTH_SHORT).show();
                } else if(value>FAT_MAX){
                    Toast.makeText(context,"体脂不要大于"+FAT_MAX+"%",Toast.LENGTH_SHORT).show();
                }else{
                    result = true;
                }
                break;
            default:
                result = true;
        }
        return result;
    }

    private String getNewValue(String num, String append, int isInt){
        if(isInt==1){
            if(num.equals("0")&&!append.equals(".")){
                return append+"";
            }else{
                if(num.length()==3){
                    Toast.makeText(context,"数字过大",Toast.LENGTH_SHORT).show();
                    return num;
                }else if(!append.equals(".")){
                    return num+""+append;
                }else{
                    Toast.makeText(context,"不能输入小数",Toast.LENGTH_SHORT).show();
                    return num;
                }
            }
        }else{
            //已经是小数了
            if(num.contains(".")){
                //如果这个符号不是.的话，说明加的是数字，检查小数位数，看看还能不能添加
                if (!append.equals(".")){
                    String digit = num.substring(num.indexOf("."),num.length());
                    if(digit.length()>=3){
                        Toast.makeText(context,"最多两位小数",Toast.LENGTH_SHORT).show();
                        return num;
                    }else{
                        return num+""+append;
                    }
                }else{//如果是.的话，因为已经是小数了，所以不用管，直接返回
                    return num;
                }
            }else{
                if (!append.equals(".")){
                    if(num.equals("0")){
                        return append+"";
                    }else{
                        if(num.length()>=3){
                            Toast.makeText(context,"数字过大",Toast.LENGTH_LONG).show();
                            return num;
                        }else{
                            return num+""+append;
                        }
                    }
                }else{
                    return num+".";
                }
            }
        }
    }
}
