package cn.fitrecipe.android.entity;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by wk on 2015/8/8.
 */
public class AuthorDao {

    SharedPreferences sp;
    Gson gson;

    public AuthorDao(Context context) {
        sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveAuthor(Author author) {
        SharedPreferences.Editor editor = sp.edit();
        String json = gson.toJson(author);
        editor.putString("author", json);
        editor.commit();
    }

    public Author getAuthor() {
        String json = sp.getString("author", null);
        if(json == null)
            return null;
        return gson.fromJson(json, Author.class);
    }

    public void clear() {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("author");
        editor.commit();
    }
}
