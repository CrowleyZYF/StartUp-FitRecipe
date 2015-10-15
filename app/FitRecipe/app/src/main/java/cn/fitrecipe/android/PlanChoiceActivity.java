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
import android.widget.Toast;

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
import cn.fitrecipe.android.dao.PlanInUseDao;
import cn.fitrecipe.android.entity.PlanInUse;
import cn.fitrecipe.android.entity.SeriesPlan;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
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
    }


    private void processData(JSONArray data) throws JSONException {
        Gson gson = new Gson();
        plans = gson.fromJson(data.toString(), new TypeToken<ArrayList<SeriesPlan>>(){}.getType());
        if(plans != null) {
            PlanInUseDao dao = new PlanInUseDao(this);
            PlanInUse planInUse = dao.getPlanInUse();
            if(planInUse != null) {
                for (int i = 0; i < plans.size(); i++) {
                    if (planInUse.getName().equals(plans.get(i).getTitle()))
                        plans.get(i).setIsUsed(true);
                }
            }
        }
        planCardAdapter = new PlanCardAdapter(this, plans);
        planChoiceRecyclerView.setAdapter(planCardAdapter);
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }else{
            scrollView.setVisibility(View.VISIBLE);
            scrollView.smoothScrollTo(0, 0);
        }
    }

    private void getData() {
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
                if (volleyError != null && volleyError.networkResponse != null) {
                    hideLoading(true, getResources().getString(R.string.network_error));
                    int statusCode = volleyError.networkResponse.statusCode;
                    if (statusCode == 404) {
                        Toast.makeText(PlanChoiceActivity.this, "404！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        FrRequest.getInstance().request(request);
    }

    private void initEvent() {
        filter_btn.setOnClickListener(this);
        sure_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            int plan_id = data.getIntExtra("plan_id", 0);
            boolean isUsed = data.getBooleanExtra("isUsed", false);
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
