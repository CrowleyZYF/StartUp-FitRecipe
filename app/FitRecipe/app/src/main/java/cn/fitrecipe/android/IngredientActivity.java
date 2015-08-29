package cn.fitrecipe.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import cn.fitrecipe.android.entity.Collection;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.Ingredient;
import cn.fitrecipe.android.entity.Recipe;

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


    private List<Recipe> basket;


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
        Collections.sort(basket);
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
                ((Fragment1)fragment1).fresh();
                ((Fragment2)fragment2).fresh();
                FrDbHelper.getInstance(this).clearBasket(basket);
                basket.clear();
                break;
        }
    }


    @Override
    protected void onStop() {
        FrDbHelper.getInstance(this).saveBasket(basket);
        super.onStop();
    }

    class Fragment1 extends Fragment {

        ListView ingredient_list;
        IngredientAdapter ingredientAdapter;
        List<Component> datalist;
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
            Map<String, Component> counter = new HashMap<String, Component>();
            for(int i = 0; i < basket.size(); i++) {
                Recipe recipe = basket.get(i);
                List<Component> components = recipe.getComponent_set();
                for(int j = 0; j < components.size(); j++) {
                    String name = components.get(j).getIngredient().getName();
                    if (counter.containsKey(name)) {
                        Component component = counter.get(name);
                        component.setMAmount(component.getMAmount() + components.get(j).getMAmount());
                        int status = component.getStatus();
                        component.setStatus(status & components.get(j).getStatus());
                    }
                    else {
                        Component component = new Component();
                        component.setMAmount(components.get(j).getMAmount());
                        Ingredient newIngredient = new Ingredient();
                        newIngredient.setId(components.get(j).getIngredient().getId());
                        newIngredient.setName(components.get(j).getIngredient().getName());
                        component.setStatus(components.get(j).getStatus());
                        component.setIngredient(newIngredient);
                        counter.put(name, component);
                    }
                }
            }
            Iterator<String> iterator = counter.keySet().iterator();
            while(iterator.hasNext()) {
                String name = iterator.next();
                Component component = counter.get(name);
                datalist.add(component);
            }
        }

        private void initView(View v) {
            ingredient_list = (ListView) v.findViewById(R.id.ingredient_list);
            ingredientAdapter = new IngredientAdapter(getActivity(), datalist, basket);
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

    class Fragment2 extends Fragment {

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
            basketAdapter = new BasketAdapter(getActivity(), basket);
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
