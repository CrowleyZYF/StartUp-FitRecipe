package cn.fitrecipe.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
public class KnowledgeCollectFragment extends Fragment
{
    private RecyclerView knowledgeSeriesRecyclerView;
    private RecyclerViewLayoutManager knowledgeSeriesLayoutManager;
    private SeriesCardAdapter seriesCardAdapter;
    private ArrayList<Series> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_collect_knowledge, container, false);

        initView(view);
        getData();

        return view;
    }

    private void initView(View view) {
        knowledgeSeriesRecyclerView = (RecyclerView) view.findViewById(R.id.collect_knowledge_series_recycler_view);
        knowledgeSeriesLayoutManager = new RecyclerViewLayoutManager(this.getActivity());
        knowledgeSeriesLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        knowledgeSeriesRecyclerView.setLayoutManager(knowledgeSeriesLayoutManager);
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
        seriesCardAdapter = new SeriesCardAdapter(this.getActivity(), dataList);
        knowledgeSeriesRecyclerView.setAdapter(seriesCardAdapter);
    }
}
