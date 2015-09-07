package cn.fitrecipe.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.PunchDayAdapter;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.PunchDay;
import cn.fitrecipe.android.entity.PunchItem;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class PunchFragment extends Fragment
{
    private RecyclerView punchRecordRecyclerView;
    private PunchDayAdapter punchDayAdapter;
    private RecyclerViewLayoutManager punchRecordLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_record_punch, container, false);

        initView(view);
        initData();
        initEvent();

        return view;
    }

    private void initView(View view) {
        punchRecordRecyclerView = (RecyclerView) view.findViewById(R.id.punch_record);
        punchRecordRecyclerView.setHasFixedSize(true);
        punchRecordLayoutManager = new RecyclerViewLayoutManager(this.getActivity());
        punchRecordLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        punchRecordRecyclerView.setLayoutManager(punchRecordLayoutManager);
    }

    private void initData() {
        List<PunchDay> punchDays = new ArrayList<PunchDay>();
        for(int i=0; i<5; i++){
            List<PunchItem> punchItems = new ArrayList<PunchItem>();
            for(int j=0; j<5; j++){
                PunchItem punchItem = new PunchItem(R.drawable.punch_temp, i, j, (i+j+100)*j, 20, 50, 30);
                punchItems.add(punchItem);
            }
            PunchDay day = new PunchDay("9月7号", punchItems);
            punchDays.add(day);
        }
        punchDayAdapter = new PunchDayAdapter(this.getActivity(), punchDays);
        punchRecordRecyclerView.setAdapter(punchDayAdapter);
    }

    private void initEvent() {

    }
}
