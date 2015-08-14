package cn.fitrecipe.android.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.entity.Recipe;

/**
 * Created by wk on 2015/8/6.
 */
public class BasketDao {

    SharedPreferences sp;
    Gson gson;

    public BasketDao(Context context) {
        sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveBasket(List<Recipe> recipes) {
        String json = gson.toJson(recipes);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("basket", json);
        editor.commit();
    }

    public List<Recipe> getBasket() {
        String json = sp.getString("basket", null);
        if(json == null)
            return new ArrayList<Recipe>();
        return gson.fromJson(json, new TypeToken<List<Recipe>>(){}.getType());
    }

    public void clearBasket() {
        saveBasket(new ArrayList<Recipe>());
    }
}
