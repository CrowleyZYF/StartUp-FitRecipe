package cn.fitrecipe.android.PlanTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.fitrecipe.android.R;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class Q02Fragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_plan_test_02, container, false);
    }
}
