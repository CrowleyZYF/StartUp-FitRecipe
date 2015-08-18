package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class ReportActivity extends Activity {
    //data
    private int sex;
    private int age;
    private double height;
    private double weight;
    private int fat;
    private double BMI;
    private String BMI_judge;
    private int BMR;
    private int buring_rate_min;
    private int buring_rate_max;
    private double best_weight;
    private double best_weight_offset;
    private double best_weight_min;
    private double best_weight_max;
    private int calories_intake;



    //view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_container);
        Intent intent =getIntent();
        String input = intent.getStringExtra("input");
        ((TextView) findViewById(R.id.report_input)).setText(input);
        String output = intent.getStringExtra("output");
        ((TextView) findViewById(R.id.report_output)).setText(output);
    }

}
