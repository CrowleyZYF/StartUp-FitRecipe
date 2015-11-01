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

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.BorderScrollView;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.Collection;
import cn.fitrecipe.android.entity.Recipe;
import cn.fitrecipe.android.function.RequestErrorHelper;
import pl.tajchert.sample.DotsTextView;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class RecipeCollectFragment extends Fragment
{

    private BorderScrollView borderScrollView;
    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;

    private RecyclerView collectRecipeRecyclerView;
    private RecipeCardAdapter recipeCardAdapter;
    private RecyclerViewLayoutManager collectRecipeLayoutManager;
    List<Recipe> recipeCards;
    List<Integer> recipeCardsID;
    private int lastid = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_collect_recipe, container, false);
        initView(view);
        getData();
        initEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences=getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
        if(recipeCardsID != null && preferences.getBoolean("hasDelete", false) && recipeCardsID.indexOf(preferences.getInt("delete_id", -1))!=-1){
            recipeCards.remove(recipeCardsID.indexOf(preferences.getInt("delete_id", -1)));
            recipeCardsID.remove(recipeCardsID.indexOf(preferences.getInt("delete_id", -1)));
            recipeCardAdapter.notifyDataSetChanged();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("hasDelete", false);
            editor.commit();
        }
    }

    private void initView(View view) {
        collectRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.collect_recipe_recycler_view);
        collectRecipeLayoutManager = new RecyclerViewLayoutManager(this.getActivity());
        collectRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        collectRecipeRecyclerView.setLayoutManager(collectRecipeLayoutManager);

        loadingInterface = (LinearLayout) view.findViewById(R.id.loading_interface);
        borderScrollView = (BorderScrollView) view.findViewById(R.id.index_content);
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
        String url = FrServerConfig.getCollectionsUrl("recipe", lastid);
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
                        if(data == null || data.length() == 0 && lastid  != -1) {
                            //Toast.makeText(getActivity(), "没有多余", Toast.LENGTH_SHORT).show();
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
                RequestErrorHelper.toast(getActivity(), volleyError);
            }
        });
        FrRequest.getInstance().request(request);
    }

    private void processData(JSONArray data)  {
        List<Collection> collections = new Gson().fromJson(data.toString(), new TypeToken<List<Collection>>(){}.getType());
        if(recipeCards == null) {
            recipeCards = new ArrayList<>();
            recipeCardsID = new ArrayList<>();
        }
        if (lastid == -1){
            recipeCards.clear();
            recipeCardsID.clear();
        }
        if(collections != null && collections.size() > 0) {
            lastid = collections.get(collections.size() - 1).getId();
            for(int i = 0; i < collections.size(); i++) {
                recipeCards.add(collections.get(i).getRecipe());
                recipeCardsID.add(collections.get(i).getId());
            }
        }
        if(recipeCardAdapter == null) {
            recipeCardAdapter = new RecipeCardAdapter(getActivity(), recipeCards, recipeCardsID, true);
            collectRecipeRecyclerView.setAdapter(recipeCardAdapter);
        }else
            recipeCardAdapter.notifyDataSetChanged();
    }

    private void initEvent() {

    }
}
