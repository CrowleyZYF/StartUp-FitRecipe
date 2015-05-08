package cn.fitrecipe.android.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.fitrecipe.android.LoginActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RegisterActivity;

import static com.tencent.open.utils.Global.getSharedPreferences;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class MeFragment extends Fragment implements View.OnClickListener {


    private TextView login_name;
    private TextView login_platform;
    private LinearLayout login_btn;
    private TextView login_btn_text;

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
        login_name = (TextView) view.findViewById(R.id.login_name);
        login_platform = (TextView) view.findViewById(R.id.login_platform);
        login_btn = (LinearLayout) view.findViewById(R.id.login_btn);
        login_btn_text = (TextView) view.findViewById(R.id.login_btn_text);
    }

    private void initData() {
        SharedPreferences preferences=getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
        boolean isLogined = preferences.getBoolean("isLogined", false);
        if(isLogined){
            login_name.setText(preferences.getString("username", "出错啦"));
            login_platform.setText("平台："+preferences.getString("platform", "出错啦"));
            login_btn_text.setText("退出登陆");
        }else{
            login_name.setText("暂无");
            login_platform.setText("平台：暂无");
            login_btn_text.setText("登陆");
        }
    }

    private void initEvent() {
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                if(login_btn_text.getText().toString().equals("退出登陆")){
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
