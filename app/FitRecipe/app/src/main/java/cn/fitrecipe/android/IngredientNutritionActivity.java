package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import cn.fitrecipe.android.UI.LinearLayoutForListView;
import cn.fitrecipe.android.UI.PieChartView;
import cn.fitrecipe.android.entity.Nutrition;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.function.Common;

public class IngredientNutritionActivity extends Activity implements View.OnClickListener{

    private TextView ingredient_title, ingredient_weight, calorie_data_text, protein_data_text, carbohydrate_data_text, lipids_data_text;
    private PieChartView piechartview;
    private TextView back_btn;
    private LinearLayoutForListView plan_nutrition_list;
    private ScrollView scrollView;

    private PlanComponent component;

    private double protein, lipid, carb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_nutrition);


        component = (PlanComponent) getIntent().getSerializableExtra("component");
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        protein = component.getNutritions().get(1).getAmount();
        lipid = component.getNutritions().get(2).getAmount();
        carb = component.getNutritions().get(3).getAmount();
        ingredient_title.setText(component.getName());
        ingredient_weight.setText(component.getAmount()+"");
        calorie_data_text.setText(String.format("%.2f kcal", component.getCalories() * component.getAmount() / 100));
        protein_data_text.setText(String.format("%.2f g", protein));
        lipids_data_text.setText(String.format("%.2f g", lipid));
        carbohydrate_data_text.setText(String.format("%.2f g", carb));

        double sum = protein * 4 + lipid * 9 + carb * 4;
        int a = (int) Math.round(carb * 4 * 100/ sum);
        int b = (int) Math.round(protein * 4 * 100 / sum);
        int c = 100 - a - b;
        piechartview.setValue(new float[]{a, b, c}, true, false, false);

        plan_nutrition_list.setAdapter(new NutritionAdapter(IngredientNutritionActivity.this, component.getNutritions()));
        scrollView.smoothScrollTo(0, 45);

    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
    }

    private void initView() {
        ingredient_title = (TextView) findViewById(R.id.ingredient_title);
        ingredient_weight = (TextView) findViewById(R.id.ingredient_weight);
        calorie_data_text = (TextView) findViewById(R.id.calorie_data_text);
        protein_data_text = (TextView) findViewById(R.id.protein_data_text);
        carbohydrate_data_text = (TextView) findViewById(R.id.carbohydrate_data_text);
        lipids_data_text = (TextView) findViewById(R.id.lipids_data_text);
        back_btn = (TextView) findViewById(R.id.back_btn);
        piechartview = (PieChartView) findViewById(R.id.piechartview);
        plan_nutrition_list = (LinearLayoutForListView) findViewById(R.id.plan_nutrition_list);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
        }
    }
    class NutritionAdapter extends BaseAdapter {

        Context context;
        List<Nutrition> data;

        public NutritionAdapter(Context context, List<Nutrition> data) {
            this.context = context;
            this.data = data;
        }

        public void setData(List<Nutrition> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            if(data == null)    return 0;
            return data.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView != null)
                holder = (ViewHolder) convertView.getTag();
            else {
                convertView = View.inflate(context, R.layout.ingredient_nutrition_item, null);
                holder = new ViewHolder();
                if(position%2==1){
                    convertView.setBackgroundColor(getResources().getColor(R.color.nutrition_gray));
                }else{
                    convertView.setBackgroundColor(getResources().getColor(R.color.white));
                }
                holder.textView1 = (TextView) convertView.findViewById(R.id.textview1);
                holder.textView2 = (TextView) convertView.findViewById(R.id.textview2);
                holder.textView3 = (TextView) convertView.findViewById(R.id.textview3);
                convertView.setTag(holder);
            }
            if (position==0){
                holder.textView1.setText("营养元素");
                holder.textView2.setText("每百克含量");
                holder.textView3.setText("总含量");
                holder.textView1.setTextColor(getResources().getColor(R.color.login_input_text_color));
                holder.textView2.setTextColor(getResources().getColor(R.color.login_input_text_color));
                holder.textView3.setTextColor(getResources().getColor(R.color.login_input_text_color));
            }else {

                holder.textView1.setTextColor(getResources().getColor(R.color.nutrition_text_gray));
                holder.textView2.setTextColor(getResources().getColor(R.color.nutrition_text_gray));
                holder.textView3.setTextColor(getResources().getColor(R.color.nutrition_text_gray));
                holder.textView1.setText(data.get(position-1).getName());
                holder.textView2.setText(String.format("%.1f", data.get(position-1).getAmount()) + data.get(position-1).getUnit());
                holder.textView3.setText(String.format("%.1f", data.get(position-1).getAmount() * component.getAmount() / 100) + data.get(position-1).getUnit());
            }
            return convertView;
        }

        class ViewHolder {
            TextView textView1;
            TextView textView2;
            TextView textView3;
        }
    }
}
