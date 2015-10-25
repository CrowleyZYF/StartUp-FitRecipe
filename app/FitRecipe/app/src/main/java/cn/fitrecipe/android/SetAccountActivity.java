package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class SetAccountActivity extends Activity implements View.OnClickListener {

    private RelativeLayout set_account_avatar;
    private EditText set_account_nickname;
    private TextView set_account_gender, set_account_age, set_account_height, set_account_weight, set_account_fat, set_account_target;
    private TextView save_btn;
    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_account);

        initView();
        initEvent();
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        set_account_avatar.setOnClickListener(this);
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        save_btn = (TextView) findViewById(R.id.right_btn);
        set_account_avatar = (RelativeLayout) findViewById(R.id.set_account_avatar);
        set_account_nickname = (EditText) findViewById(R.id.set_account_nickname);
        set_account_gender = (TextView) findViewById(R.id.set_account_gender);
        set_account_age = (TextView) findViewById(R.id.set_account_age);
        set_account_height = (TextView) findViewById(R.id.set_account_height);
        set_account_weight = (TextView) findViewById(R.id.set_account_weight);
        set_account_fat = (TextView) findViewById(R.id.set_account_fat);
        set_account_target = (TextView) findViewById(R.id.set_account_target);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:
                finish();
                break;
            case R.id.right_btn:
                break;
            case R.id.set_account_avatar:
                break;
        }

    }
}
