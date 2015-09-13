package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import cn.fitrecipe.android.UI.PieChartView;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class PunchContentSureActivity extends Activity implements View.OnClickListener {
    private PieChartView chartView1;
    private PieChartView chartView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_sure);

        initView();
        initEvent();
    }

    private void initEvent() {
    }

    private void initView() {
        chartView1 = (PieChartView) findViewById(R.id.punch_piechart);
        chartView2 = (PieChartView) findViewById(R.id.piechartview);
        float[] pieData = {60.00f, 30.00f, 10.00f};
        //chartView1.setValue(pieData, false);
        chartView2.setValue(pieData, true);
    }

    @Override
    public void onClick(View v) {

    }
}
