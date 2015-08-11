package cn.fitrecipe.android.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wk on 2015/8/9.
 */
public class HomeData {

    private List<Recipe> update;
    private List<Recommend> recommend;
    private List<Theme> theme;

    public static HomeData fromJson(String json) throws JSONException {
        HomeData homeData = new HomeData();
        JSONObject data = new JSONObject(json);
        Gson gson = new Gson();
        if(data.has("update")) {
            List<Recipe> update = new ArrayList<>();
            JSONArray jupdate = data.getJSONArray("update");
            for(int i = 0; i < jupdate.length(); i++) {
                Recipe recipe = Recipe.fromJson(jupdate.getJSONObject(i).toString());
                update.add(recipe);
            }
            homeData.setUpdate(update);
        }

        if(data.has("recommend")) {
            List<Recommend> recommends = new ArrayList<>();
            JSONArray jrecommends = data.getJSONArray("recommend");
            for(int i = 0; i < jrecommends.length(); i++) {
                JSONObject jrecommend = jrecommends.getJSONObject(i);
                int type = jrecommend.getInt("recommendtype");
                Recommend recommend = new Recommend();
                recommend.setRecommendtype(type);
                if(type == 0) {
                    recommend.setRecipe(Recipe.fromJson(jrecommend.toString()));
                }else {
                    recommend.setSeries(gson.fromJson(jrecommend.toString(), Series.class));
                }
                recommends.add(recommend);
            }
            homeData.setRecommend(recommends);
        }

        if(data.has("theme")) {
           List<Theme> themes =  gson.fromJson(data.getJSONArray("theme").toString(), new TypeToken<List<Theme>>() {
            }.getType());
            homeData.setTheme(themes);
        }
        return homeData;
    }

    public List<Recipe> getUpdate() {
        return update;
    }

    public void setUpdate(List<Recipe> update) {
        this.update = update;
    }

    public List<Recommend> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<Recommend> recommend) {
        this.recommend = recommend;
    }

    public List<Theme> getTheme() {
        return theme;
    }

    public void setTheme(List<Theme> theme) {
        this.theme = theme;
    }
}
