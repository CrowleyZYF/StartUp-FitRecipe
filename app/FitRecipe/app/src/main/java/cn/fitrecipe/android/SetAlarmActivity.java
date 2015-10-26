package cn.fitrecipe.android;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rey.material.widget.Switch;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class SetAlarmActivity extends Activity implements View.OnClickListener {

    private ImageView back_btn;
    private TextView right_btn;
    private Switch breakfast_check;
    private Switch add_meal_01_check;
    private Switch lunch_check;
    private Switch add_meal_02_check;
    private Switch dinner_check;
    private Switch add_meal_03_check;
    private RelativeLayout breakfast_time_change;
    private RelativeLayout add_meal_01_time_change;
    private RelativeLayout lunch_time_change;
    private RelativeLayout add_meal_02_time_change;
    private RelativeLayout dinner_time_change;
    private RelativeLayout add_meal_03_time_change;
    private TextView breakfast_time_change_text;
    private TextView add_meal_01_time_change_text;
    private TextView lunch_time_change_text;
    private TextView add_meal_02_time_change_text;
    private TextView dinner_time_change_text;
    private TextView add_meal_03_time_change_text;
    private TextView min_time_change_text;
    private TextView max_time_change_text;
    private int nowDialog = 0;
    private boolean shouldOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        initView();
        initData();
        initEvent();
    }

    private void initData() {
        SharedPreferences preferences=getSharedPreferences("user", MODE_PRIVATE);
        breakfast_time_change_text.setText(preferences.getString("breakfast_time", "07:30"));
        add_meal_01_time_change_text.setText(preferences.getString("add_meal_01_time", "10:00"));
        lunch_time_change_text.setText(preferences.getString("lunch_time", "12:00"));
        add_meal_02_time_change_text.setText(preferences.getString("add_meal_02_time", "15:00"));
        dinner_time_change_text.setText(preferences.getString("dinner_time", "17:30"));
        add_meal_03_time_change_text.setText(preferences.getString("add_meal_03_time", "22:00"));
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        right_btn.setOnClickListener(this);
        breakfast_time_change.setOnClickListener(this);
        add_meal_01_time_change.setOnClickListener(this);
        lunch_time_change.setOnClickListener(this);
        add_meal_02_time_change.setOnClickListener(this);
        dinner_time_change.setOnClickListener(this);
        add_meal_03_time_change.setOnClickListener(this);
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        back_btn.setImageResource(R.drawable.icon_back_white);
        right_btn = (TextView) findViewById(R.id.right_btn);

        breakfast_check = (Switch) findViewById(R.id.breakfast_check);
        add_meal_01_check = (Switch) findViewById(R.id.add_meal_01_check);
        lunch_check = (Switch) findViewById(R.id.lunch_check);
        add_meal_02_check = (Switch) findViewById(R.id.add_meal_02_check);
        dinner_check = (Switch) findViewById(R.id.dinner_check);
        add_meal_03_check = (Switch) findViewById(R.id.add_meal_03_check);

        breakfast_time_change = (RelativeLayout) findViewById(R.id.breakfast_time_change);
        add_meal_01_time_change = (RelativeLayout) findViewById(R.id.add_meal_01_time_change);
        lunch_time_change = (RelativeLayout) findViewById(R.id.lunch_time_change);
        add_meal_02_time_change = (RelativeLayout) findViewById(R.id.add_meal_02_time_change);
        dinner_time_change = (RelativeLayout) findViewById(R.id.dinner_time_change);
        add_meal_03_time_change = (RelativeLayout) findViewById(R.id.add_meal_03_time_change);

        breakfast_time_change_text = (TextView) findViewById(R.id.breakfast_time_change_text);
        add_meal_01_time_change_text = (TextView) findViewById(R.id.add_meal_01_time_change_text);
        lunch_time_change_text = (TextView) findViewById(R.id.lunch_time_change_text);
        add_meal_02_time_change_text = (TextView) findViewById(R.id.add_meal_02_time_change_text);
        dinner_time_change_text = (TextView) findViewById(R.id.dinner_time_change_text);
        add_meal_03_time_change_text = (TextView) findViewById(R.id.add_meal_03_time_change_text);
        min_time_change_text = (TextView) findViewById(R.id.min_time_change_text);
        max_time_change_text = (TextView) findViewById(R.id.max_time_change_text);
    }

    @Override
    public void onClick(View v) {
        final String[] nowDialogTimeName = {"凌晨4点", "早餐时间", "上午加餐时间", "午餐时间", "下午加餐时间", "晚餐时间", "夜宵时间", "凌晨"};
        final TextView[] nowTimeTextView = {min_time_change_text, breakfast_time_change_text, add_meal_01_time_change_text, lunch_time_change_text, add_meal_02_time_change_text, dinner_time_change_text, add_meal_03_time_change_text, max_time_change_text};
        switch (v.getId()){
            case R.id.left_btn:
                finish();
                break;
            case R.id.right_btn:
                Toast.makeText(this, "上午加餐：" + add_meal_01_check.isChecked() +
                        "，下午加餐：" + add_meal_02_check.isChecked() +
                        "，夜宵：" + add_meal_03_check.isChecked(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.breakfast_time_change:
                nowDialog = 0;
                shouldOpen = true;
                break;
            case R.id.add_meal_01_time_change:
                nowDialog = 1;
                shouldOpen = true;
                break;
            case R.id.lunch_time_change:
                nowDialog = 2;
                shouldOpen = true;
                break;
            case R.id.add_meal_02_time_change:
                nowDialog = 3;
                shouldOpen = true;
                break;
            case R.id.dinner_time_change:
                nowDialog = 4;
                shouldOpen = true;
                break;
            case R.id.add_meal_03_time_change:
                nowDialog = 5;
                shouldOpen = true;
                break;
        }
        if(shouldOpen){
            shouldOpen = false;
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hour_prefix = "";
                    String minute_prefix = "";
                    String lastTimeString = nowTimeTextView[nowDialog].getText().toString();
                    String nextTimeString = nowTimeTextView[nowDialog+2].getText().toString();
                    if (hourOfDay<10)
                        hour_prefix = "0";
                    if (minute<10)
                        minute_prefix = "0";
                    if(hourOfDay*60+minute>=Integer.parseInt(nextTimeString.substring(0, 2))*60+Integer.parseInt(nextTimeString.substring(3, 5)))
                        Toast.makeText(view.getContext(), nowDialogTimeName[nowDialog+1] + "必须早于" + nowDialogTimeName[nowDialog+2],Toast.LENGTH_SHORT).show();
                    else if (hourOfDay*60+minute<=Integer.parseInt(lastTimeString.substring(0, 2))*60+Integer.parseInt(lastTimeString.substring(3, 5)))
                        Toast.makeText(view.getContext(), nowDialogTimeName[nowDialog+1] + "必须晚于" + nowDialogTimeName[nowDialog],Toast.LENGTH_SHORT).show();
                    else{
                        nowTimeTextView[nowDialog+1].setText(hour_prefix + hourOfDay + ":" + minute_prefix + minute);
                    }
                }
            },
                    Integer.parseInt(nowTimeTextView[nowDialog+1].getText().toString().substring(0,2)),
                    Integer.parseInt(nowTimeTextView[nowDialog+1].getText().toString().substring(3,5)),
                    true);
            timePickerDialog.show();
        }

    }
}
