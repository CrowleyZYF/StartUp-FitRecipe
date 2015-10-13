package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.fitrecipe.android.function.Common;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class AddToPlanActivity extends Activity implements View.OnClickListener {

    private TextView nowDate_0_btn;
    private TextView nowDate_1_btn;
    private TextView nowDate_2_btn;
    private TextView nowDate_3_btn;
    private TextView nowDate_4_btn;
    private TextView nowDate_5_btn;
    private ArrayList<TextView> date_btn_array;

    private TextView nowMeal_0_btn;
    private TextView nowMeal_1_btn;
    private TextView nowMeal_2_btn;
    private TextView nowMeal_3_btn;
    private TextView nowMeal_4_btn;
    private TextView nowMeal_5_btn;
    private ArrayList<TextView> meal_btn_array;

    private ArrayList<String> date_text_array;
    private String[] meal_text_array = {"早餐", "上午加餐", "午餐", "下午加餐", "晚餐", "夜宵"};
    private Button add_to_plan_btn;
    private int choosen_date = 0;
    private int choosen_meal = 0;
    private double adjust_unit_weight = 50.00;//每次调整的基本单位
    private double now_weight = 200.00;//用户添加时的重量

    private TextView minus_btn;
    private TextView add_btn;
    private TextView weight_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_addtoplan);

        initView();
        initData();
        initEvent();
    }

    private void initData() {
        setDate();
    }

    private void initEvent() {
        setClickEvent(date_btn_array);
        setClickEvent(meal_btn_array);
        add_to_plan_btn.setOnClickListener(this);
        minus_btn.setOnClickListener(this);
        add_btn.setOnClickListener(this);
    }

    private void initView() {
        nowDate_0_btn = (TextView) findViewById(R.id.nowDate_0);
        nowDate_1_btn = (TextView) findViewById(R.id.nowDate_1);
        nowDate_2_btn = (TextView) findViewById(R.id.nowDate_2);
        nowDate_3_btn = (TextView) findViewById(R.id.nowDate_3);
        nowDate_4_btn = (TextView) findViewById(R.id.nowDate_4);
        nowDate_5_btn = (TextView) findViewById(R.id.nowDate_5);
        date_btn_array = new ArrayList<>();
        date_btn_array.add(nowDate_0_btn);
        date_btn_array.add(nowDate_1_btn);
        date_btn_array.add(nowDate_2_btn);
        date_btn_array.add(nowDate_3_btn);
        date_btn_array.add(nowDate_4_btn);
        date_btn_array.add(nowDate_5_btn);

        nowMeal_0_btn = (TextView) findViewById(R.id.nowMeal_0);
        nowMeal_1_btn = (TextView) findViewById(R.id.nowMeal_1);
        nowMeal_2_btn = (TextView) findViewById(R.id.nowMeal_2);
        nowMeal_3_btn = (TextView) findViewById(R.id.nowMeal_3);
        nowMeal_4_btn = (TextView) findViewById(R.id.nowMeal_4);
        nowMeal_5_btn = (TextView) findViewById(R.id.nowMeal_5);
        meal_btn_array = new ArrayList<>();
        meal_btn_array.add(nowMeal_0_btn);
        meal_btn_array.add(nowMeal_1_btn);
        meal_btn_array.add(nowMeal_2_btn);
        meal_btn_array.add(nowMeal_3_btn);
        meal_btn_array.add(nowMeal_4_btn);
        meal_btn_array.add(nowMeal_5_btn);

        add_to_plan_btn = (Button) findViewById(R.id.add_to_plan_btn);

        minus_btn = (TextView) findViewById(R.id.minus_btn);
        add_btn = (TextView) findViewById(R.id.add_btn);
        weight_text = (TextView) findViewById(R.id.weight_text);
    }

    private void setDate(){
        date_text_array = new ArrayList<>();
        for (int i = 0; i< date_btn_array.size(); i++){
            String date_text = Common.getAddDate(i);
            date_text_array.add(date_text);
            date_btn_array.get(i).setText(date_text);
        }
        date_btn_array.get(0).setText("今天");
    }

    private void resetDateBtn(ArrayList<TextView> btns){
        for(int i = 0; i< btns.size(); i++){
            btns.get(i).setTextColor(getResources().getColor(R.color.base_color));
            btns.get(i).setBackground(getResources().getDrawable(R.drawable.add_to_plan));
        }
    }

    private void setBtnChosen(TextView btn, boolean isDate){
        btn.setTextColor(getResources().getColor(R.color.white));
        btn.setBackground(getResources().getDrawable(R.drawable.add_to_plan_chosen));
        if (isDate)
            choosen_date = date_btn_array.indexOf(btn);
        else
            choosen_meal = meal_btn_array.indexOf(btn);
    }

    private void setClickEvent(ArrayList<TextView> btns){
        for (int i = 0; i < btns.size(); i++){
            btns.get(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nowDate_0:
            case R.id.nowDate_1:
            case R.id.nowDate_2:
            case R.id.nowDate_3:
            case R.id.nowDate_4:
            case R.id.nowDate_5:
                resetDateBtn(date_btn_array);
                setBtnChosen((TextView) v, true);
                break;
            case R.id.nowMeal_0:
            case R.id.nowMeal_1:
            case R.id.nowMeal_2:
            case R.id.nowMeal_3:
            case R.id.nowMeal_4:
            case R.id.nowMeal_5:
                resetDateBtn(meal_btn_array);
                setBtnChosen((TextView) v, false);
                break;
            case R.id.add_to_plan_btn:
                String date = date_text_array.get(choosen_date);
                String meal = meal_text_array[choosen_meal];
                Toast.makeText(this, "用户选择添加到"+date+"的"+meal+"中, 重量为："+now_weight+"g", Toast.LENGTH_LONG).show();
                break;
            case R.id.minus_btn:
                if (now_weight>adjust_unit_weight){
                    now_weight -= adjust_unit_weight;
                    weight_text.setText(now_weight+" g");
                }else {
                    Common.errorDialog(this, "调整错误", "重量不能小于等于0").show();
                }
                break;
            case R.id.add_btn:
                now_weight += adjust_unit_weight;
                weight_text.setText(now_weight + " g");
                break;
            default:
                break;
        }
    }
}
