package cn.fitrecipe.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.Basket;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.fragment.BasketIngredientFragment;
import cn.fitrecipe.android.fragment.BasketRecipeFragment;
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
        ArrayList<PlanComponent> components = new ArrayList<>();
        for(int i = 0; i < data.size(); ) {
            if(data.get(i).getType() == 0) {
                components.add(data.get(i));
                data.remove(i);
            }else
                i++;
        }
        if(components.size() > 0) {
            PlanComponent component = new PlanComponent();
            component.setName("其它");
            component.setType(1);
            component.setComponents(components);
            data.add(component);
        }
    }

    private void setFragment(int i) {
        transaction = getFragmentManager().beginTransaction();
        if(fragment1 == null) {
            fragment1 = new BasketIngredientFragment();
            transaction.add(R.id.fragment_container, fragment1);
        }
        if(fragment2 == null) {
            fragment2 = new BasketRecipeFragment();
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
                ((BasketIngredientFragment) fragment1).fresh();
                ((BasketRecipeFragment) fragment2).fresh();
                break;
        }
    }


    @Override
    protected void onPause() {
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).getName().equals("其它")) {
                ArrayList<PlanComponent> components = data.get(i).getComponents();
                for(int j = 0; j <components.size(); j++)
                    data.add(components.get(j));
                data.remove(i);
            }
        }
        basket.setContent(data);
        FrDbHelper.getInstance(this).saveBasket(basket);
        super.onPause();
    }

    public List<PlanComponent> getData(){
        return data;
    }
}
