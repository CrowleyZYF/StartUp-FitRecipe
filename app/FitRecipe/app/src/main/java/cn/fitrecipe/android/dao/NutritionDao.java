package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

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

    public int add(Nutrition nutrition) {
        int id = 0;
        try {
            id = nutritionDaoOpe.create(nutrition);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
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
