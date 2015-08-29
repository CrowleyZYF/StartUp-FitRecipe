package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.fitrecipe.android.entity.Ingredient;

/**
 * Created by wk on 2015/8/14.
 */
public class IngredientDao {
    private Dao<Ingredient, Integer> ingredientDaoOpe;
    private DatabaseHelper helper;

    public IngredientDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            ingredientDaoOpe = helper.getDao(Ingredient.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Ingredient ingredient) {
        try {
            if(!ingredientDaoOpe.idExists(ingredient.getId()))
                ingredientDaoOpe.create(ingredient);
            else
                ingredientDaoOpe.update(ingredient);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ingredient> getIngredientInBasket() {
        List<Ingredient> ingredients = null;
        try {
            ingredients = ingredientDaoOpe.queryForEq("inBasket", true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }


    public Ingredient get(int id) {
        Ingredient ingredient = null;
        try {
            ingredient = ingredientDaoOpe.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredient;
    }

    public List<Ingredient> getAll() {
        List<Ingredient> ingredients = null;
        try {
            ingredients = ingredientDaoOpe.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    public void addAll(List<Ingredient> ingredients) {
        try {
            ingredientDaoOpe.create(ingredients);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
