package cn.fitrecipe.android.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.fitrecipe.android.R;
import cn.fitrecipe.android.SelectRecipeActivity;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.Recipe;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class SelectStageSecondFragment extends Fragment implements View.OnClickListener{
    private View view;
    private TextView search_pre,search_finish;
    private TextView recipe_title, recipe_weight, plan_num_dash, plan_num_sure;
    private TextView[] nums;
    private int[] ids = {R.id.plan_num_00, R.id.plan_num_01, R.id.plan_num_02, R.id.plan_num_03, R.id.plan_num_04, R.id.plan_num_05, R.id.plan_num_06,
            R.id.plan_num_07, R.id.plan_num_08,  R.id.plan_num_09};
    private StringBuilder weight;
    private PieChartView piechartview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_select_recipe_2, null);
        nums = new TextView[10];
        weight = new StringBuilder();
        initView();
        initEvent();

        return view;
    }

    private void initEvent() {
        search_pre.setOnClickListener(this);
        search_finish.setOnClickListener(this);
        for(int  i = 0; i < 10; i++) {
            final String tmp = String.valueOf(i);
            nums[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((weight.toString().length() == 0 || weight.toString().equals("0")) && tmp.equals("0")) {
                        weight.setLength(0);
                    } else {
                        if (weight.toString().length() < 4)
                            weight.append(tmp);
                        else
                            Toast.makeText(getActivity(), "重量不能超过10 000克!", Toast.LENGTH_SHORT).show();
                        recipe_weight.setText(weight.toString());
                    }

                }
            });
        }
        plan_num_dash.setOnClickListener(this);
        plan_num_sure.setOnClickListener(this);
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
        if(((SelectRecipeActivity)getActivity()).obj_selected instanceof Recipe) {
            Recipe recipe = ((Recipe) ((SelectRecipeActivity)getActivity()).obj_selected);
            recipe_title.setText(recipe.getTitle());
//                double sum = recipe.getNutrition_set().get(1).getAmount() + recipe.getNutrition_set().get(2).getAmount() + recipe.getNutrition_set().get(3).getAmount();
//                int a = (int) Math.round(recipe.getNutrition_set().get(3).getAmount() * 100 / sum);
//                int b = (int) Math.round(recipe.getNutrition_set().get(1).getAmount() * 100 / sum);
//                int c = 100 - a - b;
            // a 碳水 b 蛋白质 c 脂类
            int b = (int) Math.round(recipe.getProtein_ratio());
            int c = (int) Math.round(recipe.getFat_ratio());
            int a = 100 - b - c;
            piechartview.setValue(new float[]{a, b, c}, true, false, false);
        }else {
            Component component = (Component) ((SelectRecipeActivity)getActivity()).obj_selected;
            recipe_title.setText(component.getIngredient().getName());
            piechartview.setValue(new float[]{33, 33, 34}, true, false, false);
        }
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
                    PlanComponent component_selected = new PlanComponent();
                    int weight = Integer.parseInt(recipe_weight.getText().toString());
                    if(((SelectRecipeActivity)getActivity()).obj_selected instanceof Recipe) {
                        Recipe recipe = (Recipe) ((SelectRecipeActivity)getActivity()).obj_selected;
                        component_selected.setAmount(weight);
                        component_selected.setName(recipe.getTitle());
                        component_selected.setType(1);
                        component_selected.setId(recipe.getId());
                        for(int i = 0; i < recipe.getNutrition_set().size(); i++)
                            recipe.getNutrition_set().get(i).setAmount(recipe.getNutrition_set().get(i).getAmount() * weight / 100);
                        ArrayList<PlanComponent> components = new ArrayList<>();
                        for(int i = 0; i < recipe.getComponent_set().size(); i++) {
                            PlanComponent component = new PlanComponent();
                            component.setName(recipe.getComponent_set().get(i).getIngredient().getName());
                            component.setType(0);
                            component.setAmount(Math.round(recipe.getComponent_set().get(i).getAmount() * weight * 1.0f/ recipe.getTotal_amount()));
                            component.setCalories(100);
                            components.add(component);
                        }
                        component_selected.setComponents(components);
                        component_selected.setNutritions(recipe.getNutrition_set());
                        component_selected.setCalories(recipe.getCalories() * weight / 100);

                    }else {
                        Component component = (Component) ((SelectRecipeActivity)getActivity()).obj_selected;
                        component_selected.setName(component.getIngredient().getName());
                        component_selected.setType(0);
                        component_selected.setAmount(weight);
                        component_selected.setCalories(100);
                        //TODO @wk
                    }
                    intent.putExtra("component_selected", component_selected);
                    ((SelectRecipeActivity)getActivity()).setResult(getActivity().RESULT_OK, intent);
                    ((SelectRecipeActivity)getActivity()).finish();
                }else
                    Toast.makeText(getActivity(), "重量不能为0", Toast.LENGTH_SHORT).show();
                break;
            case R.id.plan_num_dash:
                recipe_weight.setText("0");
                weight.setLength(0);
                break;
            case R.id.plan_num_sure:
                if(weight.toString().length() > 0) {
                    weight.deleteCharAt(weight.toString().length() - 1);
                    if(weight.toString().length() == 0)
                        recipe_weight.setText("0");
                    else
                        recipe_weight.setText(weight.toString());
                }
                break;
        }
    }
}