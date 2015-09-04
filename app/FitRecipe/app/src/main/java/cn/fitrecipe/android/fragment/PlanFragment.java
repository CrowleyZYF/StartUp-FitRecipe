package cn.fitrecipe.android.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.fitrecipe.android.Adpater.PlanElementAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.IngredientActivity;
import cn.fitrecipe.android.NutritionActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.UI.SlidingPage;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.DayPlan;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.PlanItem;
import cn.fitrecipe.android.entity.Report;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class PlanFragment extends Fragment implements View.OnClickListener{

    private SlidingPage mRightMenu;

    //Report
    private Report report;

    private LinearLayoutForListView plans;
    private List<PlanItem> items;
    private DayPlan dayPlan;
    private PlanElementAdapter adapter;

    //view 2
    private TextView ingredient_title, recipe_all_calorie, user_need_calorie, calorie_radio, plan_status_day;
    private LinearLayout nutrition_punch;
    private ImageView meal_pic, shopping_btn, next_btn, prev_btn;
    private ImageView[] punchImageViews;
    private int[] punchIds = {R.id.nutrition_breakfast_punch, R.id.nutrition_add_meal_01_punch, R.id.nutrition_lunch_punch, R.id.nutrition_add_meal_02_punch, R.id.nutrition_dinner_punch};
    private PieChartView take_already_piechart;
    private LinearLayoutForListView recipe_nutrition_list;
    private NutritionAdapter nutritionAdapter;
    private boolean toggleFlag = true;

    public static final int BREAKFAST_CODE = 00;
    public static final int ADDMEAL_01_CODE = 01;
    public static final int LUNCH_CODE = 02;
    public static final int ADDMEAL_02_CODE = 03;
    public static final int SUPPER_CODE = 04;

    private int iconIds[] = {R.drawable.icon_breakfast, R.drawable.icon_add_meal1, R.drawable.icon_lunch, R.drawable.icon_add_meal2, R.drawable.icon_dinner};
    private int finishIconIds[] = {R.drawable.icon_breakfast_finish, R.drawable.icon_add_meal1_finish, R.drawable.icon_lunch_finish, R.drawable.icon_add_meal2_finish, R.drawable.icon_dinner_finish};

    private int pointer = 0;
    private long now;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        report = FrDbHelper.getInstance(getActivity()).getReport(FrApplication.getInstance().getAuthor());
        initView(v);
        initEvent();
        initData();
        return v;
    }

    private void initEvent() {
        shopping_btn.setOnClickListener(this);
        next_btn.setOnClickListener(this);
        prev_btn.setOnClickListener(this);
    }

    private void initView(View v) {
        plans = (LinearLayoutForListView) v.findViewById(R.id.plans);

        ingredient_title = (TextView) v.findViewById(R.id.ingredient_title);
        nutrition_punch = (LinearLayout) v.findViewById(R.id.nutrition_punch);
        meal_pic = (ImageView) v.findViewById(R.id.meal_pic);
        recipe_all_calorie = (TextView)v.findViewById(R.id.recipe_all_calorie);
        user_need_calorie = (TextView) v.findViewById(R.id.user_need_calorie);
        calorie_radio = (TextView) v.findViewById(R.id.calorie_radio);
        take_already_piechart = (PieChartView) v.findViewById(R.id.take_already_piechart);
        recipe_nutrition_list = (LinearLayoutForListView)v.findViewById(R.id.recipe_nutrition_list);
        shopping_btn = (ImageView) v.findViewById(R.id.shopping_btn);
        plan_status_day = (TextView) v.findViewById(R.id.plan_status_day);
        next_btn = (ImageView) v.findViewById(R.id.next_btn);
        prev_btn = (ImageView) v.findViewById(R.id.prev_btn);
        punchImageViews = new ImageView[5];
        for(int i = 0; i < punchImageViews.length; i++) {
            punchImageViews[i] = (ImageView) v.findViewById(punchIds[i]);
        }

        mRightMenu = (SlidingPage) v.findViewById(R.id.filter_menu);
    }

    private void initData() {
        now = System.currentTimeMillis();
        adapter = new PlanElementAdapter(this, items, report);
        plans.setAdapter(adapter);
        nutritionAdapter = new NutritionAdapter(getActivity(), null);
        recipe_nutrition_list.setAdapter(nutritionAdapter);
//        switchPlan(pointer);
    }


    @Override
    public void onResume() {
        super.onResume();
        switchPlan(pointer);
    }


    private void switchPlan(int pointer) {
        Date date = new Date(now + pointer * 24 * 3600 * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);
        dayPlan = FrDbHelper.getInstance(getActivity()).getDayPlan(strDate);
        if(dayPlan == null) {
            dayPlan = new DayPlan();
            dayPlan.setDate(strDate);
            dayPlan = FrDbHelper.getInstance(getActivity()).addDayPlan(dayPlan);
        }
        items = dayPlan.getPlanItems();
        plan_status_day.setText(pointer+"");
        adapter.setData(items);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Object obj = null;
        if(resultCode == getActivity().RESULT_OK && data.hasExtra("obj_selected")) {
            obj = data.getSerializableExtra("obj_selected");
            switch (requestCode) {
                case BREAKFAST_CODE:
                    items.get(0).addContent(obj);
                    FrDbHelper.getInstance(getActivity()).addPlanItem(dayPlan.getPlanItems().get(0));
                    break;
                case ADDMEAL_01_CODE:
                    items.get(1).addContent(obj);
                    FrDbHelper.getInstance(getActivity()).addPlanItem(dayPlan.getPlanItems().get(1));
                    break;
                case LUNCH_CODE:
                    items.get(2).addContent(obj);
                    FrDbHelper.getInstance(getActivity()).addPlanItem(dayPlan.getPlanItems().get(2));
                    break;
                case ADDMEAL_02_CODE:
                    items.get(3).addContent(obj);
                    FrDbHelper.getInstance(getActivity()).addPlanItem(dayPlan.getPlanItems().get(3));
                    break;
                case SUPPER_CODE:
                    items.get(4).addContent(obj);
                    FrDbHelper.getInstance(getActivity()).addPlanItem(dayPlan.getPlanItems().get(4));
                    break;
            }
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void toggle(PlanItem.ItemType type) {
//        Toast.makeText(getActivity(), type.value()+"", Toast.LENGTH_SHORT).show();
        if(toggleFlag) {
            ingredient_title.setText(type.value() + "营养表");
            switch (type) {
                case BREAKFAST:
                case ADDMEAL_01:
                case ADDMEAL_02:
                case LUNCH:
                case SUPPER:
                    int index = type.index();
                    PlanItem item = items.get(index);
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
                    nutritionAdapter.setData(item.gettNutrition());
                    break;
                case ALL:
                    PlanItem itema = PlanItem.getAllItem(items);
                    if (itema.size() == 0) {
                        Toast.makeText(getActivity(), "请添加食谱、食材后再查看营养表！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    nutrition_punch.setVisibility(View.VISIBLE);
                    for(int i = 0; i < punchImageViews.length; i++) {
                        if(items.get(i).isPunched()) {
                            punchImageViews[i].setImageResource(finishIconIds[i]);
                        }else {
                            punchImageViews[i].setImageResource(iconIds[i]);
                        }
                    }
                    meal_pic.setImageResource(R.drawable.icon_dinner_temp);
                    long total = 0;
                    for (int i = 0; i < items.size(); i++)
                        total += Math.round(items.get(i).gettCalories());
                    recipe_all_calorie.setText(total + "kcal");
                    user_need_calorie.setText(report.getCaloriesIntake() + "kcal");
                    calorie_radio.setText(Math.round(total * 100 / report.getCaloriesIntake()) + "%");
                    sum = itema.getProtein() + itema.getFat() + itema.getCarbohydrate();
                    a = (int) Math.round(itema.getCarbohydrate() * 100 / sum);
                    b = (int) Math.round(itema.getProtein() * 100 / sum);
                    c = 100 - a - b;
                    take_already_piechart.setValue(new float[]{a, b, c});
                    nutritionAdapter.setData(itema.gettNutrition());
            }
            nutritionAdapter.notifyDataSetChanged();
        }
        toggleFlag = !toggleFlag;
        //mRightMenu.toggle();
        Intent intent = new Intent(getActivity(), NutritionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_btn:
                Intent intent = new Intent(getActivity(), IngredientActivity.class);
                startActivity(intent);
                break;
            case R.id.next_btn:
                pointer++;
                switchPlan(pointer);
                break;
            case R.id.prev_btn:
                pointer--;
                switchPlan(pointer);
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
