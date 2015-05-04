package cn.fitrecipe.android.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.fitrecipe.android.LoginActivity;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.RegisterActivity;

import static com.tencent.open.utils.Global.getSharedPreferences;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class MeFragment extends Fragment implements View.OnClickListener {

    private TextView me_test0;
    private TextView me_test1;
    private TextView me_test2;
    private Button me_test3;

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
        me_test0 = (TextView) view.findViewById(R.id.me_test0);
        me_test1 = (TextView) view.findViewById(R.id.me_test1);
        me_test2 = (TextView) view.findViewById(R.id.me_test2);
        me_test3 = (Button) view.findViewById(R.id.me_test3);
    }

    private void initData() {
        SharedPreferences preferences=getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
        boolean isLogined = preferences.getBoolean("isLogined", false);
        if(isLogined){
            me_test0.setText("已登录");
            me_test1.setText("用户名："+preferences.getString("username", "出错啦"));
            me_test2.setText("平台："+preferences.getString("platform", "出错啦"));
            me_test3.setText("退出登陆");
        }else{
            me_test0.setText("未登录");
            me_test1.setText("用户名：暂无");
            me_test2.setText("平台：暂无");
            me_test3.setText("登陆");
        }
    }

    private void initEvent() {
        me_test3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.me_test3:
                if(me_test3.getText().toString().equals("退出登陆")){
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
