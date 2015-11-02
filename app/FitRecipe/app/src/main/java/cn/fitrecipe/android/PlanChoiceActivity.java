package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.fitrecipe.android.Adpater.PlanCardAdapter;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.UI.SlidingMenu;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.SeriesPlan;
import cn.fitrecipe.android.function.Common;
import cn.fitrecipe.android.function.JoinPlanHelper;
import cn.fitrecipe.android.function.RequestErrorHelper;
import cn.pedant.SweetAlert.SweetAlertDialog;
import pl.tajchert.sample.DotsTextView;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class PlanChoiceActivity extends Activity implements View.OnClickListener {

    private SlidingMenu mRightMenu;

    private TextView header_name;
    private ImageView back_btn;
    private ImageView filter_btn;
    private TextView sure_btn;

    private RecyclerView planChoiceRecyclerView;
    private PlanCardAdapter planCardAdapter;
    private RecyclerViewLayoutManager planChoiceLayoutManager;

    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;
    private ScrollView scrollView;

    private ArrayList<SeriesPlan> plans;
    private String planInUse;
    private TextView changeToDIY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_choice_container);

        initView();
//        initData();
        getData();
        initEvent();
    }

    private void initView() {
        header_name = (TextView) findViewById(R.id.header_name_text);
        header_name.setText(getResources().getString(R.string.recipe_plan));
        back_btn = (ImageView) findViewById(R.id.left_btn);
        back_btn.setImageResource(R.drawable.icon_back_white);
        filter_btn = (ImageView) findViewById(R.id.right_btn);
        filter_btn.setImageResource(R.drawable.icon_filter);
        filter_btn.setVisibility(View.GONE);
        sure_btn = (TextView) findViewById(R.id.filter_sure_btn);

        planChoiceRecyclerView = (RecyclerView) findViewById(R.id.plan_choice);
        planChoiceRecyclerView.setHasFixedSize(true);
        planChoiceLayoutManager = new RecyclerViewLayoutManager(this);
        planChoiceLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        planChoiceRecyclerView.setLayoutManager(planChoiceLayoutManager);

        loadingInterface = (LinearLayout)findViewById(R.id.loading_interface);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        mRightMenu = (SlidingMenu) findViewById(R.id.container_layout);
        changeToDIY = (TextView) findViewById(R.id.changeToDIY);
    }


    private void processData(JSONArray data) throws JSONException {
        Gson gson = new Gson();
        plans = gson.fromJson(data.toString(), new TypeToken<ArrayList<SeriesPlan>>(){}.getType());
        for (int i = 0; i < plans.size(); i++) {
            if(planInUse != null && plans.get(i).getTitle().equals(planInUse)) {
                plans.get(i).setIsUsed(true);
            }
        }
        if(planInUse != null && planInUse.equals("personal plan"))
            setChangeToDIY(true);
        else
            setChangeToDIY(false);
        planCardAdapter = new PlanCardAdapter(this, plans);
        planChoiceRecyclerView.setAdapter(planCardAdapter);
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            //Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }else{
            scrollView.setVisibility(View.VISIBLE);
            scrollView.smoothScrollTo(0, 0);
        }
    }

    private void getData() {
        GetRequest requestB = new GetRequest(FrServerConfig.getInUsePlanUrl(), FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                try {
                    if (res != null && res.has("data") && res.getJSONObject("data").has("plan")) {
                        planInUse = res.getJSONObject("data").getJSONObject("plan").getString("title");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //获取官方计划
                GetRequest request = new GetRequest(FrServerConfig.getOfficalPlanUrl(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        if (res != null && res.has("data")) {
                            try {
                                JSONArray data = res.getJSONArray("data");
                                processData(data);
                                hideLoading(false, "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        RequestErrorHelper.toast(PlanChoiceActivity.this, volleyError);
                    }
                });
                FrRequest.getInstance().request(request);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                RequestErrorHelper.toast(PlanChoiceActivity.this, volleyError);
            }
        });
        FrRequest.getInstance().request(requestB);
    }

    private void initEvent() {
        filter_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        changeToDIY.setOnClickListener(this);
    }

    // true for set to diy, false to cancel diy
    private void setChangeToDIY(boolean toDIY){
        if (toDIY){
            changeToDIY.setText("已使用");
            changeToDIY.setTextColor(getResources().getColor(R.color.gray));
            changeToDIY.setBackground(getResources().getDrawable(R.drawable.recipe_button_border_disable));
        }else {
            changeToDIY.setText("确认切换");
            changeToDIY.setTextColor(getResources().getColor(R.color.base_color));
            changeToDIY.setBackground(getResources().getDrawable(R.drawable.plan_button_border));
        }
    }

    private boolean getChangeToDIY(){
        if (changeToDIY.getText().toString().equals("已使用")){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.filter_sure_btn:
            case R.id.right_btn:
                mRightMenu.toggle();
                break;
            case R.id.left_btn:
                finish();
                break;
            case R.id.changeToDIY:
                if (getChangeToDIY()){
                    Common.errorDialog(this,"已经使用","已经处于自定义计划中，可点击进入其他计划进行选择切换").show();
                }else{
                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("切换计划")
                            .setContentText("确定切换至自定义计划么？他将会覆盖今天之后的第三方计划")
                            .setConfirmText("确定").setCancelText("取消").showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    try {
                                        new JoinPlanHelper(PlanChoiceActivity.this).joinPersonalPlan(new JoinPlanHelper.CallBack() {
                                            @Override
                                            public void handle(Object... res) {
                                                int id = (Integer)res[0];
                                                SeriesPlan plan1 = Common.gerneratePersonalPlan(id, PlanChoiceActivity.this);
                                                plan1.setJoined_date(Common.getDate());
                                                FrDbHelper.getInstance(PlanChoiceActivity.this).joinPlan(plan1);
                                                FrApplication.getInstance().setPlanInUse(plan1);
                                                FrDbHelper.getInstance(PlanChoiceActivity.this).clearBasket();
                                                FrApplication.getInstance().setIsBasketEmpty(true);
                                                FrApplication.getInstance().setJustChangePlan(true);
                                            }
                                        }, Common.getDate());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    for(int i = 0; i < plans.size(); i++)
                                        plans.get(i).setIsUsed(false);
                                    planCardAdapter.notifyDataSetChanged();
                                    setChangeToDIY(true);
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            int plan_id = data.getIntExtra("plan_id", 0);
            boolean isUsed = data.getBooleanExtra("isUsed", false);
            if(FrApplication.getInstance().getPlanInUse() != null && FrApplication.getInstance().getPlanInUse().getTitle().equals("personal plan"))
                setChangeToDIY(true);
            else
                setChangeToDIY(false);
            for(int i = 0; i < plans.size(); i++) {
                if(plans.get(i).getId() == plan_id)
                    plans.get(i).setIsUsed(isUsed);
                else
                    plans.get(i).setIsUsed(false);
            }
            planCardAdapter.notifyDataSetChanged();
        }
    }
}
