package cn.fitrecipe.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rey.material.widget.CheckBox;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import cn.fitrecipe.android.Adpater.SeriesCardAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.Series;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class KnowledgeFragment extends Fragment{
    private RecyclerView frKnowledgeSeriesRecyclerView;
    private RecyclerViewLayoutManager frKnowledgeSeriesLayoutManager;
    private SeriesCardAdapter seriesCardAdapter;
    private ArrayList<Series> dataList;
    private ArrayList<Series> backup;
    private CheckBox[] checkBoxes;
    private int[] cbIds = new int[]{R.id.common_check, R.id.muscle_check, R.id.fat_check};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_knowledge, container, false);

        initView(v);
        getData();
        return v;
    }

    private void getData() {
        String url = FrServerConfig.getSeriesByType(true, true, true);
        GetRequest request = new GetRequest(url, FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res.has("data")) {
                    try {
                        JSONArray data = res.getJSONArray("data");
                        processData(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        FrRequest.getInstance().request(request);
    }


    private void processData(JSONArray data) {
        dataList = new Gson().fromJson(data.toString(), new TypeToken<ArrayList<Series>>(){}.getType());
        backup = new ArrayList<>(dataList);
        seriesCardAdapter = new SeriesCardAdapter(this.getActivity(), dataList);
        frKnowledgeSeriesRecyclerView.setAdapter(seriesCardAdapter);
    }

    private void initView(View v) {
        frKnowledgeSeriesRecyclerView = (RecyclerView) v.findViewById(R.id.knowledge_series_recycler_view);
        frKnowledgeSeriesLayoutManager = new RecyclerViewLayoutManager(this.getActivity());
        frKnowledgeSeriesLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frKnowledgeSeriesRecyclerView.setLayoutManager(frKnowledgeSeriesLayoutManager);

        checkBoxes = new CheckBox[3];
        for(int i = 0; i < cbIds.length; i++) {
            checkBoxes[i] = (CheckBox) v.findViewById(cbIds[i]);
            checkBoxes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a = -1, b = -1, c = -1;
                    if(checkBoxes[0].isChecked())   a = 1;
                    if(checkBoxes[1].isChecked())   b = 2;
                    if(checkBoxes[2].isChecked())   c = 3;
                    dataList.clear();
                    for(int j = 0; j < backup.size(); j++) {
                        int my = backup.get(j).getArticle_type();
                        if(my == a || my == b || my == c) {
                            dataList.add(backup.get(j));
                        }
                    }
                    seriesCardAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
