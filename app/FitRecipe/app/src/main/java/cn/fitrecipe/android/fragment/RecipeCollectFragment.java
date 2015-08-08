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

import cn.fitrecipe.android.Adpater.RecipeCardAdapter;
import cn.fitrecipe.android.FrApplication;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.R;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.model.RecipeCard;

/**
 * Created by 奕峰 on 2015/4/11.
 */
public class RecipeCollectFragment extends Fragment
{

    private RecyclerView collectRecipeRecyclerView;
    private RecipeCardAdapter recipeCardAdapter;
    private RecyclerViewLayoutManager collectRecipeLayoutManager;

    List<RecipeCard> recipeCards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_collect_recipe, container, false);

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

    private void initView(View view) {
        collectRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.collect_recipe_recycler_view);
        collectRecipeLayoutManager = new RecyclerViewLayoutManager(this.getActivity());
        collectRecipeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        collectRecipeRecyclerView.setLayoutManager(collectRecipeLayoutManager);
    }

    private void initData(String dataString) throws JSONException {
        JSONObject data = null;
        if(recipeCards != null)
            recipeCards.clear();
        else
            recipeCards = new ArrayList<RecipeCard>();
        if(dataString != null) {
            data = new JSONObject(dataString);
            JSONArray updates = data.getJSONArray("update");
            for (int i = 0; i < updates.length(); i++) {
                JSONObject update = updates.getJSONObject(i);
                String name = update.getString("title");
                int id = update.getInt("id");
                JSONArray effect_labels = update.getJSONArray("effect_labels");
                String function = "";
                String function_backup = "";
                if(effect_labels.length() > 0)
                    function = effect_labels.getJSONObject(0).getString("name");
                if(effect_labels.length() > 1) {
                    function_backup = effect_labels.getJSONObject(1).getString("name");
                }
                int duration = update.getInt("duration");
                String total_amount = update.getString("total_amount");
                double calories = update.getDouble("calories");// * Integer.parseInt(total_amount.substring(0, total_amount.indexOf("g"))) / 100;
                String img = FrServerConfig.getImageCompressed(update.getString("img"));
                RecipeCard rc = new RecipeCard(name, id, function, function_backup, duration, (int) calories, 0, img);
                recipeCards.add(rc);
            }
        }

        recipeCardAdapter = new RecipeCardAdapter(getActivity(),  recipeCards);
        collectRecipeRecyclerView.setAdapter(recipeCardAdapter);
    }

    private void initEvent() {

    }
}
