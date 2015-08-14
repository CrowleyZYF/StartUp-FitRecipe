package cn.fitrecipe.android.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import cn.fitrecipe.android.entity.HomeData;

/**
 * Created by wk on 2015/8/9.
 */
public class HomeDataDao {

    SharedPreferences sp;
    Gson gson;

    public HomeDataDao(Context context) {
        sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void savHomeData(HomeData homedata) {
        SharedPreferences.Editor editor = sp.edit();
        String json = gson.toJson(homedata);
        editor.putString("homedata", json);
        editor.commit();
    }

    public HomeData getHomeData() {
        String json = sp.getString("homedata", null);
        if(json == null)
            return null;
        return gson.fromJson(json, HomeData.class);
    }

    public void clear() {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("homedata");
        editor.commit();
    }
}
