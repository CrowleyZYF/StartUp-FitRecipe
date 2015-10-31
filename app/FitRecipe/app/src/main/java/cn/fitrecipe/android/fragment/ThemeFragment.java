package cn.fitrecipe.android.fragment;

import android.content.SharedPreferences;
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

import cn.fitrecipe.android.Adpater.ThemeCardAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.BorderScrollView;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.Collection;
import cn.fitrecipe.android.entity.Theme;
import cn.fitrecipe.android.function.RequestErrorHelper;
import pl.tajchert.sample.DotsTextView;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class ThemeFragment extends Fragment
{

    private BorderScrollView borderScrollView;
    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;

    private RecyclerView themeRecipeRecyclerView;
    private ThemeCardAdapter themeCardAdapter;
    private RecyclerViewLayoutManager themeRecipeLayoutManager;

    List<Theme> themeCards;
    private ArrayList<Integer> themeCardsId;
    private int lastid = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_collect_theme, container, false);

        initView(view);
        getData();
        initEvent();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences=getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
        if(themeCardsId != null && preferences.getBoolean("hasDelete", false) && themeCardsId.indexOf(preferences.getInt("delete_id", -1))!=-1){
            themeCards.remove(themeCardsId.indexOf(preferences.getInt("delete_id", -1)));
            themeCardsId.remove(themeCardsId.indexOf(preferences.getInt("delete_id", -1)));
            themeCardAdapter.notifyDataSetChanged();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("hasDelete", false);
            editor.commit();
        }
    }

    private void initEvent() {

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

    private void getData() {
        String url = FrServerConfig.getCollectionsUrl("theme", lastid);
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
                        if(!(data == null || data.length() == 0)) {
                            processData(data);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                RequestErrorHelper.toast(getActivity(), volleyError);
            }
        });
        FrRequest.getInstance().request(request);
    }

    private void processData(JSONArray data)  {
        List<Collection> collections = new Gson().fromJson(data.toString(), new TypeToken<List<Collection>>(){}.getType());
        if(themeCards == null) {
            themeCards = new ArrayList<>();
            themeCardsId = new ArrayList<>();
        }
        if (lastid==-1){
            themeCards.clear();
            themeCardsId.clear();
        }
        if(collections != null && collections.size() > 0) {
            lastid = collections.get(collections.size() - 1).getId();
            for(int i = 0; i < collections.size(); i++) {
                themeCards.add(collections.get(i).getTheme());
                themeCardsId.add(collections.get(i).getId());
            }
        }
        if(themeCardAdapter == null) {
            themeCardAdapter = new ThemeCardAdapter(getActivity(), themeCards, themeCardsId, true);
            themeRecipeRecyclerView.setAdapter(themeCardAdapter);
        }else
            themeCardAdapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        themeRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.collect_theme_recycler_view);
        themeRecipeLayoutManager = new RecyclerViewLayoutManager(this.getActivity());
        themeRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        themeRecipeRecyclerView.setLayoutManager(themeRecipeLayoutManager);

        loadingInterface = (LinearLayout) view.findViewById(R.id.loading_interface);
        borderScrollView = (BorderScrollView) view.findViewById(R.id.collect_theme_list);
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
}
