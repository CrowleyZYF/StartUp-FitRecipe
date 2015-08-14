package cn.fitrecipe.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.SeriesCardAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.BorderScrollView;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.Collection;
import cn.fitrecipe.android.entity.Series;
import pl.tajchert.sample.DotsTextView;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class KnowledgeCollectFragment extends Fragment
{
    private BorderScrollView borderScrollView;
    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;


    private RecyclerView knowledgeSeriesRecyclerView;
    private RecyclerViewLayoutManager knowledgeSeriesLayoutManager;
    private SeriesCardAdapter seriesCardAdapter;
    private ArrayList<Series> dataList;

    private int lastid = - 1;

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

        loadingInterface = (LinearLayout) view.findViewById(R.id.loading_interface);
        borderScrollView = (BorderScrollView) view.findViewById(R.id.collect_knowledge_series_list);
        dotsTextView = (DotsTextView) view.findViewById(R.id.dots);
        borderScrollView.setOnBorderListener(new BorderScrollView.OnBorderListener() {
            @Override
            public void onBottom() {
                getData();
            }

            @Override
            public void onTop() {

            }
        });
    }

    private void getData() {
        String url = FrServerConfig.getCollectionsUrl("series", lastid);
        GetRequest request = new GetRequest(url, FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res.has("data")) {
                    if(lastid == -1)
                        hideLoading(false, "");
                    else
                        borderScrollView.setCompleteMore();
                    try {
                        JSONArray data = res.getJSONArray("data");
                        if(data == null || data.length() == 0) {
                            Toast.makeText(getActivity(), "没有多余", Toast.LENGTH_SHORT).show();
                        }else
                            processData(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideLoading(true, getResources().getString(R.string.network_error));
            }
        });
        FrRequest.getInstance().request(request);
    }

    private void processData(JSONArray data) {
        List<Collection> collections = new Gson().fromJson(data.toString(), new TypeToken<List<Collection>>() {
        }.getType());
        if(dataList == null)
            dataList = new ArrayList<>();
        if(collections != null && collections.size() > 0) {
            lastid = collections.get(collections.size() - 1).getId();
            for(int i = 0; i < collections.size(); i++) {
                dataList.add(collections.get(i).getSeries());
            }
        }
        if(seriesCardAdapter == null) {
            seriesCardAdapter = new SeriesCardAdapter(getActivity(), dataList);
            knowledgeSeriesRecyclerView.setAdapter(seriesCardAdapter);
        }else
            seriesCardAdapter.notifyDataSetChanged();
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }else{
            borderScrollView.setVisibility(View.VISIBLE);
            borderScrollView.smoothScrollTo(0, 0);
        }
    }
}
