package cn.fitrecipe.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.ThemeCardAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.model.ThemeCard;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class ThemeFragment extends Fragment
{

    private RecyclerView themeRecipeRecyclerView;
    private ThemeCardAdapter themeCardAdapter;
    private RecyclerViewLayoutManager themeRecipeLayoutManager;

    List<ThemeCard> themeCards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_collect_theme, container, false);

        String dataString = FrApplication.getInstance().getData();
        initView(view);
        try {
            initData(dataString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initEvent();

        return view;
    }

    private void initEvent() {

    }

    private void initData(String dataString) throws JSONException {
        JSONObject data = null;
        if(themeCards != null)
            themeCards.clear();
        else
            themeCards = new ArrayList<ThemeCard>();
        if(dataString != null) {
            data = new JSONObject(dataString);

            JSONArray themes = data.getJSONArray("theme");
            for (int i = 0; i < themes.length(); i++) {
                JSONObject theme = themes.getJSONObject(i);
                String bg = FrServerConfig.getImageCompressed(theme.getString("thumbnail"));
                ThemeCard tc = new ThemeCard(theme.getInt("id"), bg, theme.toString());
                themeCards.add(tc);
            }
        }
        themeCardAdapter = new ThemeCardAdapter(getActivity(), themeCards);
        themeRecipeRecyclerView.setAdapter(themeCardAdapter);
    }

    private void initView(View view) {
        themeRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.collect_theme_recycler_view);
        themeRecipeLayoutManager = new RecyclerViewLayoutManager(this.getActivity());
        themeRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        themeRecipeRecyclerView.setLayoutManager(themeRecipeLayoutManager);
    }
}
