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
import java.util.List;

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.Adpater.ThemeCardAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.Collection;
import cn.fitrecipe.android.entity.Theme;
import cn.fitrecipe.android.model.ThemeCard;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class ThemeFragment extends Fragment
{

    private RecyclerView themeRecipeRecyclerView;
    private ThemeCardAdapter themeCardAdapter;
    private RecyclerViewLayoutManager themeRecipeLayoutManager;

    List<Theme> themeCards;
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

    private void initEvent() {

    }

    private void getData() {
        String url = FrServerConfig.getCollectionsUrl("theme", lastid);
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

    private void processData(JSONArray data)  {
        List<Collection> collections = new Gson().fromJson(data.toString(), new TypeToken<List<Collection>>(){}.getType());
        if(themeCards == null)
            themeCards = new ArrayList<>();
        if(collections != null && collections.size() > 0) {
            lastid = collections.get(collections.size()-1).getId();
            for(int i = 0; i < collections.size(); i++) {
                themeCards.add(collections.get(i).getTheme());
            }
        }
        if(themeCardAdapter == null) {
            themeCardAdapter = new ThemeCardAdapter(getActivity(), themeCards);
            themeRecipeRecyclerView.setAdapter(themeCardAdapter);
        }else
            themeCardAdapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        themeRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.collect_theme_recycler_view);
        themeRecipeLayoutManager = new RecyclerViewLayoutManager(this.getActivity());
        themeRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        themeRecipeRecyclerView.setLayoutManager(themeRecipeLayoutManager);
    }
}
