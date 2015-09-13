package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class SetActivity extends Activity implements View.OnClickListener {
    private Button button1;
    private Button button2;
    private Button button3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        initView();
        initEvent();
    }

    private void initEvent() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    private void initView() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                Intent dialog = new Intent(this,PunchPhotoChoiceActivity.class);
                startActivity(dialog);
                break;
            case R.id.button2:
                Intent punch = new Intent(this,PunchContentSureActivity.class);
                startActivity(punch);
                break;
        }

    }
}
