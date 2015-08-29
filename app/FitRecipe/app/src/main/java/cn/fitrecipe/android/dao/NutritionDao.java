package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fitrecipe.android.entity.Nutrition;

/**
 * Created by wk on 2015/8/15.
 */
public class NutritionDao {

    private Dao<Nutrition, Integer> nutritionDaoOpe;
    private DatabaseHelper helper;

    public NutritionDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            nutritionDaoOpe = helper.getDao(Nutrition.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Nutrition nutrition) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("recipe_id", nutrition.getRecipe().getId());
            map.put("name", nutrition.getName());
            List<Nutrition> nutritions = nutritionDaoOpe.queryForFieldValues(map);
            if(nutritions != null && nutritions.size() > 0) {
                nutrition.setId(nutritions.get(0).getId());
                nutritionDaoOpe.update(nutrition);
            }else
                nutritionDaoOpe.create(nutrition);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Nutrition> getNutritions(int recipe_id) {
        List<Nutrition> nutritions;
        try {
            nutritions = nutritionDaoOpe.queryForEq("recipe_id", recipe_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nutritions;
    }

    public Nutrition get(int id) {
        Nutrition nutrition = null;
        try {
            nutrition = nutritionDaoOpe.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nutrition;
    }

}
