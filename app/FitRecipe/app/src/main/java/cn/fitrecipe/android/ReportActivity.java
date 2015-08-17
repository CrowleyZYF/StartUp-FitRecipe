package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class ReportActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Intent intent =getIntent();
        String input = intent.getStringExtra("input");
        ((TextView) findViewById(R.id.report_input)).setText(input);
        String output = intent.getStringExtra("output");
        ((TextView) findViewById(R.id.report_output)).setText(output);
    }

}
