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

import cn.fitrecipe.android.Adpater.SeriesCardAdapter;
import cn.fitrecipe.android.Config.LocalDemo;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.model.SeriesCard;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class KnowledgeFragment extends Fragment{
    private RecyclerView frKnowledgeSeriesRecyclerView;
    private RecyclerViewLayoutManager frKnowledgeSeriesLayoutManager;
    private SeriesCardAdapter seriesCardAdapter;
    private List<SeriesCard> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_knowledge, container, false);

        initView(v);
        initData();

        return v;
    }

    private void initData() {
        dataList = new ArrayList<SeriesCard>();
        getKnowledgeSeries();
        seriesCardAdapter = new SeriesCardAdapter(this.getActivity(), dataList);
        frKnowledgeSeriesRecyclerView.setAdapter(seriesCardAdapter);
    }

    private void initView(View v) {
        frKnowledgeSeriesRecyclerView = (RecyclerView) v.findViewById(R.id.knowledge_series_recycler_view);
        frKnowledgeSeriesLayoutManager = new RecyclerViewLayoutManager(this.getActivity());
        frKnowledgeSeriesLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frKnowledgeSeriesRecyclerView.setLayoutManager(frKnowledgeSeriesLayoutManager);
    }

    public void getKnowledgeSeries() {
        for (int i=0;i< LocalDemo.authorName.length;i++){
            SeriesCard sc = new SeriesCard(LocalDemo.seriesName[i],LocalDemo.authorName[i],i%3,i*20+i+15,i*63,LocalDemo.seriesBG[i],LocalDemo.headBG[i]);
            dataList.add(sc);
        }
    }
}
