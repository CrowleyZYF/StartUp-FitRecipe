package cn.fitrecipe.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.fitrecipe.android.fragment.PunchFragment;
import cn.fitrecipe.android.fragment.RecipeFragment;
import cn.fitrecipe.android.fragment.WorkFragment;
import cn.fitrecipe.android.function.Common;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class RecordActivity extends FragmentActivity implements View.OnClickListener {
    private ImageView back_btn;
    private ImageView upload_btn;
    private TextView header_name_text;

    private LinearLayout me_record_punch;
    private LinearLayout me_record_work;

    private Fragment punch_fragment;
    private Fragment work_fragment;

    private ImageView record_punch_active_line_1;
    private ImageView record_punch_active_line_2;
    private ImageView record_work_active_line_1;
    private ImageView record_work_active_line_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        initView();
        initEvent();
        setSelect(0);
    }

    private void initEvent() {
        back_btn.setOnClickListener(this);
        me_record_punch.setOnClickListener(this);
        me_record_work.setOnClickListener(this);
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.left_btn);
        upload_btn = (ImageView) findViewById(R.id.right_btn);
        back_btn.setImageResource(R.drawable.icon_back_white);
        upload_btn.setVisibility(View.GONE);
        header_name_text = (TextView) findViewById(R.id.header_name_text);
        header_name_text.setText("我的食记");

        me_record_punch = (LinearLayout) findViewById(R.id.record_punch);
        me_record_work = (LinearLayout) findViewById(R.id.record_work);

        record_punch_active_line_1 = (ImageView) findViewById(R.id.record_punch_active_line_1);
        record_punch_active_line_2 = (ImageView) findViewById(R.id.record_punch_active_line_2);
        record_work_active_line_1 = (ImageView) findViewById(R.id.record_work_active_line_1);
        record_work_active_line_2 = (ImageView) findViewById(R.id.record_work_active_line_2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:
                finish();
                break;
            case R.id.record_punch:
                setSelect(0);
                break;
            case R.id.record_work:
                //setSelect(1);
                Common.toBeContinuedDialog(this).show();
                break;
        }
    }

    private void setSelect(int tab) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        hideFragment(transaction);
        record_punch_active_line_1.setVisibility(View.INVISIBLE);
        record_punch_active_line_2.setBackground(getResources().getDrawable(R.drawable.theme_line));
        record_work_active_line_1.setVisibility(View.INVISIBLE);
        record_work_active_line_2.setBackground(getResources().getDrawable(R.drawable.theme_line));
        switch (tab)
        {
            case 0:
                if (punch_fragment == null){
                    punch_fragment = new PunchFragment();
                    transaction.add(R.id.record_content, punch_fragment);
                } else{
                    transaction.show(punch_fragment);
                }
                upload_btn.setVisibility(View.GONE);
                record_punch_active_line_1.setVisibility(View.VISIBLE);
                record_punch_active_line_2.setBackground(getResources().getDrawable(R.drawable.theme_line_active));
                break;
            case 1:
                if (work_fragment == null){
                    work_fragment = new WorkFragment();
                    transaction.add(R.id.record_content, work_fragment);
                } else{
                    transaction.show(work_fragment);
                }
                upload_btn.setVisibility(View.GONE);
                record_work_active_line_1.setVisibility(View.VISIBLE);
                record_work_active_line_2.setBackground(getResources().getDrawable(R.drawable.theme_line_active));
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (punch_fragment != null){
            transaction.hide(punch_fragment);
        }
        if (work_fragment != null){
            transaction.hide(work_fragment);
        }
    }
}
