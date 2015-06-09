package cn.fitrecipe.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.daimajia.androidviewhover.BlurLayout;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.CategoryActivity;
import cn.fitrecipe.android.KnowledgeSeriesActivity;
import cn.fitrecipe.android.R;
/**
 * Created by 奕峰 on 2015/4/11.
 */
public class KnowledgeFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout add_muscle;
    private RelativeLayout lose_fat;
    private RelativeLayout tips;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_knowledge, container, false);

        initView(v);
        initEvent();

        return v;
    }

    private void initEvent() {
        add_muscle.setOnClickListener(this);
        lose_fat.setOnClickListener(this);
        tips.setOnClickListener(this);
    }

    private void initView(View v) {
        add_muscle = (RelativeLayout) v.findViewById(R.id.add_muscle);
        lose_fat = (RelativeLayout) v.findViewById(R.id.lose_fat);
        tips = (RelativeLayout) v.findViewById(R.id.tips);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_muscle:{
                startActivity(new Intent(this.getActivity(), KnowledgeSeriesActivity.class));
                break;
            }
            case R.id.lose_fat:{
                startActivity(new Intent(this.getActivity(), KnowledgeSeriesActivity.class));
                break;
            }
            case R.id.tips:{
                startActivity(new Intent(this.getActivity(), KnowledgeSeriesActivity.class));
                break;
            }
        }

    }
}
