package cn.fitrecipe.android.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import cn.fitrecipe.android.entity.SeriesPlan;

/**
 * Created by wk on 2015/9/4.
 */
public class SeriesPlanDao {

    private Dao<SeriesPlan, Integer> seriesPlanDaoOpe;
    private DatabaseHelper helper;

    public SeriesPlanDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context);
            seriesPlanDaoOpe = helper.getDao(SeriesPlan.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SeriesPlan add(SeriesPlan seriesPlan) {
        try {
            seriesPlanDaoOpe.create(seriesPlan);
            return seriesPlan;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SeriesPlan get(int id) {
        try {
            return seriesPlanDaoOpe.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
