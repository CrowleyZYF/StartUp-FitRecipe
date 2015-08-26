package cn.fitrecipe.android.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RecipeActivity;
import cn.fitrecipe.android.SelectRecipeActivity;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.UI.SlidingPage;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.entity.Report;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class PlanFragment extends Fragment implements View.OnClickListener {

    private SlidingPage mRightMenu;

    //breakfast
    private final int BREAKFAST_CODE = 01;
    private RelativeLayout add_breakfast;
    private LinearLayoutForListView breakfast_content;
    private List<Object> breakfast_recipes;
    private RecipeAdapter breakfast_adapter;
    private TextView breakfast_carbohydrate_intake, breakfast_protein_intake, breakfast_fat_intake, calorie_breakfast_intake;
    private double carbohydrate_intake, protein_intake, fat_intake, calorie_intake;
    private TextView  calorie_breakfast_need, breakfast_carbohydrate_need, breakfast_protein_need, breakfast_fat_need;
    private TextView breakfast_carbohydrate_rate, breakfast_protein_rate, breakfast_fat_rate, calorie_breakfast_radio;
    private ImageView breakfast_nutrition;

    //add 1
    private RelativeLayout add_meal_01;
    private final int ADDMEAL_01_CODE = 02;
    private LinearLayoutForListView addmeal_01_content;
    private List<Object> add_1_recipes;
    private RecipeAdapter add_1_adapter;
    private TextView addmeal_01_carbohydrate_intake, addmeal_01_protein_intake, addmeal_01_fat_intake, calorie_addmeal_01_intake;
    private double carbohydrate_intake1, protein_intake1, fat_intake1, calorie_intake1;
    private TextView  calorie_addmeal_01_need, addmeal_01_carbohydrate_need, addmeal_01_protein_need, addmeal_01_fat_need;
    private TextView addmeal_01_carbohydrate_rate, addmeal_01_protein_rate, addmeal_01_fat_rate, calorie_addmeal_01_radio;
    private ImageView addmeal_01_nutrition;

    //lunch
    private final int LUNCH_CODE = 03;
    private RelativeLayout add_lunch;
    private LinearLayoutForListView lunch_content;
    private List<Object> lunch_recipes;
    private RecipeAdapter lunch_adapter;
    private TextView lunch_carbohydrate_intake, lunch_protein_intake, lunch_fat_intake, calorie_lunch_intake;
    private double carbohydrate_intake2, protein_intake2, fat_intake2, calorie_intake2;
    private TextView  calorie_lunch_need, lunch_carbohydrate_need, lunch_protein_need, lunch_fat_need;
    private TextView lunch_carbohydrate_rate, lunch_protein_rate, lunch_fat_rate, calorie_lunch_radio;
    private ImageView lunch_nutrition;

    // add 2
    private RelativeLayout add_meal_02;
    private final int ADDMEAL_02_CODE = 04;
    private LinearLayoutForListView addmeal_02_content;
    private List<Object> add_2_recipes;
    private RecipeAdapter add_2_adapter;
    private TextView addmeal_02_carbohydrate_intake, addmeal_02_protein_intake, addmeal_02_fat_intake, calorie_addmeal_02_intake;
    private double carbohydrate_intake3, protein_intake3, fat_intake3, calorie_intake3;
    private TextView  calorie_addmeal_02_need, addmeal_02_carbohydrate_need, addmeal_02_protein_need, addmeal_02_fat_need;
    private TextView addmeal_02_carbohydrate_rate, addmeal_02_protein_rate, addmeal_02_fat_rate, calorie_addmeal_02_radio;
    private ImageView addmeal_02_nutrition;

    //supper
    private final int SUPPER_CODE = 05;
    private RelativeLayout add_supper;
    private LinearLayoutForListView supper_content;
    private List<Object> supper_recipes;
    private RecipeAdapter supper_adapter;
    private TextView supper_carbohydrate_intake, supper_protein_intake, supper_fat_intake, calorie_supper_intake;
    private double carbohydrate_intake4, protein_intake4, fat_intake4, calorie_intake4;
    private TextView  calorie_supper_need, supper_carbohydrate_need, supper_protein_need, supper_fat_need;
    private TextView supper_carbohydrate_rate, supper_protein_rate, supper_fat_rate, calorie_supper_radio;
    private ImageView supper_nutrition;


    //Report
    private Report report;

    //view 2
    private TextView ingredient_title, recipe_all_calorie, user_need_calorie, calorie_radio;
    private LinearLayout nutrition_punch;
    private ImageView meal_pic;
    private PieChartView take_already_piechart;
    private LinearLayoutForListView recipe_nutrition_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        report = FrDbHelper.getInstance(getActivity()).getReport(FrApplication.getInstance().getAuthor());
        initView(v);
        initView2(v);
        initData();
        initEvent();
        return v;
    }

    private void initView(View v) {

        //breakfast
        add_breakfast = (RelativeLayout) v.findViewById(R.id.add_breakfast);
        breakfast_content = (LinearLayoutForListView) v.findViewById(R.id.breakfast_content);
        breakfast_carbohydrate_intake = (TextView) v.findViewById(R.id.breakfast_carbohydrate_intake);
        breakfast_protein_intake = (TextView) v.findViewById(R.id.breakfast_protein_intake);
        breakfast_fat_intake = (TextView) v.findViewById(R.id.breakfast_fat_intake);
        calorie_breakfast_intake = (TextView) v.findViewById(R.id.calorie_breakfast_intake);
        //
        calorie_breakfast_need = (TextView) v.findViewById(R.id.calorie_breakfast_need);
        breakfast_carbohydrate_need = (TextView) v.findViewById(R.id.breakfast_carbohydrate_need);
        breakfast_protein_need = (TextView) v.findViewById(R.id.breakfast_protein_need);
        breakfast_fat_need = (TextView) v.findViewById(R.id.breakfast_fat_need);
        breakfast_carbohydrate_rate = (TextView) v.findViewById(R.id.breakfast_carbohydrate_rate);
        breakfast_protein_rate = (TextView) v.findViewById(R.id.breakfast_protein_rate);
        breakfast_fat_rate = (TextView) v.findViewById(R.id.breakfast_fat_rate);
        calorie_breakfast_radio = (TextView) v.findViewById(R.id.calorie_breakfast_radio);
        breakfast_nutrition = (ImageView) v.findViewById(R.id.breakfast_nutrition);

        //add 1
        add_meal_01 = (RelativeLayout) v.findViewById(R.id.add_meal_01);
        addmeal_01_content = (LinearLayoutForListView) v.findViewById(R.id.addmeal_01_content);
        addmeal_01_carbohydrate_intake = (TextView) v.findViewById(R.id.addmeal_01_carbohydrate_intake);
        addmeal_01_protein_intake = (TextView) v.findViewById(R.id.addmeal_01_protein_intake);
        addmeal_01_fat_intake = (TextView) v.findViewById(R.id.addmeal_01_fat_intake);
        calorie_addmeal_01_intake = (TextView) v.findViewById(R.id.calorie_addmeal_01_intake);
        //
        calorie_addmeal_01_need = (TextView) v.findViewById(R.id.calorie_addmeal_01_need);
        addmeal_01_carbohydrate_need = (TextView) v.findViewById(R.id.addmeal_01_carbohydrate_need);
        addmeal_01_protein_need = (TextView) v.findViewById(R.id.addmeal_01_protein_need);
        addmeal_01_fat_need = (TextView) v.findViewById(R.id.addmeal_01_fat_need);
        addmeal_01_carbohydrate_rate = (TextView) v.findViewById(R.id.addmeal_01_carbohydrate_rate);
        addmeal_01_protein_rate = (TextView) v.findViewById(R.id.addmeal_01_protein_rate);
        addmeal_01_fat_rate = (TextView) v.findViewById(R.id.addmeal_01_fat_rate);
        calorie_addmeal_01_radio = (TextView) v.findViewById(R.id.calorie_addmeal_01_radio);
        addmeal_01_nutrition = (ImageView) v.findViewById(R.id.addmeal_01_nutrition);

        //lunch
        add_lunch = (RelativeLayout) v.findViewById(R.id.add_lunch);
        lunch_content = (LinearLayoutForListView) v.findViewById(R.id.lunch_content);
        lunch_carbohydrate_intake = (TextView) v.findViewById(R.id.lunch_carbohydrate_intake);
        lunch_protein_intake = (TextView) v.findViewById(R.id.lunch_protein_intake);
        lunch_fat_intake = (TextView) v.findViewById(R.id.lunch_fat_intake);
        calorie_lunch_intake = (TextView) v.findViewById(R.id.calorie_lunch_intake);
        //
        calorie_lunch_need = (TextView) v.findViewById(R.id.calorie_lunch_need);
        lunch_carbohydrate_need = (TextView) v.findViewById(R.id.lunch_carbohydrate_need);
        lunch_protein_need = (TextView) v.findViewById(R.id.lunch_protein_need);
        lunch_fat_need = (TextView) v.findViewById(R.id.lunch_fat_need);
        lunch_carbohydrate_rate = (TextView) v.findViewById(R.id.lunch_carbohydrate_rate);
        lunch_protein_rate = (TextView) v.findViewById(R.id.lunch_protein_rate);
        lunch_fat_rate = (TextView) v.findViewById(R.id.lunch_fat_rate);
        calorie_lunch_radio = (TextView) v.findViewById(R.id.calorie_breakfast_radio);
        lunch_nutrition = (ImageView) v.findViewById(R.id.lunch_nutrition);


        //add 2
        add_meal_02 = (RelativeLayout) v.findViewById(R.id.add_meal_02);
        addmeal_02_content = (LinearLayoutForListView) v.findViewById(R.id.addmeal_02_content);
        addmeal_02_carbohydrate_intake = (TextView) v.findViewById(R.id.addmeal_02_carbohydrate_intake);
        addmeal_02_protein_intake = (TextView) v.findViewById(R.id.addmeal_02_protein_intake);
        addmeal_02_fat_intake = (TextView) v.findViewById(R.id.addmeal_02_fat_intake);
        calorie_addmeal_02_intake = (TextView) v.findViewById(R.id.calorie_addmeal_02_intake);

        //
        calorie_addmeal_02_need = (TextView) v.findViewById(R.id.calorie_addmeal_02_need);
        addmeal_02_carbohydrate_need = (TextView) v.findViewById(R.id.addmeal_02_carbohydrate_need);
        addmeal_02_protein_need = (TextView) v.findViewById(R.id.addmeal_02_protein_need);
        addmeal_02_fat_need = (TextView) v.findViewById(R.id.addmeal_02_fat_need);
        addmeal_02_carbohydrate_rate = (TextView) v.findViewById(R.id.addmeal_02_carbohydrate_rate);
        addmeal_02_protein_rate = (TextView) v.findViewById(R.id.addmeal_02_protein_rate);
        addmeal_02_fat_rate = (TextView) v.findViewById(R.id.addmeal_02_fat_rate);
        calorie_addmeal_02_radio = (TextView) v.findViewById(R.id.calorie_addmeal_02_radio);
        addmeal_02_nutrition = (ImageView) v.findViewById(R.id.addmeal_02_nutrition);

        //supper
        add_supper = (RelativeLayout) v.findViewById(R.id.add_supper);
        supper_content = (LinearLayoutForListView) v.findViewById(R.id.supper_content);
        supper_carbohydrate_intake = (TextView) v.findViewById(R.id.supper_carbohydrate_intake);
        supper_protein_intake = (TextView) v.findViewById(R.id.supper_protein_intake);
        supper_fat_intake = (TextView) v.findViewById(R.id.supper_fat_intake);
        calorie_supper_intake = (TextView) v.findViewById(R.id.calorie_supper_intake);

        //
        calorie_supper_need = (TextView) v.findViewById(R.id.calorie_supper_need);
        supper_carbohydrate_need = (TextView) v.findViewById(R.id.supper_carbohydrate_need);
        supper_protein_need = (TextView) v.findViewById(R.id.supper_protein_need);
        supper_fat_need = (TextView) v.findViewById(R.id.supper_fat_need);
        supper_carbohydrate_rate = (TextView) v.findViewById(R.id.supper_carbohydrate_rate);
        supper_protein_rate = (TextView) v.findViewById(R.id.supper_protein_rate);
        supper_fat_rate = (TextView) v.findViewById(R.id.supper_fat_rate);
        calorie_supper_radio = (TextView) v.findViewById(R.id.calorie_breakfast_radio);
        supper_nutrition = (ImageView) v.findViewById(R.id.supper_nutrition);

        mRightMenu = (SlidingPage) v.findViewById(R.id.filter_menu);


    }

    private void initView2(View v) {
        ingredient_title = (TextView) v.findViewById(R.id.ingredient_title);
        nutrition_punch = (LinearLayout) v.findViewById(R.id.nutrition_punch);
        meal_pic = (ImageView) v.findViewById(R.id.meal_pic);
        recipe_all_calorie = (TextView)v.findViewById(R.id.recipe_all_calorie);
        user_need_calorie = (TextView) v.findViewById(R.id.user_need_calorie);
        calorie_radio = (TextView) v.findViewById(R.id.calorie_radio);
        take_already_piechart = (PieChartView) v.findViewById(R.id.take_already_piechart);
        recipe_nutrition_list = (LinearLayoutForListView)v.findViewById(R.id.recipe_nutrition_list);
    }

    private void initData() {
        //breakfast
        if(breakfast_recipes == null) {
            breakfast_recipes = new ArrayList<>();
        }
        breakfast_adapter = new RecipeAdapter(getActivity(), breakfast_recipes, BREAKFAST_CODE);
        breakfast_adapter.setMode(Attributes.Mode.Single);
        breakfast_content.setAdapter(breakfast_adapter);
        carbohydrate_intake = 0;
        protein_intake = 0;
        fat_intake = 0;
        calorie_intake = 0;

        //add 1
        if(add_1_recipes == null) {
            add_1_recipes = new ArrayList<>();
        }
        add_1_adapter = new RecipeAdapter(getActivity(), add_1_recipes, ADDMEAL_01_CODE);
        addmeal_01_content.setAdapter(add_1_adapter);
        carbohydrate_intake1 = 0;
        protein_intake1 = 0;
        fat_intake1 = 0;
        calorie_intake1 = 0;

        //lunch
        if(lunch_recipes == null) {
            lunch_recipes = new ArrayList<>();
        }
        lunch_adapter = new RecipeAdapter(getActivity(), lunch_recipes, LUNCH_CODE);
        lunch_content.setAdapter(lunch_adapter);
        carbohydrate_intake2 = 0;
        protein_intake2 = 0;
        fat_intake2 = 0;
        calorie_intake2 = 0;

        //add 2
        if(add_2_recipes == null) {
            add_2_recipes = new ArrayList<>();
        }
        add_2_adapter = new RecipeAdapter(getActivity(), add_2_recipes, ADDMEAL_02_CODE);
        addmeal_02_content.setAdapter(add_2_adapter);
        carbohydrate_intake3 = 0;
        protein_intake3 = 0;
        fat_intake3 = 0;
        calorie_intake3 = 0;

        //supper
        if(supper_recipes == null) {
            supper_recipes = new ArrayList<>();
        }
        supper_adapter = new RecipeAdapter(getActivity(), supper_recipes, SUPPER_CODE);
        supper_content.setAdapter(supper_adapter);
        carbohydrate_intake4 = 0;
        protein_intake4 = 0;
        fat_intake4 = 0;
        calorie_intake4 = 0;
    }



    private void initEvent() {
        add_breakfast.setOnClickListener(this);
        add_meal_01.setOnClickListener(this);
        add_lunch.setOnClickListener(this);
        add_meal_02.setOnClickListener(this);
        add_supper.setOnClickListener(this);

        breakfast_nutrition.setOnClickListener(this);
        lunch_nutrition.setOnClickListener(this);
        addmeal_01_nutrition.setOnClickListener(this);
        supper_nutrition.setOnClickListener(this);
        addmeal_02_nutrition.setOnClickListener(this);



        breakfast_content.setOnItemClickListener(new MyOnItemClickListener(breakfast_recipes));
        addmeal_01_content.setOnItemClickListener(new MyOnItemClickListener(add_1_recipes));
        lunch_content.setOnItemClickListener(new MyOnItemClickListener(lunch_recipes));
        addmeal_02_content.setOnItemClickListener(new MyOnItemClickListener(add_2_recipes));
        supper_content.setOnItemClickListener(new MyOnItemClickListener(supper_recipes));
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        List<Object> data;

        public MyOnItemClickListener(List<Object> data) {
            this.data = data;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(data.get(position) instanceof Recipe) {
                String recipe_id = ((Recipe)data.get(position)).getId() +"";
                Intent intent=new Intent(getActivity(),RecipeActivity.class);
                intent.putExtra("id", recipe_id);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BREAKFAST_CODE:
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("obj_selected")) {
                    Recipe recipe = (Recipe) data.getSerializableExtra("obj_selected");
                    breakfast_recipes.add(recipe);
                    carbohydrate_intake += recipe.getNutrition_set().get(3).getAmount();
                    protein_intake += recipe.getNutrition_set().get(1).getAmount();
                    fat_intake += recipe.getNutrition_set().get(2).getAmount();
                    calorie_intake += Math.round(recipe.getTotal_amount() * recipe.getCalories() / 100);
                    breakfast_protein_intake.setText(protein_intake + "");
                    breakfast_fat_intake.setText(fat_intake + "");
                    calorie_breakfast_intake.setText(Math.round(calorie_intake) + "");
                    breakfast_carbohydrate_intake.setText(Math.round(carbohydrate_intake) + "");
                    breakfast_adapter.notifyDataSetChanged();
                }
                break;
            case ADDMEAL_01_CODE:
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("obj_selected")) {
                    Recipe recipe = (Recipe) data.getSerializableExtra("obj_selected");
                    add_1_recipes.add(recipe);
                    carbohydrate_intake1 += recipe.getNutrition_set().get(3).getAmount();
                    protein_intake1 += recipe.getNutrition_set().get(1).getAmount();
                    fat_intake1 += recipe.getNutrition_set().get(2).getAmount();
                    calorie_intake1 += Math.round(recipe.getTotal_amount() * recipe.getCalories() / 100);
                    addmeal_01_protein_intake.setText(protein_intake1 + "");
                    addmeal_01_fat_intake.setText(fat_intake1 + "");
                    calorie_addmeal_01_intake.setText(Math.round(calorie_intake1) + "");
                    addmeal_01_carbohydrate_intake.setText(Math.round(carbohydrate_intake1) + "");
                    add_1_adapter.notifyDataSetChanged();
                }
                break;
            case LUNCH_CODE:
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("obj_selected")) {
                    Recipe recipe = (Recipe) data.getSerializableExtra("obj_selected");
                    lunch_recipes.add(recipe);
                    carbohydrate_intake2 += recipe.getNutrition_set().get(3).getAmount();
                    protein_intake2 += recipe.getNutrition_set().get(1).getAmount();
                    fat_intake2 += recipe.getNutrition_set().get(2).getAmount();
                    calorie_intake2 += Math.round(recipe.getTotal_amount() * recipe.getCalories() / 100);
                    lunch_protein_intake.setText(protein_intake2 + "");
                    lunch_fat_intake.setText(fat_intake2 + "");
                    calorie_lunch_intake.setText(Math.round(calorie_intake2) + "");
                    lunch_carbohydrate_intake.setText(Math.round(carbohydrate_intake2) + "");
                    lunch_adapter.notifyDataSetChanged();
                }
                break;
            case ADDMEAL_02_CODE:
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("obj_selected")) {
                    Recipe recipe = (Recipe) data.getSerializableExtra("obj_selected");
                    add_2_recipes.add(recipe);
                    carbohydrate_intake3 += recipe.getNutrition_set().get(3).getAmount();
                    protein_intake3 += recipe.getNutrition_set().get(1).getAmount();
                    fat_intake3 += recipe.getNutrition_set().get(2).getAmount();
                    calorie_intake3 += Math.round(recipe.getTotal_amount() * recipe.getCalories() / 100);
                    addmeal_02_protein_intake.setText(protein_intake3 + "");
                    addmeal_02_fat_intake.setText(fat_intake3 + "");
                    calorie_addmeal_02_intake.setText(Math.round(calorie_intake3) + "");
                    addmeal_02_carbohydrate_intake.setText(Math.round(carbohydrate_intake3) + "");
                    add_2_adapter.notifyDataSetChanged();
                }
                break;
            case SUPPER_CODE:
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("obj_selected")) {
                    Recipe recipe = (Recipe) data.getSerializableExtra("obj_selected");
                    supper_recipes.add(recipe);
                    carbohydrate_intake4 += recipe.getNutrition_set().get(3).getAmount();
                    protein_intake4 += recipe.getNutrition_set().get(1).getAmount();
                    fat_intake4 += recipe.getNutrition_set().get(2).getAmount();
                    calorie_intake4 += Math.round(recipe.getTotal_amount() * recipe.getCalories() / 100);
                    supper_protein_intake.setText(protein_intake4 + "");
                    supper_fat_intake.setText(fat_intake4 + "");
                    calorie_supper_intake.setText(Math.round(calorie_intake4) + "");
                    supper_carbohydrate_intake.setText(Math.round(carbohydrate_intake4) + "");
                    supper_adapter.notifyDataSetChanged();
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_breakfast:
                Intent intent = new Intent(getActivity(), SelectRecipeActivity.class);
                startActivityForResult(intent, BREAKFAST_CODE);
                break;
            case R.id.add_meal_01:
                Intent intent1 = new Intent(getActivity(), SelectRecipeActivity.class);
                startActivityForResult(intent1, ADDMEAL_01_CODE);
                break;
            case R.id.add_lunch:
                Intent intent2 = new Intent(getActivity(), SelectRecipeActivity.class);
                startActivityForResult(intent2, LUNCH_CODE);
                break;
            case R.id.add_meal_02:
                Intent intent4 = new Intent(getActivity(), SelectRecipeActivity.class);
                startActivityForResult(intent4, ADDMEAL_02_CODE);
                break;
            case R.id.add_supper:
                Intent intent3 = new Intent(getActivity(), SelectRecipeActivity.class);
                startActivityForResult(intent3, SUPPER_CODE);
                break;
            case R.id.breakfast_nutrition:
                toggle("breakfast");
                break;
            case R.id.addmeal_01_nutrition:
                toggle("addmeal_01");
                break;
            case R.id.lunch_nutrition:
                toggle("lunch");
                break;
            case R.id.addmeal_02_nutrition:
                toggle("addmeal_02");
                break;
            case R.id.supper_nutrition:
                toggle("supper");
                break;
        }
    }


    public void toggle(String type) {
        Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
        if (type.equals("breakfast")) {
            ingredient_title.setText("早餐营养表");
            meal_pic.setImageResource(R.drawable.icon_breakfast);
            recipe_all_calorie.setText(Math.round(calorie_intake) + "kcal");
            calorie_radio.setText(Math.round(calorie_intake * 100 / report.getCaloriesIntake())+"%");
            double sum = protein_intake + fat_intake + carbohydrate_intake;
            int a = (int) Math.round(carbohydrate_intake * 100 / sum);
            int b = (int) Math.round(protein_intake * 100 / sum);
            int c = 100 - a - b;
            take_already_piechart.setValue(new float[]{a, b, c});
        }
        if(type.equals("addmeal_01")) {
            ingredient_title.setText("加餐营养表");
            meal_pic.setImageResource(R.drawable.icon_add_meal1);
            recipe_all_calorie.setText(Math.round(calorie_intake1) + "kcal");
            calorie_radio.setText(Math.round(calorie_intake1 * 100 / report.getCaloriesIntake())+"%");
        }
        if(type.equals("lunch")) {
            ingredient_title.setText("午餐营养表");
            meal_pic.setImageResource(R.drawable.icon_lunch);
            recipe_all_calorie.setText(Math.round(calorie_intake2) + "kcal");
            calorie_radio.setText(Math.round(calorie_intake2 * 100 / report.getCaloriesIntake())+"%");
        }
        if(type.equals("addmeal_02")) {
            ingredient_title.setText("加餐营养表");
            meal_pic.setImageResource(R.drawable.icon_add_meal2);
            recipe_all_calorie.setText(Math.round(calorie_intake3) + "kcal");
            calorie_radio.setText(Math.round(calorie_intake3 * 100 / report.getCaloriesIntake())+"%");
        }
        if(type.equals("supper")) {
            ingredient_title.setText("晚餐营养表");
            meal_pic.setImageResource(R.drawable.icon_dinner);
            recipe_all_calorie.setText(Math.round(calorie_intake4) + "kcal");
            calorie_radio.setText(Math.round(calorie_intake4 * 100 / report.getCaloriesIntake())+"%");
        }
        if(type.equals("all")) {
            ingredient_title.setText("营养表");
            nutrition_punch.setVisibility(View.VISIBLE);
            meal_pic.setImageResource(R.drawable.icon_dinner_temp);
            long total = Math.round(calorie_intake) + Math.round(calorie_intake1) + Math.round(calorie_intake2) + Math.round(calorie_intake3) +
                    Math.round(calorie_intake4);
            recipe_all_calorie.setText(total + "kcal");
            user_need_calorie.setText(report.getCaloriesIntake() + "kcal");
            calorie_radio.setText(Math.round(total * 100 / report.getCaloriesIntake())+"%");
        }else {
            nutrition_punch.setVisibility(View.GONE);
            user_need_calorie.setText(Math.round(report.getCaloriesIntake())+"kcal");
        }
        mRightMenu.toggle();
    }


    class RecipeAdapter extends BaseSwipeAdapter {

        Context context;
        List<Object> data;
        int code;

        public RecipeAdapter(Context context, List<Object> data, int code) {
            this.context = context;
            this.data = data;
            this.code = code;
        }

        @Override
        public int getSwipeLayoutResourceId(int i) {
            return R.id.swipe;
        }

        @Override
        public View generateView(int position, ViewGroup viewGroup) {
            View v = LayoutInflater.from(context).inflate(R.layout.plan_select_recipe_item, null);
            final SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
            swipeLayout.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {
                    YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
                }

            });
            return v;
        }

        @Override
        public void fillValues(final int i, final View view) {
            Log.v(i + "", view.toString());
            TextView text1 = (TextView) view.findViewById(R.id.textview1);
            TextView text2 = (TextView) view.findViewById(R.id.textview2);
            TextView text3 = (TextView) view.findViewById(R.id.textview3);
            Object obj = data.get(i);
            if(obj instanceof Recipe) {
                text1.setText(((Recipe)obj).getTitle());
                text2.setText(((Recipe)obj).getTotal_amount() + "g");
                text3.setText(Math.round(((Recipe)obj).getCalories() * ((Recipe)obj).getTotal_amount() / 100) + "kcal");
            }

            view.findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SwipeLayout)view).close();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            delete(i);
                        }
                    }, 500);
                    Toast.makeText(context, "click delete", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void delete(int i) {
            switch (code) {
                case BREAKFAST_CODE:
                    Recipe recipe = (Recipe) data.get(i);
                    carbohydrate_intake -= recipe.getNutrition_set().get(3).getAmount();
                    protein_intake -= recipe.getNutrition_set().get(1).getAmount();
                    fat_intake -= recipe.getNutrition_set().get(2).getAmount();
                    calorie_intake -= Math.round(recipe.getTotal_amount() * recipe.getCalories() / 100);
                    breakfast_protein_intake.setText(protein_intake + "");
                    breakfast_fat_intake.setText(fat_intake + "");
                    calorie_breakfast_intake.setText(Math.round(calorie_intake) + "");
                    breakfast_carbohydrate_intake.setText(Math.round(carbohydrate_intake) + "");
                    data.remove(i);
                    notifyDataSetChanged();
                    break;
                case ADDMEAL_01_CODE:
                    Recipe recipe1 = (Recipe) data.get(i);
                    carbohydrate_intake1 -= recipe1.getNutrition_set().get(3).getAmount();
                    protein_intake1 -= recipe1.getNutrition_set().get(1).getAmount();
                    fat_intake1 -= recipe1.getNutrition_set().get(2).getAmount();
                    calorie_intake1 -= Math.round(recipe1.getTotal_amount() * recipe1.getCalories() / 100);
                    addmeal_01_protein_intake.setText(protein_intake1 + "");
                    addmeal_01_fat_intake.setText(fat_intake1 + "");
                    calorie_addmeal_01_intake.setText(Math.round(calorie_intake1) + "");
                    addmeal_01_carbohydrate_intake.setText(Math.round(carbohydrate_intake1) + "");
                    data.remove(i);
                    notifyDataSetChanged();
                    break;
                case LUNCH_CODE:
                    Recipe recipe2 = (Recipe) data.get(i);
                    carbohydrate_intake2 -= recipe2.getNutrition_set().get(3).getAmount();
                    protein_intake2 -= recipe2.getNutrition_set().get(1).getAmount();
                    fat_intake2 -= recipe2.getNutrition_set().get(2).getAmount();
                    calorie_intake2 -= Math.round(recipe2.getTotal_amount() * recipe2.getCalories() / 100);
                    lunch_protein_intake.setText(protein_intake2 + "");
                    lunch_fat_intake.setText(fat_intake2 + "");
                    calorie_lunch_intake.setText(Math.round(calorie_intake2) + "");
                    lunch_carbohydrate_intake.setText(Math.round(carbohydrate_intake2) + "");
                    data.remove(i);
                    notifyDataSetChanged();
                    break;
                case ADDMEAL_02_CODE:
                    Recipe recipe3 = (Recipe) data.get(i);
                    carbohydrate_intake3 -= recipe3.getNutrition_set().get(3).getAmount();
                    protein_intake3 -= recipe3.getNutrition_set().get(1).getAmount();
                    fat_intake3 -= recipe3.getNutrition_set().get(2).getAmount();
                    calorie_intake3 -= Math.round(recipe3.getTotal_amount() * recipe3.getCalories() / 100);
                    addmeal_02_protein_intake.setText(protein_intake3 + "");
                    addmeal_02_fat_intake.setText(fat_intake3 + "");
                    calorie_addmeal_02_intake.setText(Math.round(calorie_intake3) + "");
                    addmeal_02_carbohydrate_intake.setText(Math.round(carbohydrate_intake3) + "");
                    data.remove(i);
                    notifyDataSetChanged();
                    break;
                case SUPPER_CODE:
                    Recipe recipe4 = (Recipe) data.get(i);
                    carbohydrate_intake4 -= recipe4.getNutrition_set().get(3).getAmount();
                    protein_intake4 -= recipe4.getNutrition_set().get(1).getAmount();
                    fat_intake4 -= recipe4.getNutrition_set().get(2).getAmount();
                    calorie_intake4 -= Math.round(recipe4.getTotal_amount() * recipe4.getCalories() / 100);
                    supper_protein_intake.setText(protein_intake4 + "");
                    supper_fat_intake.setText(fat_intake4 + "");
                    calorie_supper_intake.setText(Math.round(calorie_intake4) + "");
                    supper_carbohydrate_intake.setText(Math.round(carbohydrate_intake4) + "");
                    data.remove(i);
                    notifyDataSetChanged();
                    break;
            }
        }




        @Override
        public int getCount() {
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
    }

    class NutritionAdapter extends BaseAdapter {

        Context context;
        List<Nutrition> data;
        int code;

        public NutritionAdapter(Context context, List<Nutrition> data, int code) {
            this.context = context;
            this.data = data;
            this.code = code;
        }

        @Override
        public int getCount() {
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
            holder.textView2.setText("1000");
            holder.textView3.setText("1500");
            holder.textView4.setText();
        }

        class ViewHolder {
            TextView textView1;
            TextView textView2;
            TextView textView3;
            TextView textView4;
        }
    }
}
