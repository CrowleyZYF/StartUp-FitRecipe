package cn.fitrecipe.android;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;

import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.SeriesPlan;
import cn.fitrecipe.android.function.Common;
import cn.fitrecipe.android.function.JoinPlanHelper;

public class SelectSwitchDateActivity extends Activity implements View.OnClickListener{

    private TextView option_today, option_tomorrow;

    private boolean isPersonal;
    private SeriesPlan plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_switch_date);

        isPersonal = getIntent().getBooleanExtra("isPersonal", false);
        if(!isPersonal)
            plan = FrApplication.getInstance().getPlanInUse();
        initView();
        initEvent();
    }

    private void initEvent() {
        option_tomorrow.setOnClickListener(this);
        option_today.setOnClickListener(this);
    }

    private void initView() {
        option_today = (TextView) findViewById(R.id.option_today);
        option_tomorrow = (TextView) findViewById(R.id.option_tomorrow);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.option_today:
                join(Common.getDate());
                setResult(RESULT_OK);
                break;
            case R.id.option_tomorrow:
                join(Common.getSomeDay(Common.getDate(), 1));
                setResult(RESULT_OK);
                break;
        }
    }

    private void join(final String date) {
        try {
            if(isPersonal) {
                new JoinPlanHelper(SelectSwitchDateActivity.this).joinPersonalPlan(new JoinPlanHelper.CallBack() {
                    @Override
                    public void handle(Object... res) {
                        int id = (Integer) res[0];
                        SeriesPlan plan1 = Common.gerneratePersonalPlan(id);
                        plan1.setJoined_date(date);
                        FrDbHelper.getInstance(SelectSwitchDateActivity.this).joinPlan(plan1);
                        FrApplication.getInstance().setPlanInUse(plan1);
                        FrDbHelper.getInstance(SelectSwitchDateActivity.this).clearBasket();
                        FrApplication.getInstance().setIsBasketEmpty(true);
                    }
                }, date);
            }else {
                new JoinPlanHelper(SelectSwitchDateActivity.this).joinOfficalPlan(plan.getId(), new JoinPlanHelper.CallBack() {
                    @Override
                    public void handle(Object... res) {
                        plan.setJoined_date(date);
                        FrDbHelper.getInstance(SelectSwitchDateActivity.this).joinPlan(plan);
                        FrDbHelper.getInstance(SelectSwitchDateActivity.this).clearBasket();
                        FrApplication.getInstance().setIsBasketEmpty(true);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
