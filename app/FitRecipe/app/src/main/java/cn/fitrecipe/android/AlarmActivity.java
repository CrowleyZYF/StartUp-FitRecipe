package cn.fitrecipe.android;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AlarmActivity extends Activity implements View.OnClickListener{

    private TextView msg;
    private Button sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        msg = (TextView) findViewById(R.id.msg);
        msg.setText(getIntent().getStringExtra("msg"));

        sure = (Button) findViewById(R.id.sure);
        sure.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure:
                finish();
                break;
        }
    }
}
