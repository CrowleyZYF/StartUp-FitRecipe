package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.entity.DatePlanItem;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.Report;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class NutritionActivity extends Activity implements View.OnClickListener {

    private ImageView back_btn;
    private ImageView right_btn;
    private TextView header_name_text;

    private TextView ingredient_title, recipe_all_calorie, user_need_calorie, calorie_radio;
    private LinearLayout nutrition_punch;
    private ImageView meal_pic;
    private ImageView[] punchImageViews;
    private int[] punchIds = {R.id.nutrition_breakfast_punch, R.id.nutrition_add_meal_01_punch, R.id.nutrition_lunch_punch, R.id.nutrition_add_meal_02_punch, R.id.nutrition_dinner_punch};
    private PieChartView take_already_piechart;
    private ScrollView scrollView;
    private LinearLayoutForListView recipe_nutrition_list;
    private NutritionAdapter nutritionAdapter;
    private int iconIds[] = {R.drawable.icon_breakfast, R.drawable.icon_add_meal1, R.drawable.icon_lunch, R.drawable.icon_add_meal2, R.drawable.icon_dinner};
    private int finishIconIds[] = {R.drawable.icon_breakfast_finish, R.drawable.icon_add_meal1_finish, R.drawable.icon_lunch_finish, R.drawable.icon_add_meal2_finish, R.drawable.icon_dinner_finish};



    private DatePlan datePlan;
    private List<DatePlanItem> items;
    private String type;
    Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_nutrition);

        datePlan = (DatePlan) getIntent().getSerializableExtra("dateplan");
        items = datePlan.getItems();
        type = (String) getIntent().getSerializableExtra("itemtype");
        report = FrApplication.getInstance().getReport();
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        back_btn.setImageResource(R.drawable.icon_back_white);
        right_btn = (ImageView) findViewById(R.id.right_btn);
        right_btn.setVisibility(View.GONE);
        header_name_text = (TextView) findViewById(R.id.header_name_text);
        header_name_text.setText("营养分析");
        ingredient_title = (TextView) findViewById(R.id.ingredient_title);
        nutrition_punch = (LinearLayout) findViewById(R.id.nutrition_punch);
        meal_pic = (ImageView) findViewById(R.id.meal_pic);
        recipe_all_calorie = (TextView)findViewById(R.id.recipe_all_calorie);
        user_need_calorie = (TextView) findViewById(R.id.user_need_calorie);
        calorie_radio = (TextView) findViewById(R.id.calorie_radio);
        take_already_piechart = (PieChartView) findViewById(R.id.take_already_piechart);
        recipe_nutrition_list = (LinearLayoutForListView)findViewById(R.id.recipe_nutrition_list);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        punchImageViews = new ImageView[5];
        for(int i = 0; i < punchImageViews.length; i++) {
            punchImageViews[i] = (ImageView) findViewById(punchIds[i]);
        }
    }

    private void initData() {
        switch (type) {
            case "breakfast":
                ingredient_title.setText("早餐营养表");  display(0); break;
            case "add_meal_01":
                ingredient_title.setText("加餐营养表");  display(1);  break;
            case "add_meal_02":
                ingredient_title.setText("午餐营养表");  display(2); break;
            case "lunch":
                ingredient_title.setText("加餐营养表");  display(3); break;
            case "supper":
                ingredient_title.setText("晚餐营养表");  display(4); break;
            case "all":
                DatePlanItem itema = DatePlanItem.getAllItem(items);
                nutrition_punch.setVisibility(View.VISIBLE);
                for(int i = 0; i < punchImageViews.length; i++) {
                    if(items.get(i).isPunch()) {
                        punchImageViews[i].setImageResource(finishIconIds[i]);
                    }else {
                        punchImageViews[i].setImageResource(iconIds[i]);
                    }
                }
                meal_pic.setImageResource(R.drawable.icon_dinner_temp);
                long total = 0;
                for (int i = 0; i < items.size(); i++)
                    total += Math.round(items.get(i).getCalories_take());
                recipe_all_calorie.setText(total + "kcal");
                user_need_calorie.setText(report.getCaloriesIntake() + "kcal");
                calorie_radio.setText(Math.round(total * 100 / report.getCaloriesIntake()) + "%");
                double sum = itema.getProtein_take() + itema.getFat_take() + itema.getCarbohydrate_take();
                int a = (int) Math.round(itema.getCarbohydrate_take() * 100 / sum);
                int b = (int) Math.round(itema.getProtein_take() * 100 / sum);
                int c = 100 - a - b;
                take_already_piechart.setValue(new float[]{a, b, c});
                nutritionAdapter = new NutritionAdapter(this, itema.getNutritions());
        }
        recipe_nutrition_list.setAdapter(nutritionAdapter);
    }

    private void display(int i) {
        DatePlanItem item = items.get(i);
        meal_pic.setImageResource(iconIds[i]);
        recipe_all_calorie.setText(Math.round(item.getCalories_take()) + "kcal");
        calorie_radio.setText(Math.round(item.getCalories_take() * 100 / report.getCaloriesIntake()) + "%");
        double sum = item.getProtein_take() + item.getFat_take() + item.getCarbohydrate_take();
        int a = (int) Math.round(item.getCarbohydrate_take() * 100 / sum);
        int b = (int) Math.round(item.getProtein_take() * 100 / sum);
        int c = 100 - a - b;
        take_already_piechart.setValue(new float[]{a, b, c});
        nutrition_punch.setVisibility(View.GONE);
        user_need_calorie.setText(Math.round(report.getCaloriesIntake()) + "kcal");
        nutritionAdapter = new NutritionAdapter(this, item.getNutritions());
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        scrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn :
                finish();
                break;
            default:
                break;
        }
    }

    class NutritionAdapter extends BaseAdapter {

        Context context;
        List<Nutrition> data;

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
            return data.size();
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
                holder.textView1 = (TextView) convertView.findViewById(R.id.textview1);
                holder.textView2 = (TextView) convertView.findViewById(R.id.textview2);
                holder.textView3 = (TextView) convertView.findViewById(R.id.textview3);
                holder.textView4 = (TextView) convertView.findViewById(R.id.textview4);
                convertView.setTag(holder);
            }
            Nutrition nutrition = data.get(position);
            holder.textView1.setText(nutrition.getName());
            holder.textView2.setText("1300");
            holder.textView3.setText("1500g");
            holder.textView4.setText(Math.round(nutrition.getAmount()) + nutrition.getUnit());
            return convertView;
        }

        class ViewHolder {
            TextView textView1;
            TextView textView2;
            TextView textView3;
            TextView textView4;
        }
    }
}
