package cn.fitrecipe.android.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.SelectRecipeActivity;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.entity.PlanComponent;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class SelectStageSecondFragment extends Fragment implements View.OnClickListener{
    private View view;
    private TextView search_pre,search_finish;
    private TextView recipe_title, recipe_weight, plan_num_dash, plan_num_sure, add, sub, unit;
    private TextView[] nums;
    private int[] ids = {R.id.plan_num_00, R.id.plan_num_01, R.id.plan_num_02, R.id.plan_num_03, R.id.plan_num_04, R.id.plan_num_05, R.id.plan_num_06,
            R.id.plan_num_07, R.id.plan_num_08,  R.id.plan_num_09};
    private StringBuilder weight;       //component weight
    private float weight2;             //recipe weight
    private PieChartView piechartview;
    private LinearLayout food_adjust, recipe_adjust;
    private TextView calorie_data_text, protein_data_text, carbohydrate_data_text, lipids_data_text, recipe_amount, recipe_amount_unit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_select_recipe_2, null);
        nums = new TextView[10];
        weight = new StringBuilder();
        weight2 = 1;
        initView();
        initEvent();

        return view;
    }

    private void initEvent() {
        search_pre.setOnClickListener(this);
        search_finish.setOnClickListener(this);
        add.setOnClickListener(this);
        sub.setOnClickListener(this);
        for(int  i = 0; i < 10; i++) {
            final String tmp = String.valueOf(i);
            nums[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((weight.toString().length() == 0 || weight.toString().equals("0")) && tmp.equals("0")) {
                        weight.setLength(0);
                    } else {
                        if (weight.toString().length() < 4) {
                            weight.append(tmp);
                            recipe_weight.setText(weight.toString());
                            update();
                        } else
                            Toast.makeText(getActivity(), "重量不能超过10 000克!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        plan_num_dash.setOnClickListener(this);
        plan_num_sure.setOnClickListener(this);
    }

    private void update() {
        int weight = Integer.parseInt(recipe_weight.getText().toString());
        PlanComponent component = ((SelectRecipeActivity)getActivity()).obj_selected;
        calorie_data_text.setText(Math.round(component.getCalories() * weight / 100) + " kcal");
        protein_data_text.setText(String.format("%.2f g", component.getNutritions().get(1).getAmount() * weight / 100));
        lipids_data_text.setText(String.format("%.2f g", component.getNutritions().get(2).getAmount() * weight / 100));
        carbohydrate_data_text.setText(String.format("%.2f g", component.getNutritions().get(3).getAmount() * weight / 100));
    }

    private void update2() {
        PlanComponent component = ((SelectRecipeActivity)getActivity()).obj_selected;
        recipe_amount.setText(Math.round(component.getAmount() * weight2) + "");
        calorie_data_text.setText(Math.round(component.getCalories() * component.getAmount() * weight2 / 100)+" kcal");
        protein_data_text.setText(String.format("%.2f g", component.getNutritions().get(1).getAmount() * component.getAmount() * weight2 / 100));
        lipids_data_text.setText(String.format("%.2f g", component.getNutritions().get(2).getAmount() * component.getAmount() * weight2 / 100));
        carbohydrate_data_text.setText(String.format("%.2f g", component.getNutritions().get(3).getAmount() * component.getAmount() * weight2 / 100));
    }

    private void initView() {
        for(int i = 0; i < 10; i++) {
            nums[i] = (TextView) view.findViewById(ids[i]);
        }
        search_finish = (TextView) view.findViewById(R.id.search_finish);
        search_pre = (TextView) view.findViewById(R.id.search_pre);
        recipe_title = (TextView) view.findViewById(R.id.recipe_title);
        recipe_weight = (TextView) view.findViewById(R.id.recipe_weight);
        plan_num_dash = (TextView) view.findViewById(R.id.plan_num_dash);
        plan_num_sure = (TextView) view.findViewById(R.id.plan_num_sure);
        piechartview = (PieChartView) view.findViewById(R.id.piechartview);
        food_adjust = (LinearLayout) view.findViewById(R.id.food_adjust);
        recipe_adjust = (LinearLayout) view.findViewById(R.id.recipe_adjust);
        add = (TextView) view.findViewById(R.id.add);
        sub = (TextView) view.findViewById(R.id.sub);
        unit = (TextView) view.findViewById(R.id.unit);
        calorie_data_text = (TextView) view.findViewById(R.id.calorie_data_text);
        protein_data_text = (TextView) view.findViewById(R.id.protein_data_text);
        carbohydrate_data_text = (TextView) view.findViewById(R.id.carbohydrate_data_text);
        lipids_data_text = (TextView) view.findViewById(R.id.lipids_data_text);
        recipe_amount = (TextView) view.findViewById(R.id.recipe_amount);
        recipe_amount_unit = (TextView) view.findViewById(R.id.recipe_amount_unit);

        fresh();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden) {
            fresh();
        }
        super.onHiddenChanged(hidden);
    }

    public void fresh() {
        PlanComponent component = ((SelectRecipeActivity)getActivity()).obj_selected;
        if(component.getType() == 1) {
            food_adjust.setVisibility(View.GONE);
            recipe_amount.setVisibility(View.VISIBLE);
            recipe_adjust.setVisibility(View.VISIBLE);
            recipe_amount_unit.setVisibility(View.VISIBLE);
            recipe_weight.setText(1 + "");
            unit.setText(getResources().getString(R.string.search_recipe_unit));
            update2();
        }else {
            recipe_weight.setText("0");
            food_adjust.setVisibility(View.VISIBLE);
            recipe_adjust.setVisibility(View.GONE);
            recipe_amount.setVisibility(View.GONE);
            recipe_amount_unit.setVisibility(View.GONE);
            unit.setText(getResources().getString(R.string.search_food_unit));
        }
        recipe_title.setText(component.getName());
        double sum = component.getNutritions().get(1).getAmount() + component.getNutritions().get(2).getAmount() + component.getNutritions().get(3).getAmount();
        int a = (int) Math.round(component.getNutritions().get(3).getAmount() * 100 / sum);
        int b = (int) Math.round(component.getNutritions().get(1).getAmount() * 100 / sum);
        int c = 100 - a - b;
        // a 碳水 b 蛋白质 c 脂类
        piechartview.setValue(new float[]{a, b, c}, true, false, false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_pre:
                ((SelectRecipeActivity)getActivity()).setFragment(0);
                break;
            case R.id.search_finish:
                String w = recipe_weight.getText().toString();
                if(!w.equals("0")) {
                    Intent intent = new Intent();
                    PlanComponent component_selected = ((SelectRecipeActivity)getActivity()).obj_selected;
                    if(component_selected.getType() == 1) {
                        component_selected.setAmount(Math.round(component_selected.getAmount() * weight2));
                        ArrayList<PlanComponent> components = component_selected.getComponents();
                        for(int i = 0; i < components.size(); i++) {
                            components.get(i).setAmount(Math.round(components.get(i).getAmount() * weight2));
                        }
                    }else {
                        int weight = Integer.parseInt(recipe_weight.getText().toString());
                        component_selected.setAmount(weight);
                    }
                    intent.putExtra("component_selected", component_selected);
                    getActivity().setResult(getActivity().RESULT_OK, intent);
                    getActivity().finish();
                }else
                    Toast.makeText(getActivity(), "重量不能为0", Toast.LENGTH_SHORT).show();
                break;
            case R.id.plan_num_dash:
                recipe_weight.setText("0");
                weight.setLength(0);
                calorie_data_text.setText("0 kcal");
                protein_data_text.setText("0.00g");
                lipids_data_text.setText("0.00g");
                carbohydrate_data_text.setText("0.00g");
                break;
            case R.id.plan_num_sure:
                if(weight.toString().length() > 0) {
                    weight.deleteCharAt(weight.toString().length() - 1);
                    if(weight.toString().length() == 0)
                        recipe_weight.setText("0");
                    else
                        recipe_weight.setText(weight.toString());
                    update();
                }
                break;
            case R.id.add:
                /*weight2 += 0.25;
                int tmp = (int)(weight2 * 4);
                if(tmp % 4 == 0)
                    recipe_weight.setText(String.valueOf(tmp/4));
                else
                    recipe_weight.setText(String.valueOf(weight2));*/
                weight2 += 0.5;
                int tmp = (int)(weight2 * 2);
                if(tmp % 2 == 0)
                    recipe_weight.setText(String.valueOf(tmp/2));
                else
                    recipe_weight.setText(String.valueOf(weight2));
                update2();
                break;
            case R.id.sub:
                /*if(weight2 > 0) {
                    weight2 -= 0.25;
                    tmp = (int)(weight2 * 4);
                    if(tmp % 4 == 0)
                        recipe_weight.setText(String.valueOf(tmp/4));
                    else
                        recipe_weight.setText(String.valueOf(weight2));
                }*/
                if(weight2 > 0) {
                    weight2 -= 0.5;
                    tmp = (int)(weight2 * 2);
                    if(tmp % 2 == 0)
                        recipe_weight.setText(String.valueOf(tmp/2));
                    else
                        recipe_weight.setText(String.valueOf(weight2));
                }
                update2();
                break;

        }
    }
}