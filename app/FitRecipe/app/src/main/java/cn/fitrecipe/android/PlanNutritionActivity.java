package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.entity.SeriesPlan;

public class PlanNutritionActivity extends Activity implements View.OnClickListener {

    private Report report;
    private ImageView left_btn, prev_day_btn, next_day_btn;

    private SeriesPlan plan;
    private String plan_day_text;
    private int now_day, total_day;
    private PieChartView nutrition_chartview;
    private PieChartView dinner_chartview;

    private TextView plan_day, plan_all_calorie, user_need_calorie, plan_calorie_radio;
    private TextView protein_intake, carbohydrate_intake, lipids_intake;

    ArrayList<Nutrition> nutritions;
    List<DatePlanItem> dataPlanItems;
    DatePlanItem wholeDateItem;
    private LinearLayoutForListView plan_nutrition_list;
    private NutritionAdapter nutritionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_container_1);

        plan = (SeriesPlan) getIntent().getSerializableExtra("plan");
        plan_day_text = getIntent().getStringExtra("plan_day");
        now_day = Integer.parseInt(plan_day_text.substring(0,plan_day_text.indexOf("/")));
        total_day = Integer.parseInt(plan_day_text.substring(plan_day_text.indexOf("/") + 1,plan_day_text.length()));

        initView();
        initDate();
        initEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PlanNutritionActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PlanNutritionActivity");
        MobclickAgent.onResume(this);
    }

    private void initEvent() {
        left_btn.setOnClickListener(this);
        prev_day_btn.setOnClickListener(this);
        next_day_btn.setOnClickListener(this);

    }

    private void initDate() {
        report = FrApplication.getInstance().getReport();
        freshData(now_day);
    }

    private void initView() {
        plan_day = (TextView) findViewById(R.id.plan_day);
        plan_all_calorie = (TextView) findViewById(R.id.plan_all_calorie);
        user_need_calorie = (TextView) findViewById(R.id.user_need_calorie);
        plan_calorie_radio = (TextView) findViewById(R.id.plan_calorie_radio);

        left_btn = (ImageView) findViewById(R.id.left_btn);
        prev_day_btn = (ImageView) findViewById(R.id.prev_day_btn);
        next_day_btn = (ImageView) findViewById(R.id.next_day_btn);

        protein_intake = (TextView) findViewById(R.id.protein_intake);
        carbohydrate_intake = (TextView) findViewById(R.id.carbohydrate_intake);
        lipids_intake = (TextView) findViewById(R.id.lipids_intake);

        dinner_chartview = (PieChartView) findViewById(R.id.dinner_chartview);
        nutrition_chartview = (PieChartView) findViewById(R.id.nutrition_chartview);
        plan_nutrition_list = (LinearLayoutForListView)findViewById(R.id.plan_nutrition_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prev_day_btn:
                if(now_day>1){
                    now_day--;
                }else{
                    now_day = total_day;
                }
                freshData(now_day);
                break;
            case R.id.next_day_btn:
                if(now_day < total_day){
                    now_day++;
                }else{
                    now_day = 1;
                }
                freshData(now_day);
                break;
            case R.id.left_btn:
                finish();
                break;
        }

    }

    private void freshData(int now_day) {
        dataPlanItems =  plan.getDatePlans().get(now_day-1).getItems();//一日五餐

        plan_day.setText("第 " + plan_day_text + " 天");
        plan_all_calorie.setText(Math.round(plan.getDatePlans().get(now_day-1).getTotalCalories())+"kcal");
        user_need_calorie.setText("(" + report.getCaloriesIntake() + "kcal)");
        plan_calorie_radio.setText(Math.round(plan.getDatePlans().get(now_day-1).getTotalCalories()/report.getCaloriesIntake()*100)+"%");

        wholeDateItem = DatePlanItem.getAllItem(dataPlanItems);
        nutritions = wholeDateItem.getNutritions();
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
        dinner_chartview.setValue(mealCalorieRate, true, true, true);

        nutritionAdapter = new NutritionAdapter(this, nutritions);
        plan_nutrition_list.setAdapter(nutritionAdapter);
    }

    class NutritionAdapter extends BaseAdapter {

        Context context;
        List<Nutrition> data;
        String[] nutritionString = {"水", "蛋白质", "脂类", "碳水化合物", "纤维素", "钠", "维他命 C", "维他命 D", "不饱和脂肪酸", "胆固醇"};

        public NutritionAdapter(Context context, List<Nutrition> data) {
            this.context = context;
            this.data = data;
        }

        public void setData(List<Nutrition> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            if(data == null)    return 0;
            return data.size()+1;
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView != null)
                holder = (ViewHolder) convertView.getTag();
            else {
                convertView = View.inflate(context, R.layout.nutrition_list_item, null);
                holder = new ViewHolder();
                holder.list_item = (LinearLayout) convertView.findViewById(R.id.list_item);
                if(position%2==1){
                    holder.list_item.setBackgroundColor(getResources().getColor(R.color.nutrition_gray));
                }else{
                    holder.list_item.setBackgroundColor(getResources().getColor(R.color.white));
                }
                holder.textView1 = (TextView) convertView.findViewById(R.id.textview1);
                holder.textView2 = (TextView) convertView.findViewById(R.id.textview2);
                holder.textView3 = (TextView) convertView.findViewById(R.id.textview3);
                holder.textView4 = (TextView) convertView.findViewById(R.id.textview4);
                holder.textViewDash = (TextView) convertView.findViewById(R.id.textview_dash);
                convertView.setTag(holder);
            }
            if (position==0){
                holder.textView1.setText("营养元素");
                holder.textView2.setText("建议摄入范围");
                holder.textView4.setText("该计划摄入");
                holder.textView3.setVisibility(View.GONE);
                holder.textViewDash.setVisibility(View.GONE);
                holder.textView1.setTextColor(getResources().getColor(R.color.login_input_text_color));
                holder.textView2.setTextColor(getResources().getColor(R.color.login_input_text_color));
                holder.textView3.setTextColor(getResources().getColor(R.color.login_input_text_color));
                holder.textView4.setTextColor(getResources().getColor(R.color.login_input_text_color));
                holder.textViewDash.setTextColor(getResources().getColor(R.color.login_input_text_color));
            }else {
                Nutrition nutrition = data.get(position-1);
                holder.textView1.setText(nutrition.getName());
                int nutritionResult = 0;
                for(int i=0; i<nutritionString.length; i++){
                    if (nutrition.getName().equals(nutritionString[i])){
                        nutritionResult = i;
                        i = nutritionString.length;
                    }
                }
                switch (nutritionResult){
                    case 0:
                        holder.textView2.setText(Math.round(report.getWaterIntakeMin() * 1000)+"");
                        holder.textView3.setText(Math.round(report.getWaterIntakeMax() * 1000)+"g");
                        break;
                    case 1:
                        holder.textView2.setText(Math.round(report.getProteinIntakeMin())+"");
                        holder.textView3.setText(Math.round(report.getProteinIntakeMax())+"g");
                        break;
                    case 2:
                        holder.textView2.setText(Math.round(report.getFatIntakeMin())+"");
                        holder.textView3.setText(Math.round(report.getFatIntakeMax())+"g");
                        break;
                    case 3:
                        holder.textView2.setText(Math.round(report.getCarbohydrateIntakeMin())+"");
                        holder.textView3.setText(Math.round(report.getCarbohydrateIntakeMax())+"g");
                        break;
                    case 4:
                        holder.textView2.setText(Math.round(report.getFiberIntakeMin())+"");
                        holder.textView3.setText(Math.round(report.getFiberIntakeMax())+"g");
                        break;
                    case 5:
                        holder.textView2.setText(Math.round(report.getSodiumIntakeMin())+"");
                        holder.textView3.setText(Math.round(report.getSodiumIntakeMax())+"mg");
                        break;
                    case 6:
                        holder.textView2.setText(Math.round(report.getVCIntakeMin())+"");
                        holder.textView3.setText(Math.round(report.getVCIntakeMax())+"mg");
                        break;
                    case 7:
                        holder.textView2.setText(Math.round(report.getVDIntakeMin())+"");
                        holder.textView3.setText(Math.round(report.getVDIntakeMax())+"µg");
                        break;
                    case 8:
                        holder.textView2.setText(Math.round(report.getUnsaturatedFattyAcidsIntakeMin())+"");
                        holder.textView3.setText(Math.round(report.getUnsaturatedFattyAcidsIntakeMin())+"g");
                        break;
                    case 9:
                        holder.textView2.setText(Math.round(report.getCholesterolIntakeMin())+"");
                        holder.textView3.setText(Math.round(report.getCholesterolIntakeMin())+"mg");
                        break;
                }
                holder.textView4.setText(String.format("%.1f", nutrition.getAmount()) + nutrition.getUnit());
                holder.textView3.setVisibility(View.VISIBLE);
                holder.textViewDash.setVisibility(View.VISIBLE);
                holder.textView1.setTextColor(getResources().getColor(R.color.nutrition_text_gray));
                holder.textView2.setTextColor(getResources().getColor(R.color.nutrition_text_gray));
                holder.textView3.setTextColor(getResources().getColor(R.color.nutrition_text_gray));
                holder.textView4.setTextColor(getResources().getColor(R.color.nutrition_text_gray));
                holder.textViewDash.setTextColor(getResources().getColor(R.color.nutrition_text_gray));
            }
            return convertView;
        }

        class ViewHolder {
            LinearLayout list_item;
            TextView textView1;
            TextView textView2;
            TextView textView3;
            TextView textView4;
            TextView textViewDash;
        }
    }
}
