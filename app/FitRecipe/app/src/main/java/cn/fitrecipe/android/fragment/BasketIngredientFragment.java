package cn.fitrecipe.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.IngredientAdapter;
import cn.fitrecipe.android.IngredientActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.entity.PlanComponent;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class BasketIngredientFragment extends android.app.Fragment {

    ListView ingredient_list;
    IngredientAdapter ingredientAdapter;
    List<PlanComponent> datalist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = View.inflate(getActivity(), R.layout.fragment_ingredient_1, null);
        getData();
        initView(v);
        return v;
    }

    private void getData() {
        if(datalist == null)
            datalist = new ArrayList<>();
        else
            datalist.clear();
        Map<String, PlanComponent> counter = new HashMap<>();
        for(int i = 0; i < ((IngredientActivity) getActivity()).getData().size(); i++) {
            PlanComponent planComponent = ((IngredientActivity) getActivity()).getData().get(i);
            if(planComponent.getType() == 1) {
                List<PlanComponent> components = planComponent.getComponents();
                int total = 0;
                for(int j =0 ; j < components.size(); j++)  total += components.get(j).getAmount();
                for(int j = 0; j < components.size(); j++) {
                    String name = components.get(j).getName();
                    if (counter.containsKey(name)) {
                        PlanComponent component = counter.get(name);
                        component.setType(0);
                        component.setAmount(component.getAmount() + planComponent.getAmount() * components.get(j).getAmount() / total);
                        int status = component.getStatus();
                        component.setStatus(status & components.get(j).getStatus());
                    }
                    else {
                        PlanComponent component = new PlanComponent();
                        component.setName(components.get(j).getName());
                        component.setType(0);
                        component.setAmount(planComponent.getAmount() * components.get(j).getAmount() / total);
                        component.setStatus(components.get(j).getStatus());
                        counter.put(name, component);
                    }
                }
            }else {
                String name = planComponent.getName();
                if (counter.containsKey(name)) {
                    PlanComponent component = counter.get(name);
                    component.setType(0);
                    component.setAmount(component.getAmount() + planComponent.getAmount());
                    int status = component.getStatus();
                    component.setStatus(status & planComponent.getStatus());
                }
                else {
                    PlanComponent component = new PlanComponent();
                    component.setName(planComponent.getName());
                    component.setType(0);
                    component.setAmount(planComponent.getAmount());
                    component.setStatus(planComponent.getStatus());
                    counter.put(name, component);
                }
            }

        }
        Iterator<String> iterator = counter.keySet().iterator();
        while(iterator.hasNext()) {
            String name = iterator.next();
            PlanComponent component = counter.get(name);
            datalist.add(component);
        }
    }

    private void initView(View v) {
        ingredient_list = (ListView) v.findViewById(R.id.ingredient_list);
        ingredientAdapter = new IngredientAdapter(getActivity(), datalist, ((IngredientActivity) getActivity()).getData());
        ingredient_list.setAdapter(ingredientAdapter);
    }

    public void fresh() {
        getData();
        ingredientAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden)
            fresh();
        super.onHiddenChanged(hidden);
    }
}