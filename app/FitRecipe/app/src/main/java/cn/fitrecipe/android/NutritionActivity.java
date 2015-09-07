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
import android.widget.Toast;

import java.util.List;

import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.Author;
import cn.fitrecipe.android.entity.DayPlan;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.PlanItem;
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



    private DayPlan dayPlan;
    private PlanItem.ItemType type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_nutrition);

        dayPlan = (DayPlan) getIntent().getSerializableExtra("dayplan");
        type = (PlanItem.ItemType) getIntent().getSerializableExtra("itemtype");
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
        Report report = FrApplication.getInstance().getReport();
        ingredient_title.setText(type.value() + "营养表");
        switch (type) {
            case BREAKFAST:
            case ADDMEAL_01:
            case ADDMEAL_02:
            case LUNCH:
            case SUPPER:
                int index = type.index();
                PlanItem item = dayPlan.getPlanItems().get(index);
                meal_pic.setImageResource(iconIds[type.index()]);
                recipe_all_calorie.setText(Math.round(item.gettCalories()) + "kcal");
                calorie_radio.setText(Math.round(item.gettCalories() * 100 / report.getCaloriesIntake()) + "%");
                double sum = item.getProtein() + item.getFat() + item.getCarbohydrate();
                int a = (int) Math.round(item.getCarbohydrate() * 100 / sum);
                int b = (int) Math.round(item.getProtein() * 100 / sum);
                int c = 100 - a - b;
                take_already_piechart.setValue(new float[]{a, b, c});
                nutrition_punch.setVisibility(View.GONE);
                user_need_calorie.setText(Math.round(report.getCaloriesIntake()) + "kcal");
                nutritionAdapter = new NutritionAdapter(this, item.gettNutrition());
                break;
            case ALL:
                PlanItem itema = PlanItem.getAllItem(dayPlan.getPlanItems());
                nutrition_punch.setVisibility(View.VISIBLE);
                for(int i = 0; i < punchImageViews.length; i++) {
                    if(dayPlan.getPlanItems().get(i).isPunched()) {
                        punchImageViews[i].setImageResource(finishIconIds[i]);
                    }else {
                        punchImageViews[i].setImageResource(iconIds[i]);
                    }
                }
                meal_pic.setImageResource(R.drawable.icon_dinner_temp);
                long total = 0;
                for (int i = 0; i < dayPlan.getPlanItems().size(); i++)
                    total += Math.round(dayPlan.getPlanItems().get(i).gettCalories());
                recipe_all_calorie.setText(total + "kcal");
                user_need_calorie.setText(report.getCaloriesIntake() + "kcal");
                calorie_radio.setText(Math.round(total * 100 / report.getCaloriesIntake()) + "%");
                sum = itema.getProtein() + itema.getFat() + itema.getCarbohydrate();
                a = (int) Math.round(itema.getCarbohydrate() * 100 / sum);
                b = (int) Math.round(itema.getProtein() * 100 / sum);
                c = 100 - a - b;
                take_already_piechart.setValue(new float[]{a, b, c});
                nutritionAdapter = new NutritionAdapter(this, itema.gettNutrition());
        }
        recipe_nutrition_list.setAdapter(nutritionAdapter);
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
