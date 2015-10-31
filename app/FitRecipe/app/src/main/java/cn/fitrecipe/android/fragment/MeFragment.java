package cn.fitrecipe.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.fitrecipe.android.CollectActivity;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.IngredientActivity;
import cn.fitrecipe.android.LoginActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RecordActivity;
import cn.fitrecipe.android.ReportActivity;
import cn.fitrecipe.android.entity.Report;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class MeFragment extends Fragment implements View.OnClickListener {


    private TextView me_name;
    private TextView me_status;
    private TextView me_punch;
    private TextView me_work;
    private CircleImageView me_avatar;

    private LinearLayout me_record_btn;
    private LinearLayout me_collect_btn;
    private LinearLayout me_report_btn;
    private LinearLayout me_shopping_btn;
    private LinearLayout me_login_btn;

    private TextView me_login_btn_text;

    public static boolean isFresh = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        initView(view);
        initData();
        initEvent();

        return view;
    }

    public void freshData(){
        GetRequest request = new GetRequest(FrServerConfig.getPunchCountUrl(), FrApplication.getInstance().getToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        if(res != null && res.has("data")) {
                            try {
                                int data = res.getJSONObject("data").getInt("count");
                                me_punch.setText("打卡次数："+data);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        me_punch.setText("打卡次数："+0);
                    }
                });
        FrRequest.getInstance().request(request);
    }

    private void initView(View view) {
        me_name = (TextView) view.findViewById(R.id.me_name);
        me_status = (TextView) view.findViewById(R.id.me_status);
        me_punch = (TextView) view.findViewById(R.id.me_punch);
        me_work = (TextView) view.findViewById(R.id.me_work);

        me_record_btn = (LinearLayout) view.findViewById(R.id.me_record_btn);
        me_collect_btn = (LinearLayout) view.findViewById(R.id.me_collect_btn);
        me_report_btn = (LinearLayout) view.findViewById(R.id.me_report_btn);
        me_shopping_btn = (LinearLayout) view.findViewById(R.id.me_shopping_btn);
        me_login_btn = (LinearLayout) view.findViewById(R.id.me_login_btn);

        me_login_btn_text = (TextView) view.findViewById(R.id.me_login_btn_text);
        me_avatar = (CircleImageView) view.findViewById(R.id.me_avatar);
    }

    private void initData() {
        boolean isLogined = FrApplication.getInstance().isLogin();
        if(isLogined){
            me_name.setText(FrApplication.getInstance().getAuthor().getNick_name());
            //login_platform.setText("平台："+preferences.getString("platform", "出错啦"));
            FrApplication.getInstance().getMyImageLoader().displayImage(me_avatar, FrApplication.getInstance().getAuthor().getAvatar());
            me_login_btn_text.setText("退出登陆");
        }else{
            me_name.setText("未登录");
            //login_platform.setText("平台：暂无");
            me_login_btn_text.setText("登陆");
            me_avatar.setImageResource(R.drawable.pic_header);
        }
        freshData();
    }

    @Override
    public void onResume() {
        super.onResume();
        Report report = FrApplication.getInstance().getReport();
        if(report == null) {
            me_status.setText("无报告");
        }else {
            if(report.isGoalType() == 0) {
                me_status.setText("增肌中...");
            }else {
                me_status.setText("减脂中...");
            }
        }
        if(isFresh)
            initData();
    }

    private void initEvent() {
        me_record_btn.setOnClickListener(this);
        me_collect_btn.setOnClickListener(this);
        me_report_btn.setOnClickListener(this);
        me_shopping_btn.setOnClickListener(this);
        me_login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.me_record_btn:{
                if(!FrApplication.getInstance().isLogin()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.login_tip), Toast.LENGTH_SHORT).show();
                }else
                    startActivity(new Intent(getActivity(), RecordActivity.class));
                break;
            }
            case R.id.me_collect_btn:{
                if(!FrApplication.getInstance().isLogin()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.login_tip), Toast.LENGTH_SHORT).show();
                }
                else
                    startActivity(new Intent(getActivity(), CollectActivity.class));
                break;
            }
            case R.id.me_report_btn:{
                if(!FrApplication.getInstance().isLogin()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.login_tip), Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), ReportActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.me_shopping_btn:{
                startActivity(new Intent(getActivity(), IngredientActivity.class));
                break;
            }
            case R.id.me_login_btn:
                if(me_login_btn_text.getText().toString().equals("退出登陆")){
                    FrApplication.getInstance().logOut();
                    initData();
                }else{
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }
}
