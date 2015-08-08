package cn.fitrecipe.android.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.fitrecipe.android.CollectActivity;
import cn.fitrecipe.android.IngredientActivity;
import cn.fitrecipe.android.LoginActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RecordActivity;
import cn.fitrecipe.android.ReportActivity;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class MeFragment extends Fragment implements View.OnClickListener {


    private TextView me_name;
    private TextView me_status;
    private TextView me_punch;
    private TextView me_work;

    private LinearLayout me_record_btn;
    private LinearLayout me_collect_btn;
    private LinearLayout me_report_btn;
    private LinearLayout me_shopping_btn;
    private LinearLayout me_login_btn;

    private TextView me_login_btn_text;

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
    }

    private void initData() {
        SharedPreferences preferences=getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
        boolean isLogined = preferences.getBoolean("isLogined", false);
        if(isLogined){
            me_name.setText(preferences.getString("username", "出错啦"));
            //login_platform.setText("平台："+preferences.getString("platform", "出错啦"));
            me_login_btn_text.setText("退出登陆");
        }else{
            me_name.setText("未登录");
            //login_platform.setText("平台：暂无");
            me_login_btn_text.setText("登陆");
        }
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
                startActivity(new Intent(getActivity(), RecordActivity.class));
                break;
            }
            case R.id.me_collect_btn:{
                startActivity(new Intent(getActivity(), CollectActivity.class));
                break;
            }
            case R.id.me_report_btn:{
                startActivity(new Intent(getActivity(), ReportActivity.class));
                break;
            }
            case R.id.me_shopping_btn:{
                startActivity(new Intent(getActivity(), IngredientActivity.class));
                break;
            }
            case R.id.me_login_btn:
                if(me_login_btn_text.getText().toString().equals("退出登陆")){
                    SharedPreferences preferences=getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLogined", false);
                    editor.commit();
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
