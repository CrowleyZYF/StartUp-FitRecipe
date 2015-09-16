package cn.fitrecipe.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.BasketAdapter;
import cn.fitrecipe.android.Adpater.IngredientAdapter;
import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.Basket;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.Ingredient;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.fragment.PlanFragment;

public class IngredientActivity extends Activity implements View.OnClickListener{

    private Fragment fragment1, fragment2;
    private FragmentTransaction transaction;

    private LinearLayout shoppingFoodBtn;
    private ImageView shoppingFoodLine1;
    private ImageView shoppingFoodLine2;
    private LinearLayout shoppingRecipeBtn;
    private ImageView shoppingRecipeLine1;
    private ImageView shoppingRecipeLine2;

    private ImageView backBtn;
    private TextView clearBtn;
    private List<PlanComponent> data;
    private Basket basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_container);

        initView();
        initEvent();
        initData();
        setFragment(0);
    }

    private void initData() {
        basket = FrDbHelper.getInstance(this).getBasket();
        if(basket == null)  basket = new Basket();
        data = basket.getContent();
        if(data == null) data = new ArrayList<>();
        Collections.sort(data);
    }

    private void setFragment(int i) {
        transaction = getFragmentManager().beginTransaction();
        if(fragment1 == null) {
            fragment1 = new Fragment1();
            transaction.add(R.id.fragment_container, fragment1);
        }
        if(fragment2 == null) {
            fragment2 = new Fragment2();
            transaction.add(R.id.fragment_container, fragment2);
        }

        if(i == 0)
            transaction.hide(fragment2).show(fragment1);
        if(i == 1)
            transaction.hide(fragment1).show(fragment2);
        transaction.commit();
    }

    private void initEvent() {
        shoppingFoodBtn.setOnClickListener(this);
        shoppingRecipeBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
    }

    private void initView() {
        shoppingFoodBtn = (LinearLayout) findViewById(R.id.shopping_food);
        shoppingFoodLine1 = (ImageView) findViewById(R.id.shopping_food_active_line_1);
        shoppingFoodLine2 = (ImageView) findViewById(R.id.shopping_food_active_line_2);
        shoppingRecipeBtn = (LinearLayout) findViewById(R.id.shopping_recipe);
        shoppingRecipeLine1 = (ImageView) findViewById(R.id.shopping_recipe_active_line_1);
        shoppingRecipeLine2 = (ImageView) findViewById(R.id.shopping_recipe_active_line_2);

        backBtn = (ImageView) findViewById(R.id.back_btn);
        clearBtn = (TextView) findViewById(R.id.shopping_clear);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shopping_food:
                shoppingFoodLine1.setVisibility(View.VISIBLE);
                shoppingFoodLine2.setBackground(getResources().getDrawable(R.drawable.theme_line_active));
                shoppingRecipeLine1.setVisibility(View.INVISIBLE);
                shoppingRecipeLine2.setBackground(getResources().getDrawable(R.drawable.theme_line));
                setFragment(0);
                break;
            case R.id.shopping_recipe:
                shoppingRecipeLine1.setVisibility(View.VISIBLE);
                shoppingRecipeLine2.setBackground(getResources().getDrawable(R.drawable.theme_line_active));
                shoppingFoodLine1.setVisibility(View.INVISIBLE);
                shoppingFoodLine2.setBackground(getResources().getDrawable(R.drawable.theme_line));
                setFragment(1);
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.shopping_clear:
                FrDbHelper.getInstance(this).clearBasket();
                data.clear();
                PlanFragment.isFresh = true;
                ((Fragment1) fragment1).fresh();
                ((Fragment2)fragment2).fresh();
                break;
        }
    }


    @Override
    protected void onPause() {
        basket.setContent(data);
        FrDbHelper.getInstance(this).saveBasket(basket);
        super.onPause();
    }

    public class Fragment1 extends Fragment {

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
            for(int i = 0; i < data.size(); i++) {
                PlanComponent planComponent = data.get(i);
                if(planComponent.getType() == 1) {
                    List<PlanComponent> components = planComponent.getComponents();
                    int original = 0;
                    for(int j = 0; j < components.size(); j++)  original += components.get(j).getAmount();
                    for(int j = 0; j < components.size(); j++) {
                        String name = components.get(j).getName();
                        if (counter.containsKey(name)) {
                            PlanComponent component = counter.get(name);
                            component.setType(0);
                            component.setAmount(component.getAmount() + components.get(j).getAmount() * planComponent.getAmount() / original);
                            int status = component.getStatus();
                            component.setStatus(status & components.get(j).getStatus());
                        }
                        else {
                            PlanComponent component = new PlanComponent();
                            component.setName(components.get(j).getName());
                            component.setType(0);
                            component.setAmount(components.get(j).getAmount() * planComponent.getAmount() / original);
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
            ingredientAdapter = new IngredientAdapter(getActivity(), datalist, data);
            ingredient_list.setAdapter(ingredientAdapter);
        }

        private void fresh() {
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

    public class Fragment2 extends Fragment {

        LinearLayoutForListView recipe_list_view;
        BasketAdapter basketAdapter;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = View.inflate(getActivity(), R.layout.fragment_ingredient_2, null);
            initView(v);
            return v;
        }

        private void initView(View v) {
            recipe_list_view = (LinearLayoutForListView) v.findViewById(R.id.recipe_list_view);
            basketAdapter = new BasketAdapter(getActivity(), data);
            recipe_list_view.setAdapter(basketAdapter);
        }

        public void fresh() {
            basketAdapter.notifyDataSetChanged();
        }

        @Override
        public void onHiddenChanged(boolean hidden) {
            if(!hidden)
                fresh();
            super.onHiddenChanged(hidden);
        }
    }
}
