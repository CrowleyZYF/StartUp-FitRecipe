package cn.fitrecipe.android.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.SelectRecipeActivity;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.SlidingPage;
import cn.fitrecipe.android.entity.Recipe;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class PlanFragment extends Fragment implements View.OnClickListener {

    private SlidingPage mRightMenu;

    //breakfast
    private final int BREAKFAST_CODE = 01;
    private RelativeLayout add_breakfast;
    private LinearLayoutForListView breakfast_content;
    private List<Recipe> breakfast_recipes;
    private RecipeAdapter breakfast_adapter;
    private TextView breakfast_carbohydrate_intake, breakfast_protein_intake, breakfast_fat_intake, calorie_breakfast_intake;
    private double carbohydrate_intake, protein_intake, fat_intake, calorie_intake;

    //add 1
    private RelativeLayout add_meal_01;
    private final int ADDMEAL_01_CODE = 02;
    private LinearLayoutForListView addmeal_01_content;
    private List<Recipe> add_1_recipes;
    private RecipeAdapter add_1_adapter;
    private TextView addmeal_01_carbohydrate_intake, addmeal_01_protein_intake, addmeal_01_fat_intake, calorie_addmeal_01_intake;
    private double carbohydrate_intake1, protein_intake1, fat_intake1, calorie_intake1;

    //lunch
    private final int LUNCH_CODE = 03;
    private RelativeLayout add_lunch;
    private LinearLayoutForListView lunch_content;
    private List<Recipe> lunch_recipes;
    private RecipeAdapter lunch_adapter;
    private TextView lunch_carbohydrate_intake, lunch_protein_intake, lunch_fat_intake, calorie_lunch_intake;
    private double carbohydrate_intake2, protein_intake2, fat_intake2, calorie_intake2;

    // add 2
    private RelativeLayout add_meal_02;
    private final int ADDMEAL_02_CODE = 04;
    private LinearLayoutForListView addmeal_02_content;
    private List<Recipe> add_2_recipes;
    private RecipeAdapter add_2_adapter;
    private TextView addmeal_02_carbohydrate_intake, addmeal_02_protein_intake, addmeal_02_fat_intake, calorie_addmeal_02_intake;
    private double carbohydrate_intake3, protein_intake3, fat_intake3, calorie_intake3;


    //supper
    private final int SUPPER_CODE = 05;
    private RelativeLayout add_supper;
    private LinearLayoutForListView supper_content;
    private List<Recipe> supper_recipes;
    private RecipeAdapter supper_adapter;
    private TextView supper_carbohydrate_intake, supper_protein_intake, supper_fat_intake, calorie_supper_intake;
    private double carbohydrate_intake4, protein_intake4, fat_intake4, calorie_intake4;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        initView(v);
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


        //add 1
        add_meal_01 = (RelativeLayout) v.findViewById(R.id.add_meal_01);
        addmeal_01_content = (LinearLayoutForListView) v.findViewById(R.id.addmeal_01_content);
        addmeal_01_carbohydrate_intake = (TextView) v.findViewById(R.id.addmeal_01_carbohydrate_intake);
        addmeal_01_protein_intake = (TextView) v.findViewById(R.id.addmeal_01_protein_intake);
        addmeal_01_fat_intake = (TextView) v.findViewById(R.id.addmeal_01_fat_intake);
        calorie_addmeal_01_intake = (TextView) v.findViewById(R.id.calorie_addmeal_01_intake);


        //lunch
        add_lunch = (RelativeLayout) v.findViewById(R.id.add_lunch);
        lunch_content = (LinearLayoutForListView) v.findViewById(R.id.lunch_content);
        lunch_carbohydrate_intake = (TextView) v.findViewById(R.id.lunch_carbohydrate_intake);
        lunch_protein_intake = (TextView) v.findViewById(R.id.lunch_protein_intake);
        lunch_fat_intake = (TextView) v.findViewById(R.id.lunch_fat_intake);
        calorie_lunch_intake = (TextView) v.findViewById(R.id.calorie_lunch_intake);


        //add 2
        add_meal_02 = (RelativeLayout) v.findViewById(R.id.add_meal_02);
        addmeal_02_content = (LinearLayoutForListView) v.findViewById(R.id.addmeal_02_content);
        addmeal_02_carbohydrate_intake = (TextView) v.findViewById(R.id.addmeal_02_carbohydrate_intake);
        addmeal_02_protein_intake = (TextView) v.findViewById(R.id.addmeal_02_protein_intake);
        addmeal_02_fat_intake = (TextView) v.findViewById(R.id.addmeal_02_fat_intake);
        calorie_addmeal_02_intake = (TextView) v.findViewById(R.id.calorie_addmeal_02_intake);


        //supper
        add_supper = (RelativeLayout) v.findViewById(R.id.add_supper);
        supper_content = (LinearLayoutForListView) v.findViewById(R.id.supper_content);
        supper_carbohydrate_intake = (TextView) v.findViewById(R.id.supper_carbohydrate_intake);
        supper_protein_intake = (TextView) v.findViewById(R.id.supper_protein_intake);
        supper_fat_intake = (TextView) v.findViewById(R.id.supper_fat_intake);
        calorie_supper_intake = (TextView) v.findViewById(R.id.calorie_supper_intake);

        mRightMenu = (SlidingPage) v.findViewById(R.id.filter_menu);


    }

    private void initData() {
        //breakfast
        if(breakfast_recipes == null) {
            breakfast_recipes = new ArrayList<>();
        }
        breakfast_adapter = new RecipeAdapter(getActivity(), breakfast_recipes);
        breakfast_content.setAdapter(breakfast_adapter);
        carbohydrate_intake = 0;
        protein_intake = 0;
        fat_intake = 0;
        calorie_intake = 0;

        //add 1
        if(add_1_recipes == null) {
            add_1_recipes = new ArrayList<>();
        }
        add_1_adapter = new RecipeAdapter(getActivity(), add_1_recipes);
        addmeal_01_content.setAdapter(add_1_adapter);
        carbohydrate_intake1 = 0;
        protein_intake1 = 0;
        fat_intake1 = 0;
        calorie_intake1 = 0;

        //lunch
        if(lunch_recipes == null) {
            lunch_recipes = new ArrayList<>();
        }
        lunch_adapter = new RecipeAdapter(getActivity(), lunch_recipes);
        lunch_content.setAdapter(lunch_adapter);
        carbohydrate_intake2 = 0;
        protein_intake2 = 0;
        fat_intake2 = 0;
        calorie_intake2 = 0;

        //add 2
        if(add_2_recipes == null) {
            add_2_recipes = new ArrayList<>();
        }
        add_2_adapter = new RecipeAdapter(getActivity(), add_2_recipes);
        addmeal_02_content.setAdapter(add_2_adapter);
        carbohydrate_intake3 = 0;
        protein_intake3 = 0;
        fat_intake3 = 0;
        calorie_intake3 = 0;

        //supper
        if(supper_recipes == null) {
            supper_recipes = new ArrayList<>();
        }
        supper_adapter = new RecipeAdapter(getActivity(), supper_recipes);
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
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BREAKFAST_CODE:
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("recipe_selected")) {
                    Recipe recipe = (Recipe) data.getSerializableExtra("recipe_selected");
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
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("recipe_selected")) {
                    Recipe recipe = (Recipe) data.getSerializableExtra("recipe_selected");
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
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("recipe_selected")) {
                    Recipe recipe = (Recipe) data.getSerializableExtra("recipe_selected");
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
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("recipe_selected")) {
                    Recipe recipe = (Recipe) data.getSerializableExtra("recipe_selected");
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
                if(resultCode == getActivity().RESULT_OK && data.hasExtra("recipe_selected")) {
                    Recipe recipe = (Recipe) data.getSerializableExtra("recipe_selected");
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
        }
    }

//    class RecipeAdapter1 extends BaseSwipeAdapter {
//
//        Context context;
//        List<Recipe> data;
//
//        public RecipeAdapter(Context context, List<Recipe> data) {
//            this.context = context;
//            this.data = data;
//        }
//
//        @Override
//        public int getCount() {
//            return data.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return data.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            if (convertView != null) {
//                holder = (ViewHolder) convertView.getTag();
//            } else {
//                convertView = View.inflate(context, R.layout.plan_select_recipe_item, null);
//                holder = new ViewHolder();
//                holder.textview1 = (TextView) convertView.findViewById(R.id.textview1);
//                holder.textview2 = (TextView) convertView.findViewById(R.id.textview2);
//                holder.textview3 = (TextView) convertView.findViewById(R.id.textview3);
//                convertView.setTag(holder);
//            }
//            holder.textview1.setText(data.get(position).getTitle());
//            holder.textview2.setText(data.get(position).getTotal_amount() + "g");
//            holder.textview3.setText(Math.round(data.get(position).getTotal_amount() * data.get(position).getCalories() / 100) + "kcal");
//            return convertView;
//        }
//
//        class ViewHolder {
//            TextView textview1;
//            TextView textview2;
//            TextView textview3;
//        }
//    }

    class RecipeAdapter extends BaseSwipeAdapter {

        Context context;
        List<Recipe> data;

        public RecipeAdapter(Context context, List<Recipe> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getSwipeLayoutResourceId(int i) {
            return R.id.swipe;
        }

        @Override
        public View generateView(final int position, ViewGroup viewGroup) {
            View v = LayoutInflater.from(context).inflate(R.layout.plan_select_recipe_item, null);
            SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
            swipeLayout.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {
                    YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
                }
            });

            v.findViewById(R.id.trash).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "click delete", Toast.LENGTH_SHORT).show();
                }
            });
            return v;
        }

        @Override
        public void fillValues(int i, View view) {
            TextView text1 = (TextView) view.findViewById(R.id.textview1);
            text1.setText(data.get(i).getTitle());
            TextView text2 = (TextView) view.findViewById(R.id.textview2);
            text2.setText(data.get(i).getTotal_amount()+"g");
            TextView text3 = (TextView) view.findViewById(R.id.textview3);
            text3.setText(Math.round(data.get(i).getCalories() * data.get(i).getTotal_amount() / 100) + "kcal");
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
}
