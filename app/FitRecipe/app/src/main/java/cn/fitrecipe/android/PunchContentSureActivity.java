package cn.fitrecipe.android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import cn.fitrecipe.android.UI.PieChartView;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class PunchContentSureActivity extends Activity implements View.OnClickListener {
    private PieChartView chartView1;
    private PieChartView chartView2;
    private Bitmap bitmap;
    private ImageView punch_photo, left_btn;
    private TextView right_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_sure);

        String path = getIntent().getStringExtra("bitmap");
        bitmap = BitmapFactory.decodeFile(path);

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
        punch_photo = (ImageView) findViewById(R.id.punch_photo);
        punch_photo.setImageBitmap(bitmap);
        chartView2.setValue(pieData, true, false, false);
    }

    @Override
    public void onClick(View v) {

    }
}
