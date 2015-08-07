package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.Adpater.ShoppingAdapter;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class IngredientActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView listView;
    private ShoppingAdapter shopping_adapter;
    private List<Map<String, Object>> dataList;

    private LinearLayout shoppingFoodBtn;
    private ImageView shoppingFoodLine1;
    private ImageView shoppingFoodLine2;
    private LinearLayout shoppingRecipeBtn;
    private ImageView shoppingRecipeLine1;
    private ImageView shoppingRecipeLine2;

    private ImageView backBtn;
    private TextView clearBtn;

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
        dataList=new ArrayList<Map<String,Object>>();
        getData(true);
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

    public void getData(boolean isFood) {
        dataList.clear();
        List<Map<String, Object>> temp=new ArrayList<Map<String,Object>>();
        if(isFood){
            for(int i=0;i<20;i++){
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("ingredient_id", i);
                map.put("ingredient_type", 0);
                map.put("ingredient_status", 0);
                map.put("ingredient_name", "大米"+i);
                map.put("ingredient_weight", "200g");
                dataList.add(map);
            }
        }else{
            for(int i=0;i<20;i++){
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("ingredient_id", i);
                if(i%6==0){
                    map.put("ingredient_type", 1);
                    map.put("ingredient_name", "这是个食谱"+i);
                }else{
                    map.put("ingredient_type", 0);
                    map.put("ingredient_name", "大米"+i);
                }
                map.put("ingredient_status", 0);
                map.put("ingredient_weight", "200g");
                dataList.add(map);
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
                getData(true);
                shopping_adapter.notifyDataSetChanged();
                break;
            case R.id.shopping_recipe:
                shoppingRecipeLine1.setVisibility(View.VISIBLE);
                shoppingRecipeLine2.setBackground(getResources().getDrawable(R.drawable.theme_line_active));
                shoppingFoodLine1.setVisibility(View.INVISIBLE);
                shoppingFoodLine2.setBackground(getResources().getDrawable(R.drawable.theme_line));
                getData(false);
                shopping_adapter.notifyDataSetChanged();
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.shopping_clear:
                dataList.clear();
                shopping_adapter.notifyDataSetChanged();
                break;
        }
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
                    }else{
                        (view.findViewById(R.id.ingredient_line)).setVisibility(View.GONE);
                        ((TextView) view.findViewById(R.id.ingredient_name)).setTextColor(getResources().getColor(R.color.login_input_text_color));
                        ((TextView) view.findViewById(R.id.ingredient_weight)).setTextColor(getResources().getColor(R.color.login_input_text_color));
                        ((TextView) view.findViewById(R.id.ingredient_status)).setText("0");
                        dataList.get(position).put("ingredient_status",0);
                        shopping_adapter.notifyDataSetChanged();
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
}
