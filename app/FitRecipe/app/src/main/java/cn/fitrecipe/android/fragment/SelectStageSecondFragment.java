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
    private TextView recipe_title, recipe_weight, plan_num_dash, plan_num_sure, sub, add;
    private double weight;
    private PieChartView piechartview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_select_recipe_2, null);
        weight = 0;
        initView();
        initEvent();

        return view;
    }

    private void initEvent() {
        search_pre.setOnClickListener(this);
        search_finish.setOnClickListener(this);
        add.setOnClickListener(this);
        sub.setOnClickListener(this);
        plan_num_dash.setOnClickListener(this);
        plan_num_sure.setOnClickListener(this);
    }

    private void initView() {
        search_finish = (TextView) view.findViewById(R.id.search_finish);
        search_pre = (TextView) view.findViewById(R.id.search_pre);
        recipe_title = (TextView) view.findViewById(R.id.recipe_title);
        recipe_weight = (TextView) view.findViewById(R.id.recipe_weight);
        plan_num_dash = (TextView) view.findViewById(R.id.plan_num_dash);
        plan_num_sure = (TextView) view.findViewById(R.id.plan_num_sure);
        piechartview = (PieChartView) view.findViewById(R.id.piechartview);
        add = (TextView) view.findViewById(R.id.add);
        sub = (TextView) view.findViewById(R.id.sub);
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
                    if(((SelectRecipeActivity)getActivity()).obj_selected instanceof Recipe) {
                        Recipe recipe = (Recipe) ((SelectRecipeActivity)getActivity()).obj_selected;
                        component_selected.setAmount((int) Math.round(weight * recipe.getTotal_amount()));
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
                            component.setAmount(Math.round(recipe.getComponent_set().get(i).getAmount() * weight);
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
            case R.id.add:
                weight += 0.25;
                int tmp = (int)(weight * 4);
                recipe_weight.setText(tmp % 4 == 0?String.valueOf(tmp/4):tmp +"/4");
                break;
            case R.id.sub:
                if(weight > 0) {
                    weight -= 0.25;
                    int tmp1 = (int) (weight * 4);
                    recipe_weight.setText(tmp1 % 4 == 0 ? String.valueOf(tmp1 / 4) : tmp1 + "/4");
                }
                break;
        }
    }
}