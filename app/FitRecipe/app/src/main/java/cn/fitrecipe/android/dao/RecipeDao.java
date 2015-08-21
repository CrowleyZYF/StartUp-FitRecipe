package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.fitrecipe.android.entity.Recipe;

/**
 * Created by wk on 2015/8/12.
 */
public class RecipeDao {

    private Dao<Recipe, Integer> recipeDaoOpe;
    private DatabaseHelper helper;

    public RecipeDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            recipeDaoOpe = helper.getDao(Recipe.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Recipe recipe) {
        try {
            recipeDaoOpe.create(recipe);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Recipe> getAll() {
        List<Recipe> recipes = null;
        try {
            recipes = recipeDaoOpe.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recipes;
    }
}