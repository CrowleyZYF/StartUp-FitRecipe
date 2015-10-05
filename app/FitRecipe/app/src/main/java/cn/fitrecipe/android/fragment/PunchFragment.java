package cn.fitrecipe.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.fitrecipe.android.Adpater.PunchDayAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.dao.FrDbHelper;
import cn.fitrecipe.android.entity.DatePlan;
import cn.fitrecipe.android.function.Common;
import pl.tajchert.sample.DotsTextView;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class PunchFragment extends Fragment
{
    private RecyclerView punchRecordRecyclerView;
    private PunchDayAdapter punchDayAdapter;
    private RecyclerViewLayoutManager punchRecordLayoutManager;

    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;
    private ScrollView info_container;
    private List<DatePlan> datePlans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_record_punch, container, false);

        initView(view);
        getData();

        return view;
    }

    private void initView(View view) {
        punchRecordRecyclerView = (RecyclerView) view.findViewById(R.id.punch_record);
        punchRecordRecyclerView.setHasFixedSize(true);
        punchRecordLayoutManager = new RecyclerViewLayoutManager(this.getActivity());
        punchRecordLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        punchRecordRecyclerView.setLayoutManager(punchRecordLayoutManager);

        loadingInterface = (LinearLayout)view.findViewById(R.id.loading_interface);
        dotsTextView = (DotsTextView) view.findViewById(R.id.dots);
        info_container = (ScrollView) view.findViewById(R.id.info_container);
    }

    private void getData() {

        if(!Common.isOpenNetwork(getActivity())) {
            getPunchDateFromLocal();
        }else {
            String url = FrServerConfig.getPunchListUrl();
            GetRequest request = new GetRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    if (res != null && res.has("data")) {
                        try {
                            JSONArray data = res.getJSONArray("data");
                            processData(data);
                            hideLoading(false, "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    getPunchDateFromLocal();
                }
            });
            FrRequest.getInstance().request(request);
        }
    }

    private void processData(JSONArray data) {
        punchDayAdapter = new PunchDayAdapter(this.getActivity(), datePlans, FrApplication.getInstance().getReport());
        punchRecordRecyclerView.setAdapter(punchDayAdapter);
    }

    private void getPunchDateFromLocal() {
        datePlans = FrDbHelper.getInstance(getActivity()).getPunchDatePlans();
        hideLoading(true, getResources().getString(R.string.network_error));
        if(punchDayAdapter == null) {
            punchDayAdapter = new PunchDayAdapter(this.getActivity(), datePlans, FrApplication.getInstance().getReport());
            punchRecordRecyclerView.setAdapter(punchDayAdapter);
        }else
            punchDayAdapter.notifyDataSetChanged();
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }
        info_container.setVisibility(View.VISIBLE);
        info_container.smoothScrollTo(0, 0);
    }


}
