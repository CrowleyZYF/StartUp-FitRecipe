package cn.fitrecipe.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidviewhover.BlurLayout;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.R;
/**
 * Created by 奕峰 on 2015/4/11.
 */
public class KnowledgeFragment extends Fragment
{
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);


        return v;
    }
}
