package cn.fitrecipe.android.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.fitrecipe.android.entity.Collection;

/**
 * Created by wk on 2015/8/9.
 */
public class CollectionDao {

    SharedPreferences sp;
    Gson gson;

    public CollectionDao(Context context) {
        sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveCollections(List<Collection> collections) {
        SharedPreferences.Editor editor = sp.edit();
        String json = gson.toJson(collections);
        editor.putString("collections", json);
        editor.commit();
    }

    public List<Collection> getCollections() {
        String json = sp.getString("collections", null);
        if(json == null)
            return null;
        return gson.fromJson(json, new TypeToken<List<Collection>>(){}.getType());
    }

    public void clear() {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("collections");
        editor.commit();
    }
}
