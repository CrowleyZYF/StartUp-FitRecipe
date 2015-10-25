package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class SetMealActivity extends Activity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_meal);

        initView();
        initEvent();
    }

    private void initEvent() {

    }

    private void initView() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_account_btn:
                Intent dialog = new Intent(this,ChoosePhotoActivity.class);
                startActivity(dialog);
                break;
            case R.id.set_meal_btn:
                Intent punch = new Intent(this,PunchContentSureActivity.class);
                startActivity(punch);
                break;
        }

    }
}
