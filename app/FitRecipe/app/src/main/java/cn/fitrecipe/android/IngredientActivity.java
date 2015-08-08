package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.ShoppingAdapter;
import cn.fitrecipe.android.entity.Component;
import cn.fitrecipe.android.entity.Ingredient;
import cn.fitrecipe.android.entity.Recipe;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class IngredientActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView listView;
    private ShoppingAdapter shopping_adapter;
    private List<Map<String, Object>> dataList;
    private List<Recipe> recipes;

    private LinearLayout shoppingFoodBtn;
    private ImageView shoppingFoodLine1;
    private ImageView shoppingFoodLine2;
    private LinearLayout shoppingRecipeBtn;
    private ImageView shoppingRecipeLine1;
    private ImageView shoppingRecipeLine2;

    private ImageView backBtn;
    private TextView clearBtn;

    private boolean isFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_container);


        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        listView.setOnItemClickListener(this);
        shoppingFoodBtn.setOnClickListener(this);
        shoppingRecipeBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
    }

    private void initData() {
        isFood = true;
        recipes = FrApplication.getInstance().getBasket();
        dataList=new ArrayList<Map<String,Object>>();
        getData();
        shopping_adapter=new ShoppingAdapter(this, dataList, R.layout.activity_shopping_food_item, new String[]{"ingredient_id","ingredient_type","ingredient_status","ingredient_name","ingredient_weight"}, new int[]{R.id.ingredient_id,R.id.ingredient_type,R.id.ingredient_status,R.id.ingredient_name,R.id.ingredient_weight});
        listView.setAdapter(shopping_adapter);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.shopping_list_view);
        shoppingFoodBtn = (LinearLayout) findViewById(R.id.shopping_food);
        shoppingFoodLine1 = (ImageView) findViewById(R.id.shopping_food_active_line_1);
        shoppingFoodLine2 = (ImageView) findViewById(R.id.shopping_food_active_line_2);
        shoppingRecipeBtn = (LinearLayout) findViewById(R.id.shopping_recipe);
        shoppingRecipeLine1 = (ImageView) findViewById(R.id.shopping_recipe_active_line_1);
        shoppingRecipeLine2 = (ImageView) findViewById(R.id.shopping_recipe_active_line_2);

        backBtn = (ImageView) findViewById(R.id.back_btn);
        clearBtn = (TextView) findViewById(R.id.shopping_clear);
    }

    public void getData() {
        dataList.clear();
        if(isFood){
            Map<String, Component> counter = new HashMap<String, Component>();
            for(int i = 0; i < recipes.size(); i++) {
                List<Component> components = recipes.get(i).getComponent_set();
                for(int j = 0; j < components.size(); j++) {
                    String name = components.get(j).getIngredient().getName();
                    if (counter.containsKey(name)) {
                        Component component = counter.get(name);
                        component.setMAmount(component.getMAmount() + components.get(j).getMAmount());
                        int status = component.getIngredient().getStatus();
                        component.getIngredient().setStatus(status & components.get(j).getIngredient().getStatus());
                    }
                    else {
                        Component component = new Component();
                        component.setMAmount(components.get(j).getMAmount());
                        Ingredient newIngredient = new Ingredient();
                        newIngredient.setId(components.get(j).getIngredient().getId());
                        newIngredient.setName(components.get(j).getIngredient().getName());
                        newIngredient.setStatus(components.get(j).getIngredient().getStatus());
                        component.setIngredient(newIngredient);
                        counter.put(name, component);
                    }
                }
            }
            Iterator<String> iterator = counter.keySet().iterator();
            while(iterator.hasNext()) {
                String name = iterator.next();
                Component component = counter.get(name);
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("ingredient_id", component.getIngredient().getId());
                map.put("ingredient_type", 0);
                map.put("ingredient_status", component.getIngredient().getStatus());
                map.put("ingredient_name", component.getIngredient().getName());
                map.put("ingredient_weight", component.getMAmount() + "g");
                dataList.add(map);
            }
        }else{
            for(int i = 0; i < recipes.size(); i++) {
                Map<String, Object> recipe =new HashMap<String, Object>();
                List<Component> components = recipes.get(i).getComponent_set();
                recipe.put("ingredient_id", recipes.get(i).getId());
                recipe.put("ingredient_type", 1);
                recipe.put("ingredient_name", ""+ recipes.get(i).getTitle());
                recipe.put("ingredient_status", 0);
                dataList.add(recipe);
                for(int j = 0; j < components.size(); j++) {
                    Map<String, Object> map =new HashMap<String, Object>();
                    Ingredient ingredient = components.get(j).getIngredient();
                    map.put("ingredient_id", ingredient.getId());
                    map.put("ingredient_type", 0);
                    map.put("ingredient_name", ingredient.getName());
                    map.put("ingredient_status",  ingredient.getStatus());
                    map.put("ingredient_weight", components.get(j).getAmount() + "g");
                    dataList.add(map);
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shopping_food:
                shoppingFoodLine1.setVisibility(View.VISIBLE);
                shoppingFoodLine2.setBackground(getResources().getDrawable(R.drawable.theme_line_active));
                shoppingRecipeLine1.setVisibility(View.INVISIBLE);
                shoppingRecipeLine2.setBackground(getResources().getDrawable(R.drawable.theme_line));
                isFood = true;
                getData();
                shopping_adapter.notifyDataSetChanged();
                break;
            case R.id.shopping_recipe:
                shoppingRecipeLine1.setVisibility(View.VISIBLE);
                shoppingRecipeLine2.setBackground(getResources().getDrawable(R.drawable.theme_line_active));
                shoppingFoodLine1.setVisibility(View.INVISIBLE);
                shoppingFoodLine2.setBackground(getResources().getDrawable(R.drawable.theme_line));
                isFood = false;
                getData();
                shopping_adapter.notifyDataSetChanged();
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.shopping_clear:
                recipes.clear();
                dataList.clear();
                FrApplication.getInstance().clearBasket();
                shopping_adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FrApplication.getInstance().saveBasket(recipes);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.shopping_list_view:{
                if((((TextView)view.findViewById(R.id.ingredient_type)).getText().toString()).equals("0")){
                    if((((TextView)view.findViewById(R.id.ingredient_status)).getText().toString()).equals("0")){
                        (view.findViewById(R.id.ingredient_line)).setVisibility(View.VISIBLE);
                        ((TextView) view.findViewById(R.id.ingredient_name)).setTextColor(getResources().getColor(R.color.gray));
                        ((TextView) view.findViewById(R.id.ingredient_weight)).setTextColor(getResources().getColor(R.color.gray));
                        ((TextView) view.findViewById(R.id.ingredient_status)).setText("1");
                        dataList.get(position).put("ingredient_status",1);
                        shopping_adapter.notifyDataSetChanged();
                        recordData(position, 1);
                    }else{
                        (view.findViewById(R.id.ingredient_line)).setVisibility(View.GONE);
                        ((TextView) view.findViewById(R.id.ingredient_name)).setTextColor(getResources().getColor(R.color.login_input_text_color));
                        ((TextView) view.findViewById(R.id.ingredient_weight)).setTextColor(getResources().getColor(R.color.login_input_text_color));
                        ((TextView) view.findViewById(R.id.ingredient_status)).setText("0");
                        dataList.get(position).put("ingredient_status",0);
                        shopping_adapter.notifyDataSetChanged();
                        recordData(position, 0);
                    }
                }else {
                    String recipeID = ((TextView) view.findViewById(R.id.ingredient_id)).getText().toString();
                    Intent intent=new Intent(this,RecipeActivity.class);
                    intent.putExtra("id", recipeID);
                    startActivity(intent);
                }
                break;
            }
            default:
                break;
        }
    }

    private void recordData(int position, int status) {
        if(isFood) {
            for(int i = 0; i < recipes.size(); i++) {
                for(int j = 0; j < recipes.get(i).getComponent_set().size(); j++) {
                    Component component = recipes.get(i).getComponent_set().get(j);
                    Ingredient ingredient = component.getIngredient();
                    if(ingredient.getId() == (int)dataList.get(position).get("ingredient_id"))
                        ingredient.setStatus(status);
                }
            }
        }else {
            int recipe_id = 0;
            for(int i = position - 1; i >= 0; i--) {
                if((int)dataList.get(i).get("ingredient_type") == 1) {
                    recipe_id = (int) dataList.get(i).get("ingredient_id");
                    break;
                }
            }

            for(int i = 0; i <recipes.size(); i++) {
                if (recipes.get(i).getId() == recipe_id) {
                    for (int j = 0; j < recipes.get(i).getComponent_set().size(); j++) {
                        Component component = recipes.get(i).getComponent_set().get(j);
                        Ingredient ingredient = component.getIngredient();
                        if (ingredient.getId() == (int) dataList.get(position).get("ingredient_id"))
                            ingredient.setStatus(status);
                    }
                    break;
                }
            }
        }
    }
}
